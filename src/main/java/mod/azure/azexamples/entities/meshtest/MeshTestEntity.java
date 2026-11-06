package mod.azure.azexamples.entities.meshtest;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * A stationary entity that shows one {@link MeshTestVariant}. Right-click it to switch to the variant's next animation;
 * the choice is synced, so it only affects this entity.
 */
public class MeshTestEntity extends EntityLiving {

    private static final DataParameter<Integer> ANIMATION = EntityDataManager.createKey(
        MeshTestEntity.class,
        DataSerializers.VARINT
    );

    private static final String ANIMATION_TAG = "MeshTestAnimation";

    private final MeshTestVariant variant;

    public MeshTestEntity(World world, MeshTestVariant variant) {
        super(world);
        this.setSize(1.0f, 1.75f);
        this.variant = variant;

        if (!world.isRemote) {
            this.dataManager.set(ANIMATION, variant.startAnimation(this.getEntityId()));
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

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ANIMATION, 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote) {
            // Re-sending the playing animation is a no-op, so this only restarts when the index changes.
            variant.command(this.dataManager.get(ANIMATION)).sendForEntity(this);
        }
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (variant.animationCount() < 2) {
            return super.processInteract(player, hand);
        }

        if (!this.world.isRemote) {
            this.dataManager.set(
                ANIMATION,
                Math.floorMod(this.dataManager.get(ANIMATION) + 1, variant.animationCount())
            );
        }

        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(ANIMATION_TAG, this.dataManager.get(ANIMATION));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);

        if (tag.hasKey(ANIMATION_TAG, 99)) { // 99 = any numeric tag type
            this.dataManager.set(
                ANIMATION,
                Math.floorMod(tag.getInteger(ANIMATION_TAG), variant.animationCount())
            );
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
