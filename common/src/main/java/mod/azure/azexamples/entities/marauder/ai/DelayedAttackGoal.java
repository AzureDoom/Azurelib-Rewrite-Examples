package mod.azure.azexamples.entities.marauder.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity.AttackKind;

/**
 * Melee + energy slash, both driven by bone hitboxes. The attack clock, cooldowns and "already resolved" state live on
 * the entity, so the goal stopping/restarting mid-attack can neither double-hit nor skip a cooldown.
 */
public class DelayedAttackGoal extends MeleeAttackGoal {

    /** How fast he squares up before an attack. */
    private static final float PRE_TURN_DEGREES_PER_TICK = 20f;

    /** The spin keeps tracking through its wind-up, so it can start a little off-axis. */
    private static final float MELEE_FACE_TOLERANCE = 20f;

    /** The wave only tracks until frame 5 and is ~1 block wide at up to 26 blocks, so square up tighter. */
    private static final float SLASH_FACE_TOLERANCE = 8f;

    private final MarauderEntity marauder;

    public DelayedAttackGoal(MarauderEntity marauder, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(marauder, speedModifier, followingTargetEvenIfNotSeen);
        this.marauder = marauder;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return marauder.hasFinishedSpawning() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return marauder.hasFinishedSpawning() && super.canContinueToUse();
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity target) {
        if (mob.level().isClientSide()) {
            return;
        }

        var t = marauder.getAttackTicks();

        if (t >= 0) {
            mob.getNavigation().stop();
            trackTarget(target, marauder.currentTiming(), t);

            if (!marauder.isSwingResolved()) {
                switch (marauder.getServerAttackKind()) {
                    case MELEE -> tickMelee(target, marauder.getMeleeProfile(), t);
                    case SLASH -> tickSlash(target, marauder.getSlashProfile(), t);
                    default -> {}
                }
            }
            return;
        }

        AttackKind next = null;

        if (marauder.isAttackReady(AttackKind.MELEE) && canStartSwing(target, marauder.getMeleeProfile())) {
            next = AttackKind.MELEE;
        } else if (marauder.isAttackReady(AttackKind.SLASH) && canStartSlash(target, marauder.getSlashProfile())) {
            next = AttackKind.SLASH;
        }

        if (next == null) {
            return; // not calling turnToward lets the entity release any pre-attack turn
        }

        // Square up first; the attack only starts once he is facing the target.
        mob.getNavigation().stop();
        var desiredYaw = yawTo(target);
        var tolerance = next == AttackKind.MELEE ? MELEE_FACE_TOLERANCE : SLASH_FACE_TOLERANCE;

        if (!marauder.turnToward(desiredYaw, PRE_TURN_DEGREES_PER_TICK, tolerance)) {
            return;
        }

        marauder.beginAttack(next, desiredYaw);
    }

    /** Wind-up: turn toward the target at a limited rate, then commit to the locked yaw. */
    private void trackTarget(LivingEntity target, TimedAttack timing, int t) {
        if (t < timing.trackUntilTick()) {
            marauder.setAttackYaw(Mth.approachDegrees(marauder.getAttackYaw(), yawTo(target), timing.maxTurnPerTick()));
        }
    }

    private void tickMelee(LivingEntity target, MeleeSwingProfile profile, int t) {
        if (profile.isHitTick(t)) {
            var axeHit = marauder.checkAxeHit(target);

            if (axeHit == MarauderEntity.AxeHit.UNKNOWN) {
                marauder.noteUnknownPose();
            } else if (axeHit == MarauderEntity.AxeHit.HIT) {
                mob.swingForAttack(InteractionHand.MAIN_HAND);
                mob.doHurtTarget((ServerLevel) target.level(), target);
                marauder.resolveSwing();
                return;
            }
        }

        if (t >= profile.lastHitTick()) {
            marauder.resolveSwing();
        }
    }

    private void tickSlash(LivingEntity target, EnergySlashProfile profile, int t) {
        if (profile.isHitTick(t)) {
            switch (marauder.checkSlashHit(target, t)) {
                case HIT -> {
                    target.hurtServer((ServerLevel) mob.level(), mob.damageSources().mobAttack(mob), profile.damage());
                    marauder.resolveSwing();
                    return;
                }
                case BLOCKED -> {
                    // Wave has passed the first wall in its path; nothing beyond it can be hit.
                    marauder.resolveSwing();
                    return;
                }
                case UNKNOWN -> marauder.noteUnknownPose();
                case MISS -> {}
            }
        }

        if (t >= profile.lastHitTick()) {
            marauder.resolveSwing();
        }
    }

    /** Range gate tuned to where the axe actually lands, instead of vanilla's ~0.83 block reach. */
    protected boolean canStartSwing(LivingEntity target, MeleeSwingProfile profile) {
        if (!mob.getSensing().hasLineOfSight(target)) {
            return false;
        }

        var horizontal = horizontalDistance(target) - target.getBbWidth() * 0.5;
        var vertical = Math.abs(target.getY() - mob.getY());

        return horizontal <= profile.startReach() && vertical <= 2.0;
    }

    protected boolean canStartSlash(LivingEntity target, EnergySlashProfile profile) {
        if (!mob.getSensing().hasLineOfSight(target)) {
            return false;
        }

        var horizontal = horizontalDistance(target);
        var vertical = Math.abs(target.getY() - mob.getY());

        return horizontal >= profile.minRange()
            && horizontal <= profile.maxRange()
            && vertical <= profile.maxHeightDiff();
    }

    private double horizontalDistance(LivingEntity target) {
        var dx = target.getX() - mob.getX();
        var dz = target.getZ() - mob.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    private float yawTo(LivingEntity target) {
        var dx = target.getX() - mob.getX();
        var dz = target.getZ() - mob.getZ();
        return (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
    }

    @Override
    protected void resetAttackCooldown() {
        // Cooldowns are owned by the entity; see MarauderEntity#resolveSwing.
    }

    @Override
    protected boolean isTimeToAttack() {
        return marauder.isAttackReady(AttackKind.MELEE);
    }

    @Override
    protected int getAttackInterval() {
        return marauder.getMeleeProfile().cooldownTicks();
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return marauder.getTicksUntilNextAttack(AttackKind.MELEE);
    }
}
