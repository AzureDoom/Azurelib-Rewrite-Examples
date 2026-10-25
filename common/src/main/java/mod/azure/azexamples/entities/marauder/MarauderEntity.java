package mod.azure.azexamples.entities.marauder;

import com.mojang.logging.LogUtils;
import mod.azure.azurelib.common.ai.pathing.AzureNavigation;
import mod.azure.azurelib.common.hitbox.AzHitbox;
import mod.azure.azurelib.common.hitbox.AzHitboxHolder;
import mod.azure.azurelib.common.hitbox.AzHitboxRole;
import mod.azure.azurelib.common.hitbox.AzHitboxSet;
import mod.azure.azurelib.common.hitbox.AzHitboxes;
import mod.azure.azurelib.common.hitbox.AzOrientedBox;
import mod.azure.azurelib.common.util.MoveAnalysis;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Predicate;

import mod.azure.azexamples.entities.marauder.ai.DelayedAttackGoal;
import mod.azure.azexamples.entities.marauder.ai.EnergySlashProfile;
import mod.azure.azexamples.entities.marauder.ai.MeleeSwingProfile;
import mod.azure.azexamples.entities.marauder.ai.TimedAttack;

public class MarauderEntity extends Monster implements AzHitboxHolder {

    private static final Logger LOGGER = LogUtils.getLogger();

    /** Per-tick server log of the slash wave during energy slashes. Turn off once verified. */
    public static final boolean DEBUG_SLASH = true;

    protected static final EntityDataAccessor<Float> SPAWN_TICKS = SynchedEntityData.defineId(
        MarauderEntity.class,
        EntityDataSerializers.FLOAT
    );

    /** Synced AttackKind ordinal; replaces the old ATTACKING boolean so the client knows which clip to play. */
    private static final EntityDataAccessor<Integer> ATTACK_KIND = SynchedEntityData.defineId(
        MarauderEntity.class,
        EntityDataSerializers.INT
    );

    /**
     * The body yaw isn't synced by vanilla, so while the server holds the Marauder's facing (turning toward a target or
     * attacking) it publishes the yaw here and the client applies it to body and head. Without this the client body
     * lags up to ~75 degrees behind the head, and the model (and any client-reported pose) faces the wrong way.
     */
    private static final EntityDataAccessor<Float> LOCKED_YAW = SynchedEntityData.defineId(
        MarauderEntity.class,
        EntityDataSerializers.FLOAT
    );

    private static final EntityDataAccessor<Boolean> YAW_LOCKED = SynchedEntityData.defineId(
        MarauderEntity.class,
        EntityDataSerializers.BOOLEAN
    );

    private static final Predicate<AzHitbox> AXE = hitbox -> hitbox.hasTag("axe");

    private static final Predicate<AzHitbox> SLASH = hitbox -> hitbox.hasTag("slash");

    private static final Predicate<AzHitbox> COMBAT = hitbox -> hitbox.role() == AzHitboxRole.COMBAT;

    private static final double AXE_HIT_MARGIN = 0.3;

    private static final double SLASH_HIT_MARGIN = 0.25;

    /** Max distance (blocks) between sweep samples; well under the wave's 2 block length. */
    private static final double SLASH_SWEEP_STEP = 0.5;

    /** The wave ends ~31 blocks out; walls beyond this don't matter. */
    private static final double SLASH_MAX_TRAVEL = 32.0;

