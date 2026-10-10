package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class DoomHunterEntity extends EntityMob {

    /** DataWatcher slot for the synced "has an attack target" flag. */
    private static final int AGGRESSIVE = 20;

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(AGGRESSIVE, (byte) 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote) {
            this.dataWatcher.updateObject(AGGRESSIVE, (byte) (this.getEntityToAttack() != null ? 1 : 0));
        }
    }

    /**
     * The attack target only exists on the server in 1.7.10, so it is synced to the client through the data watcher.
     */
    public boolean isAggressive() {
        return this.dataWatcher.getWatchableObjectByte(AGGRESSIVE) != 0;
    }

    public void updateAnimations() {
        animationDispatcher.clientIdle();
        animationDispatcher.clientFlamethrower();
    }
}
