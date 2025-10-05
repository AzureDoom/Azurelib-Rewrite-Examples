package mod.azure.azexamples.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.registry.EntityRegistry;

public class StargateBlock extends BaseEntityBlock {

    public StargateBlock(Properties properties) {
        super(properties);
    }

    /**
     * Creates a new {@link BlockEntity} instance for the Stargate block at the specified position and state.
     *
     * @param pos   The position of the block in the world.
     * @param state The current block state for this block entity.
     * @return A new {@link BlockEntity} instance associated with the Stargate block, or {@code null} if none is
     *         available.
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return EntityRegistry.STARGATE_BLOCK_ENTITY.get().create(pos, state);
    }

    /**
     * Determines the appropriate ticker for a block entity to handle its periodic updates.
     *
     * @param level The current level or world instance.
     * @param state The block state of the associated block.
     * @param type  The type of the block entity to obtain the ticker for.
     * @param <T>   A subtype of BlockEntity.
     * @return A BlockEntityTicker for the specified block entity type, or null if no ticker is applicable.
     */
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        @NotNull Level level,
        @NotNull BlockState state,
        @NotNull BlockEntityType<T> type
    ) {
        return createTickerHelper(
            type,
            EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
            StargateBlockEntity::tick
        );
    }
}
