package mod.azure.azexamples.entities.manul;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;

/**
 * Credit to JayZX535 for request of this example.
 */
public class ManulEntity extends PathfinderMob {

    /** Ticks the new movement state must hold before the animation switches. Filters out pathing hiccups. */
    private static final int STATE_SWITCH_TICKS = 5;

    /** Squared horizontal blocks per tick below which the manul counts as standing still. */
    private static final double MOVING_THRESHOLD_SQR = 1.0E-4;

    protected final ManulAnimationDispatcher animationDispatcher;

    private boolean walking;

    private int pendingStateTicks;

    public ManulEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new ManulAnimationDispatcher(this);
    }

    @Override
    public void tick() {
        super.tick();
        updateMovementState();
    }

    /**
     * Debounced walking state. Horizontal only, so gravity/step-up jitter on Y doesn't count as walking, and a short
     * pause between path nodes doesn't count as stopping.
     */
    private void updateMovementState() {
        var dx = getX() - xo;
        var dz = getZ() - zo;
        var movingNow = dx * dx + dz * dz > MOVING_THRESHOLD_SQR;

        if (movingNow == walking) {
            pendingStateTicks = 0;
            return;
        }

        if (++pendingStateTicks >= STATE_SWITCH_TICKS) {
            walking = movingNow;
            pendingStateTicks = 0;
        }
    }

    /**
     * Safe to call every tick. Only switches pools when the debounced state changes; the pool picks each variant
     * itself when the current animation ends.
     */
    public void updateAnimations() {
        animationDispatcher.play(walking);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.3F));
    }
}