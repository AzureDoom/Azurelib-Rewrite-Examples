package mod.azure.azexamples.registry;

import mod.azure.azurelib.common.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.services.AzExampleServices;

/**
 * Example of using this class to create a new Creative Tab:
 * <p>
 * The following code demonstrates how to register a new creative mode tab in the game:
 * </p>
 *
 * <pre>{@code
 *
 * public static final Supplier<CreativeModeTab> EXAMPLEMOD_TAB = AzExampleServices.COMMON_REGISTRY
 *     .registerCreativeModeTab(
 *         "examplemod_items",
 *         () -> Services.COMMON_REGISTRY.newCreativeTabBuilder()
 *             .title(Component.translatable("itemGroup." + CommonMod.MOD_ID + ".examplemod_items"))
 *             .icon(() -> new ItemStack(Items.ITEM_FRAME))
 *             .displayItems((enabledFeatures, entries) -> entries.accept(Items.ITEM_FRAME))
 *             .build()
 *     );
 * }</pre>
 * <p>
 * In this example:
 * </p>
 * <ul>
 * <li><code>registerCreativeModeTab</code> is a method to register a new creative mode tab with the specified mod ID
 * and tab name.</li>
 * <li>The <code>newCreativeTabBuilder</code> method is used to build the creative tab with a title, icon, and displayed
 * items.</li>
 * </ul>
 * <p>
 * The {@link net.minecraft.world.item.CreativeModeTab CreativeModeTab} class represents a tab in the creative inventory
 * menu.
 * </p>
 */
public class CreativeTabRegistry {

    private CreativeTabRegistry() {}

    public static final Supplier<CreativeModeTab> EXAMPLEMOD_TAB = AzExampleServices.COMMON_REGISTRY
        .registerCreativeModeTab(
            "examplemod_items",
            () -> Services.COMMON_REGISTRY.newCreativeTabBuilder()
                .title(Component.translatable("itemGroup." + CommonMod.MOD_ID + ".examplemod_items"))
                .icon(() -> new ItemStack(BlockRegistry.STARGATE_ITEM.get()))
                .displayItems((enabledFeatures, entries) -> {
                    entries.accept(BlockRegistry.STARGATE_ITEM.get());
                    entries.accept(ItemRegistry.PISTOL.get());
                    entries.accept(ItemRegistry.DOOMICORN_HELMET.get());
                    entries.accept(ItemRegistry.DOOMICORN_CHESTPLATE.get());
                    entries.accept(ItemRegistry.DOOMICORN_LEGGINGS.get());
                    entries.accept(ItemRegistry.DOOMICORN_BOOTS.get());
                    entries.accept(ItemRegistry.MARAUDER_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.DOOMHUNTER_SPAWN_EGG.get());
                })
                .build()
        );

    public static void initialize() {}

}
