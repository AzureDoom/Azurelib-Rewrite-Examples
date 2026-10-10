package mod.azure.azexamples.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

public final class RegistryHelper {

    static final List<Block> BLOCKS = new ArrayList<>();

    static final List<Item> ITEMS = new ArrayList<>();

    private RegistryHelper() {}

    static <T extends Block> T block(T block, String name) {
        block.setRegistryName(CommonMod.modResource(name));
        block.setUnlocalizedName(CommonStrings.MOD_ID + "." + name);
        block.setCreativeTab(AzExamplesTab.TAB);
        BLOCKS.add(block);
        return block;
    }

    static <T extends Item> T item(T item, String name) {
        item.setRegistryName(CommonMod.modResource(name));
        item.setUnlocalizedName(CommonStrings.MOD_ID + "." + name);
        item.setCreativeTab(AzExamplesTab.TAB);
        ITEMS.add(item);
        return item;
    }
}
