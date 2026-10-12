package mod.azure.azexamples.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nonnull;

import mod.azure.azexamples.CommonStrings;

public final class AzExamplesTab {

    public static final CreativeTabs TAB = new CreativeTabs(CommonStrings.CREATIVE_TAB) {

        @Override
        @Nonnull
        public ItemStack getTabIconItem() {
            return new ItemStack(BlockRegistry.STARGATE_ITEM);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> items) {
            super.displayAllRelevantItems(items);

            for (EntityEntry entry : EntityRegistry.ENTRIES) {
                if (entry.getEgg() == null) {
                    continue;
                }

                ItemStack egg = new ItemStack(Items.SPAWN_EGG);

                NBTTagCompound entityTag = new NBTTagCompound();
                entityTag.setString(
                    "id",
                    entry.getRegistryName().toString()
                );

                NBTTagCompound tag = new NBTTagCompound();
                tag.setTag("EntityTag", entityTag);

                egg.setTagCompound(tag);
                items.add(egg);
            }
        }
    };

    private AzExamplesTab() {}
}