    private static final AzHitboxSet HITBOXES = AzHitboxSet.builder()
        .add(
            "h_head_furious",
            h -> h.size(8, 8, 9)
                .anchorAtPivot()
                .offset(0, 3.5f, -0.5f)
                .damageMultiplier(2f)
                .tag("weak_point")
        )
        .add("upper_torso")
        .add("torso")
        .add("left_arm")
        .add("left_elbow")
        .add("hand_boomstick", h -> h.tag("weapon"))
        .add("right_arm")
        .add("right_elbow")
        .add("left_leg")
        .add("left_knee")
        .add("leg_boomstick", h -> h.tag("weapon"))
        .add("right_leg")
        .add("right_knee")
        .add("axe_blade", "axe", h -> h.anchor().size(3, 17, 19).anchorAtPivot().offset(0, 0, -21.5f).tag("axe"))
        .add("axe_haft", "axe", h -> h.anchor().size(3, 3, 32).anchorAtPivot().offset(0, 0, -9))
        .add(
            "slash_wave",
            "h_slash10",
            h -> h.anchor()
                .size(16, 32, 32)
                .anchorAtPivot()
                .offset(0, 2, 0)
                .tag("slash")
        )
        .reportInterval(1)
        .build();

    public enum AxeHit {
        HIT,
        MISS,
        UNKNOWN
    }

    public enum SlashHit {
        HIT,
        MISS,
        BLOCKED,
        UNKNOWN
    }

    public enum AttackKind {

        NONE,
        MELEE,
        SLASH;

        private static final AttackKind[] VALUES = values();

        public static AttackKind byId(int id) {
            return id >= 0 && id < VALUES.length ? VALUES[id] : NONE;
        }
    }

    public int MAX_SPAWN_ANIMATION_TICKS = 290;

    private static final MeleeSwingProfile MELEE_PROFILE = MeleeSwingProfile.AXE_SPIN;

    private static final EnergySlashProfile SLASH_PROFILE = EnergySlashProfile.ENERGY_SLASH;

    // Server-only attack state. The entity owns the clock so goal restarts can't desync it.
    private AttackKind attackKind = AttackKind.NONE;

    private int attackStartTick = -1;

    private int nextMeleeTick;

    private int nextSlashTick;

    private boolean swingResolved;

    private float lockedYaw;

    private boolean yawLocked;

    private int lastTurnTick = -100;

    private int unknownPoseTicks;

    // Server-only slash sweep state, reset per slash.
    @Nullable
    private Vec3 prevSlashCenter;

    @Nullable
    private Vec3 slashOrigin;

    private Vec3 slashForward = Vec3.ZERO;

    private double slashWallDistance;

    public final MarauderAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    public MarauderEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new MarauderAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public AzHitboxSet getHitboxSet() {
        return HITBOXES;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return AzHitboxes.getCullingBox(this, super.getBoundingBoxForCulling());
    }

    public AxeHit checkAxeHit(Entity target) {
        if (!AzHitboxes.hasLivePose(this)) {
            return AxeHit.UNKNOWN;
        }

        var axe = AzHitboxes.getActiveHitboxes(this, AXE);

        if (axe.isEmpty()) {
            return AxeHit.MISS;
        }

        var targetBoxes = AzHitboxes.getActiveHitboxes(target, COMBAT);

        for (var blade : axe) {
            var box = blade.box().inflate(AXE_HIT_MARGIN);

            if (targetBoxes.isEmpty()) {
                if (box.intersects(target.getBoundingBox())) {
                    return AxeHit.HIT;
                }
                continue;
            }

            for (var part : targetBoxes) {
                if (box.intersects(part.box())) {
                    return AxeHit.HIT;
                }
            }
        }

        return AxeHit.MISS;
    }

