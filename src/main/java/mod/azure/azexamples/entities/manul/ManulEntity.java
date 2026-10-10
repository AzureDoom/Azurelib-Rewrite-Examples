package mod.azure.azexamples.entities.manul;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.world.World;

public class ManulEntity extends EntityCreature {

    private static final int STATE_SWITCH_TICKS = 5;

    private static final double MOVING_THRESHOLD_SQR = 1.0E-4;

    protected final ManulAnimationDispatcher animationDispatcher;

    private boolean walking;

    private int pendingStateTicks;

    public ManulEntity(World world) {
        super(world);
        this.setSize(1.2F, 1.1F);
        this.animationDispatcher = new ManulAnimationDispatcher(this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        updateMovementState();
    }

    private void updateMovementState() {
        double dx = this.posX - this.prevPosX;
        double dz = this.posZ - this.prevPosZ;
        boolean movingNow = dx * dx + dz * dz > MOVING_THRESHOLD_SQR;

        if (movingNow == walking) {
            pendingStateTicks = 0;
            return;
        }

        if (++pendingStateTicks >= STATE_SWITCH_TICKS) {
            walking = movingNow;
            pendingStateTicks = 0;
        }
    }

    public void updateAnimations() {
        animationDispatcher.play(walking);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(7, new EntityAIWander(this, 0.3D));
    }
}
