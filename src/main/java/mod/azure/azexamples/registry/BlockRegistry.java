package mod.azure.azexamples.registry;

import net.minecraft.item.ItemBlock;

import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;

public final class BlockRegistry {

    public static final StargateBlock STARGATE = RegistryHelper.block(new StargateBlock(), "stargate");

    public static final ItemBlock STARGATE_ITEM = RegistryHelper.item(new StargateBlockItem(STARGATE), "stargate");

    private BlockRegistry() {}
}
