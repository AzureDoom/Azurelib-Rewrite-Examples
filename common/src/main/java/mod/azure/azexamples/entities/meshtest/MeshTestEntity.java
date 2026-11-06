package mod.azure.azexamples.entities.meshtest;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A stationary entity that shows one {@link MeshTestVariant}. Right-click it to switch to the variant's next animation;
 * the choice is synced, so it only affects this entity.
 */
public class MeshTestEntity extends Mob {

    private static final EntityDataAccessor<Integer> ANIMATION = SynchedEntityData.defineId(
        MeshTestEntity.class,
        EntityDataSerializers.INT
    );

    private final MeshTestVariant variant;

    public MeshTestEntity(EntityType<? extends Mob> entityType, Level level, MeshTestVariant variant) {
        super(entityType, level);
        this.variant = variant;

        if (!level.isClientSide()) {
            entityData.set(ANIMATION, variant.startAnimation(getId()));
        }
    }

    public MeshTestVariant variant() {
        return variant;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            // Re-sending the playing animation is a no-op, so this only restarts when the index changes.
            variant.command(entityData.get(ANIMATION)).sendForEntity(this);
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (variant.animationCount() < 2) {
            return super.mobInteract(player, hand);
        }

        if (!level().isClientSide()) {
            entityData.set(ANIMATION, Math.floorMod(entityData.get(ANIMATION) + 1, variant.animationCount()));
        }

        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("MeshTestAnimation", entityData.get(ANIMATION));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("MeshTestAnimation")) {
            entityData.set(ANIMATION, Math.floorMod(tag.getInt("MeshTestAnimation"), variant.animationCount()));
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
