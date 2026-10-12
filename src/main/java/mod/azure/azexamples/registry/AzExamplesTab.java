package mod.azure.azexamples.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import mod.azure.azexamples.CommonStrings;

public final class AzExamplesTab {

    public static final CreativeTabs TAB = new CreativeTabs(CommonStrings.CREATIVE_TAB) {

        @Override
        public Item getTabIconItem() {
            return BlockRegistry.stargateItem();
        }
    };

    private AzExamplesTab() {}
}
