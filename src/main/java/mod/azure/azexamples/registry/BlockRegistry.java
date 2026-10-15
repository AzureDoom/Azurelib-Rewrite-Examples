package mod.azure.azexamples.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.ornithemc.osl.core.api.util.NamespacedIdentifiers;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.platform.ExMc;

/**
 * The examples' blocks, registered through OSL's Blocks API.
 * <p>
 * {@link #register()} runs from OSL's {@code BlockEvents.REGISTER_BLOCKS}, right after Vanilla's blocks; touching
 * {@code Block} any earlier (for example straight from the {@code init} entrypoint) crashes, because OSL's synced block
 * registry doesn't exist yet.
 * <p>
 * Unlike items, Beta blocks can't have their id assigned automatically: OSL rejects {@code Block.AUTO_ASSIGN_ID} on
 * Beta because the save and packet formats only hold block ids 0-255. So each block takes a fixed id from
 * {@link ExamplesIds} and is registered under that id.
 */
public final class BlockRegistry {

    public static StargateBlock STARGATE;

    private BlockRegistry() {}

    public static void register() {
        STARGATE = register(ExamplesIds.STARGATE_BLOCK, "stargate", new StargateBlock(ExamplesIds.STARGATE_BLOCK));
        ExMc.name(STARGATE, "stargate");
    }

    /** Deprecated in OSL, but it registers the block under the id it was constructed with. */
    @SuppressWarnings("deprecation")
    private static <T extends Block> T register(int id, String name, T block) {
        net.ornithemc.osl.blocks.api.BlockRegistry.register(
            id,
            NamespacedIdentifiers.from(CommonStrings.MOD_ID, name),
            block
        );
        return block;
    }

    public static Item stargateItem() {
        return ItemRegistry.STARGATE;
    }
}
