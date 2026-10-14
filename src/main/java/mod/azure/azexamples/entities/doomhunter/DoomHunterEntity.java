package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.world.World;

public class DoomHunterEntity extends MonsterEntity {

    private static final int AGGRESSIVE = 20;

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(3.0F, 7.0F);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(AGGRESSIVE, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Mc.isClientSide(this.world)) {
            this.dataTracker.set(AGGRESSIVE, (byte) (this.target != null ? 1 : 0));
        }
    }

    public boolean isAggressive() {
        return this.dataTracker.getByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }
}
