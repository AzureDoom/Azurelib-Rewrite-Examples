package mod.azure.azexamples.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
}
