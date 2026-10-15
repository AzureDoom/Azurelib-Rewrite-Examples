package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class DoomHunterEntity extends MobEntity {

    private static final int AGGRESSIVE = 20;

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    @Override
    protected void registerSyncedData() {
        super.registerSyncedData();
        this.syncedData.register(AGGRESSIVE, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Mc.isClientSide(this.world)) {
            // this.syncedData.update(AGGRESSIVE, (byte) (this.walkTarget != null ? 1 : 0));
        }
    }

    public boolean isAggressive() {
        return this.syncedData.getByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }
}
