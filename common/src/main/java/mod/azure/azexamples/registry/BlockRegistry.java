package mod.azure.azexamples.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;
import mod.azure.azexamples.services.AzExampleServices;

/**
 * A registry class for managing the initialization and registration of custom blocks and their associated items in the
 * mod.
 */
public class BlockRegistry {

    private BlockRegistry() {}

    public static final Supplier<StargateBlock> STARGATE =
        AzExampleServices.COMMON_REGISTRY.registerBlock(
            "stargate",
            StargateBlock::new,
            BlockBehaviour.Properties.of()
                .sound(SoundType.DRIPSTONE_BLOCK)
                .strength(5.0f, 8.0f)
                .noOcclusion()
        );

    public static final Supplier<StargateBlockItem> STARGATE_ITEM =
        AzExampleServices.COMMON_REGISTRY.registerItem(
            "stargate",
            properties -> new StargateBlockItem(STARGATE.get(), properties),
            Item.Properties::useBlockDescriptionPrefix
        );

    public static void initialize() {}
}