    /**
     * Swept test of the slash wave against the target. The wave moves up to ~4.3 blocks per tick but is only 2 blocks
     * long, so a single per-tick overlap test would tunnel through targets. Instead, the path from last tick's wave
     * centre to this tick's is sampled every {@link #SLASH_SWEEP_STEP} blocks. Moving the wave back by an offset is the
     * same as moving the target forward by it, so each sample shifts the target box instead of rebuilding the oriented
     * box.
     */
    public SlashHit checkSlashHit(LivingEntity target, int t) {
        if (!AzHitboxes.hasLivePose(this)) {
            if (DEBUG_SLASH) {
                LOGGER.info("[marauder slash] t={} frame={} livePose=false", t, SLASH_PROFILE.animTick(t));
            }
            return SlashHit.UNKNOWN;
        }

        var waves = AzHitboxes.getActiveHitboxes(this, SLASH);

        if (waves.isEmpty()) {
            if (DEBUG_SLASH) {
                LOGGER.info("[marauder slash] t={} frame={} livePose=true boxes=0", t, SLASH_PROFILE.animTick(t));
            }
            return SlashHit.MISS;
        }

        var wave = waves.iterator().next().box().inflate(SLASH_HIT_MARGIN);
        var current = wave.center();

        if (slashOrigin == null) {
            launchSlash(current);
        }

        var from = prevSlashCenter == null ? current : prevSlashCenter;
        prevSlashCenter = current;

        var travel = current.subtract(from);
        var steps = Math.max(1, Mth.ceil(travel.length() / SLASH_SWEEP_STEP));
        var waveDistance = current.subtract(slashOrigin).dot(slashForward);

        var result = sweepWave(wave, travel, steps, target);

        if (result == SlashHit.HIT && forwardDistanceTo(target) > slashWallDistance) {
            result = SlashHit.BLOCKED;
        } else if (result == SlashHit.MISS && waveDistance > slashWallDistance) {
            result = SlashHit.BLOCKED;
        }

        if (DEBUG_SLASH) {
            LOGGER.info(
                "[marauder slash] t={} frame={} livePose=true wave={} sweep={} steps={} wall={} target={} result={}",
                t,
                SLASH_PROFILE.animTick(t),
                String.format("%.2f", waveDistance),
                String.format("%.2f", travel.length()),
                steps,
                String.format("%.2f", slashWallDistance),
                String.format("%.2f", forwardDistanceTo(target)),
                result
            );
        }

        return result;
    }

    private SlashHit sweepWave(
        AzOrientedBox wave,
        Vec3 travel,
        int steps,
        LivingEntity target
    ) {
        var targetBoxes = AzHitboxes.getActiveHitboxes(target, COMBAT);

        for (int i = 0; i <= steps; i++) {
            // Sample i puts the wave at (current - back); test it by shifting the target by +back instead.
            var back = travel.scale(1.0 - (double) i / steps);

            if (targetBoxes.isEmpty()) {
                if (wave.intersects(target.getBoundingBox().move(back))) {
                    return SlashHit.HIT;
                }
                continue;
            }

            for (var part : targetBoxes) {
                if (wave.intersects(part.box().bounds().move(back))) {
                    return SlashHit.HIT;
                }
            }
        }

        return SlashHit.MISS;
    }

    /** First live wave frame: fix the flight line and find the first wall along it. */
    private void launchSlash(Vec3 waveCenter) {
        var yaw = Math.toRadians(lockedYaw);
        this.slashForward = new Vec3(-Math.sin(yaw), 0, Math.cos(yaw));
        this.slashOrigin = waveCenter;

        var end = waveCenter.add(slashForward.scale(SLASH_MAX_TRAVEL));
        var hit = level().clip(
            new ClipContext(waveCenter, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)
        );

        this.slashWallDistance = hit.getType() == HitResult.Type.MISS
            ? SLASH_MAX_TRAVEL
            : hit.getLocation().subtract(waveCenter).dot(slashForward);
    }

    /** Distance along the flight line from the wave's launch point to the near side of the target. */
    private double forwardDistanceTo(LivingEntity target) {
        if (slashOrigin == null) {
            return 0;
        }
        return target.getBoundingBox().getCenter().subtract(slashOrigin).dot(slashForward) - target.getBbWidth() * 0.5;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AzureNavigation(this, level);
    }

