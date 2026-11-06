package mod.azure.azexamples.entities.meshtest;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * A stationary entity that shows one {@link MeshTestVariant}. Right-click it to switch to the variant's next animation;
 * the choice is synced, so it only affects this entity.
 */
public class MeshTestEntity extends EntityLiving {

    private static final int ANIMATION_WATCHER_ID = 20;

    private static final String ANIMATION_TAG = "MeshTestAnimation";

    private final MeshTestVariant variant;

    public MeshTestEntity(World world, MeshTestVariant variant) {
        super(world);
        this.setSize(1.0f, 1.75f);
        this.variant = variant;

        if (!world.isRemote) {
            setAnimation(variant.startAnimation(this.getEntityId()));
        }
    }

    public static class Rigid extends MeshTestEntity {

        public Rigid(World world) {
            super(world, MeshTestVariant.RIGID);
        }
    }

    public static class Animated extends MeshTestEntity {

        public Animated(World world) {
            super(world, MeshTestVariant.ANIMATED);
        }
    }

    public static class Mixed extends MeshTestEntity {

        public Mixed(World world) {
            super(world, MeshTestVariant.MIXED);
        }
    }

    public static class Skinned extends MeshTestEntity {

        public Skinned(World world) {
            super(world, MeshTestVariant.SKINNED);
        }
    }

    public static class Scaled extends MeshTestEntity {

        public Scaled(World world) {
            super(world, MeshTestVariant.SCALED);
        }
    }

    public static class Copies extends MeshTestEntity {

        public Copies(World world) {
            super(world, MeshTestVariant.COPIES);
        }
    }

    public static class AnimatedOwner extends MeshTestEntity {

        public AnimatedOwner(World world) {
            super(world, MeshTestVariant.ANIMATED_OWNER);
        }
    }

    public MeshTestVariant variant() {
        return variant;
    }

    private int getAnimation() {
        return this.dataWatcher.getWatchableObjectInt(ANIMATION_WATCHER_ID);
    }

    private void setAnimation(int index) {
        this.dataWatcher.updateObject(ANIMATION_WATCHER_ID, Integer.valueOf(index));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // Runs inside the Entity constructor, before variant is assigned, so only register the default here.
        this.dataWatcher.addObject(ANIMATION_WATCHER_ID, Integer.valueOf(0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.worldObj.isRemote) {
            // Re-sending the playing animation is a no-op, so this only restarts when the index changes.
            variant.command(getAnimation()).sendForEntity(this);
        }
    }

    /**
     * Called by EntityLiving's (final) interactFirst after the leash check. 1.7.10 has no hands, so there is no
     * EnumHand parameter.
     */
    @Override
    protected boolean interact(EntityPlayer player) {
        if (variant.animationCount() < 2) {
            return super.interact(player);
        }

        if (!this.worldObj.isRemote) {
            setAnimation(Math.floorMod(getAnimation() + 1, variant.animationCount()));
        }

        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(ANIMATION_TAG, getAnimation());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);

        if (tag.hasKey(ANIMATION_TAG, 99)) {
            setAnimation(Math.floorMod(tag.getInteger(ANIMATION_TAG), variant.animationCount()));
        }
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
