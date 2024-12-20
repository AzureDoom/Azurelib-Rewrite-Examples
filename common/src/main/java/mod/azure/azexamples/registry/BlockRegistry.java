package mod.azure.azexamples.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;
import mod.azure.azexamples.services.AzExampleServices;

public class BlockRegistry {

    private BlockRegistry() {}

    public static final Supplier<StargateBlock> STARGATE = registerBlock(
        "stargate",
        () -> new StargateBlock(
            BlockBehaviour.Properties.of().sound(SoundType.DRIPSTONE_BLOCK).strength(5.0f, 8.0f).noOcclusion()
        )
    );

    public static final Supplier<BlockItem> STARGATE_ITEM = ItemRegistry.registerItem(
        "stargate",
        () -> new StargateBlockItem(STARGATE.get())
    );

    /**
     * Registers a new Block.
     *
     * @param blockName The name of the block.
     * @param block     A supplier for the block.
     * @param <T>       The type of the block.
     * @return A supplier for the registered block.
     */
    static <T extends Block> Supplier<T> registerBlock(String blockName, Supplier<T> block) {
        return AzExampleServices.COMMON_REGISTRY.registerBlock(blockName, block);
    }

    public static void initialize() {}
}