    @Override
    public float maxUpStep() {
        return 2.0F;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 80 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void tick() {
        // Goals run inside super.tick(), so an attack started this tick has getAttackTicks() == 0 below.
        super.tick();
        moveAnalysis.update();

        if (!this.level().isClientSide()) {
            tickAttack();
            tickFacing();

            if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS && this.isAlive()) {
                this.setSpawnTicks(this.getSpawnTicks() + 1.0F);
                this.navigation.stop();
                this.setYBodyRot(0);
                this.setYHeadRot(0);
                this.setXRot(0);
                this.setYRot(0);
                this.setTarget(null);
            }
        } else {
            if (this.entityData.get(YAW_LOCKED)) {
                var yaw = this.entityData.get(LOCKED_YAW);
                this.setYBodyRot(yaw);
                this.setYHeadRot(yaw);
            }
            updateAnimations();
        }
    }

    public void updateAnimations() {
        if (this.isDeadOrDying()) {
            animationDispatcher.clientDeath();
            return;
        }

        if (this.getSpawnTicks() < MAX_SPAWN_ANIMATION_TICKS) {
            this.animationDispatcher.clientSpawn();
            return;
        }

        switch (getAttackKind()) {
            case MELEE -> {
                this.animationDispatcher.clientMelee();
                return;
            }
            case SLASH -> {
                this.animationDispatcher.clientEnergySlash();
                return;
            }
            default -> {}
        }

        if (this.moveAnalysis.isMoving()) {
            if (this.isAggressive()) {
                this.animationDispatcher.clientRun();
            } else {
                this.animationDispatcher.clientWalk();
            }
            return;
        }
        this.animationDispatcher.clientIdle();
    }

    // ---------------------------------------------------------------- attacks

    public MeleeSwingProfile getMeleeProfile() {
        return MELEE_PROFILE;
    }

    public EnergySlashProfile getSlashProfile() {
        return SLASH_PROFILE;
    }

    /** Timing of the attack in progress (server). */
    public TimedAttack currentTiming() {
        return attackKind == AttackKind.SLASH ? SLASH_PROFILE : MELEE_PROFILE;
    }

    public boolean hasFinishedSpawning() {
        return getSpawnTicks() >= MAX_SPAWN_ANIMATION_TICKS;
    }

    /** Synced: which attack clip the client should be playing. */
    public AttackKind getAttackKind() {
        return AttackKind.byId(this.entityData.get(ATTACK_KIND));
    }

    public boolean isAttacking() {
        return getAttackKind() != AttackKind.NONE;
    }

    /** Server-side kind of the attack in progress. */
    public AttackKind getServerAttackKind() {
        return attackKind;
    }

    /** Ticks since the current attack started (0 on the start tick), or -1 when not attacking. Server only. */
    public int getAttackTicks() {
        return attackStartTick < 0 ? -1 : this.tickCount - attackStartTick;
    }

    public boolean isAttackReady(AttackKind kind) {
        return attackStartTick < 0 && this.tickCount >= nextTickFor(kind);
    }

    public int getTicksUntilNextAttack(AttackKind kind) {
        return Math.max(0, nextTickFor(kind) - this.tickCount);
    }

    private int nextTickFor(AttackKind kind) {
        return kind == AttackKind.SLASH ? nextSlashTick : nextMeleeTick;
    }

    public boolean isSwingResolved() {
        return swingResolved;
    }

    public float getAttackYaw() {
        return lockedYaw;
    }

    public void setAttackYaw(float yaw) {
        this.lockedYaw = yaw;
    }

    /**
     * Pre-attack turn. Takes over the facing (starting from the current body yaw), rotates toward desiredYaw by at most
     * maxDegrees this tick, and returns true once within tolerance. Must be called every tick while turning; if it
     * stops being called the lock is released automatically.
     */
    public boolean turnToward(float desiredYaw, float maxDegrees, float tolerance) {
        if (!yawLocked) {
            this.lockedYaw = this.yBodyRot;
            this.yawLocked = true;
        }

        this.lastTurnTick = this.tickCount;
        this.lockedYaw = Mth.approachDegrees(lockedYaw, desiredYaw, maxDegrees);
        applyLockedYaw();

        return Math.abs(Mth.wrapDegrees(desiredYaw - lockedYaw)) <= tolerance;
    }

