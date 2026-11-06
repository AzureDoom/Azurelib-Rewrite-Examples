package mod.azure.azexamples.entities.meshtest;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * A stationary entity that shows one {@link MeshTestVariant}. Right-click it to switch to the variant's next animation;
 * the choice is synced, so it only affects this entity.
 */
public class MeshTestEntity extends MobEntity {

    private static final int ANIMATION_WATCHER_ID = 20;

    private static final String ANIMATION_TAG = "MeshTestAnimation";

    private final MeshTestVariant variant;

    public MeshTestEntity(World world, MeshTestVariant variant) {
        super(world);
        this.setSize(1.0f, 1.75f);
        this.variant = variant;

        if (!world.isMultiplayer) {
            setAnimation(variant.startAnimation(this.networkId));
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
        return this.syncedData.getInt(ANIMATION_WATCHER_ID);
    }

    private void setAnimation(int index) {
        this.syncedData.update(ANIMATION_WATCHER_ID, Integer.valueOf(index));
    }

    @Override
    protected void registerSyncedData() {
        super.registerSyncedData();
        // Runs inside the Entity constructor, before variant is assigned, so only register the default here.
        this.syncedData.register(ANIMATION_WATCHER_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isMultiplayer) {
            // Re-sending the playing animation is a no-op, so this only restarts when the index changes.
            variant.command(getAnimation()).sendForEntity(this);
        }
    }

    /**
     * Called by EntityLiving's (final) interactFirst after the leash check.
     */
    @Override
    public boolean interact(PlayerEntity player) {
        if (variant.animationCount() < 2) {
            return super.interact(player);
        }

        if (!this.world.isMultiplayer) {
            setAnimation(Math.floorMod(getAnimation() + 1, variant.animationCount()));
        }

        return true;
    }

    @Override
    public boolean writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        compound.putInt(ANIMATION_TAG, getAnimation());
        return true;
    }

    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);

        if (compound.contains(ANIMATION_TAG)) {
            setAnimation(Math.floorMod(compound.getInt(ANIMATION_TAG), variant.animationCount()));
        }
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
