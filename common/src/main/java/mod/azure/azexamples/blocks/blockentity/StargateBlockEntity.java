package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import mod.azure.azexamples.registry.EntityRegistry;

/**
 * TODO: Move to new systems once ready
 */
public class StargateBlockEntity extends BlockEntity {

    public StargateBlockEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.STARGATE_BLOCK_ENTITY.get(), pos, blockState);
    }
}
