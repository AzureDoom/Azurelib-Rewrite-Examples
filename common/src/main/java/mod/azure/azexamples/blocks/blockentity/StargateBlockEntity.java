package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import mod.azure.azexamples.registry.EntityRegistry;

public class StargateBlockEntity extends BlockEntity {

    public final StargateBlockAnimationDispatcher animationDispatcher;

    public StargateBlockEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.STARGATE_BLOCK_ENTITY.get(), pos, blockState);
        this.animationDispatcher = new StargateBlockAnimationDispatcher();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StargateBlockEntity blockEntity) {
        if (blockEntity.level != null && level.isClientSide()) {
            blockEntity.animationDispatcher.serverSpin(blockEntity);
        }
    }
}
