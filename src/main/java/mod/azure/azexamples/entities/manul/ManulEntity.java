package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class ManulEntity extends MobEntity {

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
    public void tick() {
        super.tick();
        updateMovementState();
    }

    private void updateMovementState() {
        double dx = Mc.x(this) - Mc.prevX(this);
        double dz = Mc.z(this) - Mc.prevZ(this);
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
}
