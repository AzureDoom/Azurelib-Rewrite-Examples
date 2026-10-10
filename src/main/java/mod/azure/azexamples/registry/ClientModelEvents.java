package mod.azure.azexamples.registry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.azure.azexamples.CommonStrings;

/**
 * Points every item at {@code assets/azexamples/models/item/<name>.json}. Animated items use
 * {@code "parent": "builtin/entity"} so AzureLib renders them with the json's display transforms.
 */
@Mod.EventBusSubscriber(modid = CommonStrings.MOD_ID, value = Side.CLIENT)
public final class ClientModelEvents {

    private ClientModelEvents() {}

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : RegistryHelper.ITEMS) {
            ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(item.getRegistryName(), "inventory")
            );
        }
    }
}
