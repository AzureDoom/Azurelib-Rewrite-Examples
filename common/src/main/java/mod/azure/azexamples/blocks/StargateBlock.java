package mod.azure.azexamples.blocks;

import com.mojang.serialization.MapCodec;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod.azure.azexamples.registry.EntityRegistry;

public class StargateBlock extends BaseEntityBlock {

    public static final MapCodec<StargateBlock> CODEC = simpleCodec(StargateBlock::new);

    public StargateBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return EntityRegistry.STARGATE_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NotNull Level level,
            @NotNull BlockState state,
            @NotNull BlockEntityType<T> type
    ) {
        return createTickerHelper(type, EntityRegistry.STARGATE_BLOCK_ENTITY.get(), StargateBlockEntity::tick);
    }
}
