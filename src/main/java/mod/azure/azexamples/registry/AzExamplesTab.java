package mod.azure.azexamples.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import mod.azure.azexamples.CommonStrings;

public final class AzExamplesTab {

    public static final CreativeTabs TAB = new CreativeTabs(CommonStrings.CREATIVE_TAB) {

        @Override
        @Nonnull
        public ItemStack getTabIconItem() {
            return new ItemStack(BlockRegistry.STARGATE_ITEM);
        }
    };

    private AzExamplesTab() {}
}
