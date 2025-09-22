package mod.azure.azexamples.platform;

import net.minecraft.world.level.block.entity.BlockEntityType;

import mod.azure.azexamples.ExampleRegistry;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.services.CommonRegistry;

/**
 * The NeoForgeCommonRegistry class provides a concrete implementation of the CommonRegistry interface for use with
 * NeoForge. It facilitates centralized registration of blocks, items, entities, block entities, and creative mode tabs.
 * This implementation leverages the ForgeMod's deferred registers to manage the creation and registration of these
 * objects.
 */
public class ForgeCommonRegistry implements CommonRegistry {

    @Override
    public BlockEntityType<StargateBlockEntity> stargateBlockEntity() {
        return ExampleRegistry.STARGATE_BLOCK_ENTITY.get();
    }
}
