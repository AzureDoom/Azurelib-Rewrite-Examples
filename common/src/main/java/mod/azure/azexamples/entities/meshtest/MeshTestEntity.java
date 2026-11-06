package mod.azure.azexamples.entities.meshtest;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

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
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION, 0);
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
    protected @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        if (variant.animationCount() < 2) {
            return super.mobInteract(player, hand);
        }

        if (!level().isClientSide()) {
            entityData.set(ANIMATION, Math.floorMod(entityData.get(ANIMATION) + 1, variant.animationCount()));
        }

        return level().isClientSide()
            ? InteractionResult.SUCCESS
            : InteractionResult.SUCCESS_SERVER;
    }

    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("MeshTestAnimation", entityData.get(ANIMATION));
    }

    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);

        input.getInt("MeshTestAnimation").ifPresent(animation -> {
            entityData.set(
                ANIMATION,
                Math.floorMod(animation, Math.max(1, variant.animationCount()))
            );
        });
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
