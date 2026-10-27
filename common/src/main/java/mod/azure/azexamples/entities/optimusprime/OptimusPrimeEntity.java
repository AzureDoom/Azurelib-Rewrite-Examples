package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.entities.optimusprime.ai.OptimusStrikeGoal;
import mod.azure.azurelib.animation.dispatch.command.sequence.AzSequence;
import mod.azure.azurelib.animation.dispatch.command.sequence.AzSequenceEvent;
import mod.azure.azurelib.animation.dispatch.command.sequence.AzSequencePlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 * <p>
 * Shows server-side gameplay timed by sequence events. {@link OptimusStrikeGoal} starts a sword strike; the
 * {@link AzSequencePlayer} below sends it to clients and, ticking on the server, fires the strike's events on the ticks
 * the animation reaches them. The {@code impact} event deals the damage, so it lands on the swing's impact frame at
 * either strike speed without any hand-tuned tick numbers.
 */
public class OptimusPrimeEntity extends Monster {

    protected final OptimusPrimeAnimationDispatcher animationDispatcher;

    /**
     * Plays the strike on the base controller and delivers its events here, on the server. The timing tells it the
     * controller's transition length and the animation lengths, which is what lets the anchored events follow speed.
     */
    private final AzSequencePlayer strikePlayer = AzSequencePlayer
        .forEntity(this, CommonStrings.BASE_CONTROLLER, this::onStrikeEvent)
        .withTiming(OptimusPrimeAnimationDispatcher.STRIKE_TIMING);

    private boolean animationStarted;

    /** True from the start of a strike until its {@code recovered} event (or an interruption). */
    private boolean striking;

    public OptimusPrimeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new OptimusPrimeAnimationDispatcher(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new OptimusStrikeGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) {
            updateAnimations();
        } else {
            // Events are delivered on the side that ticks the player.
            strikePlayer.tick();
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    public void updateAnimations() {
        if (animationStarted) {
            return;
        }
        animationStarted = true;

        animationDispatcher.clientIdle();
    }

    public boolean isStriking() {
        return striking;
    }

    /**
     * Starts a strike, faster once he's below half health. Only the sequence changes: its events are anchored to the
     * animation, so the impact still lands on the swing's impact frame (tick 95 normally, 63 enraged).
     */
    public void startStrike() {
        var sequence = isEnraged()
            ? OptimusPrimeAnimationDispatcher.STRIKE_ENRAGED
            : OptimusPrimeAnimationDispatcher.STRIKE;

        striking = true;
        strikePlayer.play(sequence);
    }

    /**
     * Abandons a strike in progress: pending events are dropped so the impact can't land late, and the idle loop is put
     * back.
     */
    public void interruptStrike() {
        striking = false;
        strikePlayer.clear();
        animationDispatcher.serverIdle();
    }

    private boolean isEnraged() {
        return getHealth() < getMaxHealth() / 2;
    }

    /**
     * Receives the strike's events on the server, each on the tick the animation reaches it.
     */
    public void onStrikeEvent(AzSequence sequence, AzSequenceEvent event) {
        switch (event.name()) {
            case OptimusPrimeAnimationDispatcher.SWING_EVENT -> playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, 0.5F);
            case OptimusPrimeAnimationDispatcher.IMPACT_EVENT -> strikeImpact(
                sequence == OptimusPrimeAnimationDispatcher.STRIKE_ENRAGED
            );
            case OptimusPrimeAnimationDispatcher.RECOVERED_EVENT -> striking = false;
            default -> {}
        }
    }

    /**
     * Damages and knocks back everything in the impact area in front of him, with a shockwave of particles.
     */
    private void strikeImpact(boolean enraged) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        float yaw = getYRot() * Mth.DEG_TO_RAD;
        float forwardX = -Mth.sin(yaw);
        float forwardZ = Mth.cos(yaw);
        Vec3 center = new Vec3(getX() + forwardX * 5, getY(), getZ() + forwardZ * 5);

        AABB area = AABB.ofSize(center, 3.5 * 2, 4.0, 3.5 * 2);
        float damage = enraged ? 16F : 12F;
        DamageSource source = DamageSource.mobAttack(this);

        for (
            LivingEntity victim : serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity != this && entity.isAlive() && entity.distanceToSqr(center) <= Mth.square(
                        3.5 + entity.getBbWidth() / 2
                )
            )
        ) {
            if (victim.hurt(source, damage)) {
                victim.knockback(enraged ? 2.0 : 1.5, -forwardX, -forwardZ);
            }
        }

        serverLevel.sendParticles(
            ParticleTypes.EXPLOSION,
            center.x,
            center.y + 0.2,
            center.z,
            8,
                3.5 / 2,
            0.1,
                3.5 / 2,
            0.0
        );
        serverLevel.sendParticles(
            ParticleTypes.CLOUD,
            center.x,
            center.y + 0.1,
            center.z,
            30,
                3.5 / 2,
            0.05,
                3.5 / 2,
            0.15
        );
        serverLevel.playSound(
            null,
            center.x,
            center.y,
            center.z,
            SoundEvents.ANVIL_LAND,
            SoundSource.HOSTILE,
            1.5F,
            enraged ? 0.8F : 0.6F
        );
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        // A dead Optimus doesn't get to land the strike he was winding up.
        strikePlayer.clear();
        striking = false;
        super.die(damageSource);
    }
}
