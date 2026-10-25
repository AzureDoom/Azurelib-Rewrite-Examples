package mod.azure.azexamples.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.blocks.StargateBlock;
import mod.azure.azexamples.blocks.StargateBlockItem;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

public final class BlockRegistry {

    public static final StargateBlock STARGATE = new StargateBlock();

    private BlockRegistry() {}

    public static void register() {
        STARGATE.setBlockName(CommonStrings.MOD_ID + ".stargate");
        STARGATE.setBlockTextureName(CommonStrings.MOD_ID + ":stargate");
        STARGATE.setCreativeTab(AzExamplesTab.TAB);
        GameRegistry.registerBlock(STARGATE, StargateBlockItem.class, "stargate");
        GameRegistry.registerTileEntity(StargateBlockEntity.class, CommonStrings.MOD_ID + ":stargate_block_entity");
    }

    /** The stargate's {@link StargateBlockItem}, created by Forge when the block is registered. */
    public static Item stargateItem() {
        return Item.getItemFromBlock(STARGATE);
    }
}
