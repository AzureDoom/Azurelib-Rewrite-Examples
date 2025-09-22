package mod.azure.azexamples.platform;

import net.minecraft.world.level.block.entity.BlockEntityType;

import mod.azure.azexamples.ExampleRegistry;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.services.CommonRegistry;

public class FabricCommonRegistry implements CommonRegistry {

    @Override
    public BlockEntityType<StargateBlockEntity> stargateBlockEntity() {
        return ExampleRegistry.STARGATE_BLOCK_ENTITY;
    }
}