    public void beginAttack(AttackKind kind, float yaw) {
        if (this.level().isClientSide() || kind == AttackKind.NONE) {
            return;
        }

        this.attackKind = kind;
        this.attackStartTick = this.tickCount;
        this.swingResolved = false;
        this.unknownPoseTicks = 0;
        this.lockedYaw = yaw;
        this.yawLocked = true;
        this.prevSlashCenter = null;
        this.slashOrigin = null;
        this.slashForward = Vec3.ZERO;
        this.slashWallDistance = SLASH_MAX_TRAVEL;
        this.entityData.set(ATTACK_KIND, kind.ordinal());
        applyLockedYaw();
    }

    /** Hit landed, or the attack can no longer hit: no more tests this attack, its cooldown starts now. */
    public void resolveSwing() {
        if (swingResolved) {
            return;
        }
        this.swingResolved = true;

        if (attackKind == AttackKind.SLASH) {
            this.nextSlashTick = this.tickCount + SLASH_PROFILE.cooldownTicks();
        } else {
            this.nextMeleeTick = this.tickCount + MELEE_PROFILE.cooldownTicks();
        }
    }

    public void noteUnknownPose() {
        unknownPoseTicks++;
    }

    private void tickAttack() {
        if (attackStartTick < 0) {
            return;
        }

        if (!isAlive() || getAttackTicks() >= currentTiming().durationTicks()) {
            endAttack();
            return;
        }
    }

    /**
     * Re-applies the held yaw after super.tick() so LookControl / BodyRotationControl / MoveControl can't turn the
     * Marauder off target. Releases a pre-attack turn that the goal stopped driving (target lost, out of range).
     */
    private void tickFacing() {
        if (!yawLocked) {
            return;
        }

        if (attackStartTick < 0 && this.tickCount - lastTurnTick > 1) {
            releaseFacing();
            return;
        }

        applyLockedYaw();
    }

    private void releaseFacing() {
        this.yawLocked = false;
        this.entityData.set(YAW_LOCKED, false);
    }

    private void endAttack() {
        // Goal stopped mid-attack (target died, lost, etc.): still pay the cooldown.
        resolveSwing();

        if (unknownPoseTicks > 0) {
            LOGGER.warn(
                "[marauder] {} hit-window ticks had no live pose; those were treated as misses",
                unknownPoseTicks
            );
        }

        this.attackKind = AttackKind.NONE;
        this.attackStartTick = -1;
        this.prevSlashCenter = null;
        this.slashOrigin = null;
        this.entityData.set(ATTACK_KIND, AttackKind.NONE.ordinal());
        releaseFacing();
    }

    private void applyLockedYaw() {
        this.setYRot(lockedYaw);
        this.setYBodyRot(lockedYaw);
        this.setYHeadRot(lockedYaw);
        this.entityData.set(LOCKED_YAW, lockedYaw);
        this.entityData.set(YAW_LOCKED, true);
    }

    protected void setSpawnTicks(float spawnTicks) {
        this.entityData.set(SPAWN_TICKS, spawnTicks);
    }

    public float getSpawnTicks() {
        return this.entityData.get(SPAWN_TICKS);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SPAWN_TICKS, 0.0F);
        builder.define(ATTACK_KIND, AttackKind.NONE.ordinal());
        builder.define(LOCKED_YAW, 0.0F);
        builder.define(YAW_LOCKED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("SpawnTicks", this.getSpawnTicks());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSpawnTicks(compound.getFloat("SpawnTicks"));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.3F));
        this.goalSelector.addGoal(2, new DelayedAttackGoal(this, 0.6F, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) { /* DISABLES VANILLA WALK SOUND */}
}
