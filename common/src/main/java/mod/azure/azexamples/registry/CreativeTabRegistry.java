package mod.azure.azexamples.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

import mod.azure.azexamples.CommonStrings;
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
 *         () -> AzExampleServices.COMMON_REGISTRY.newCreativeTabBuilder()
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

    @SuppressWarnings("unchecked")
    public static final Supplier<CreativeModeTab> EXAMPLEMOD_TAB =
        AzExampleServices.COMMON_REGISTRY.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            "examplemod_items",
            () -> AzExampleServices.COMMON_REGISTRY
                .newCreativeTabBuilder(
                    BlockRegistry.STARGATE_ITEM,
                    ItemRegistry.PISTOL,
                    ItemRegistry.DOOMICORN_HELMET,
                    ItemRegistry.DOOMICORN_CHESTPLATE,
                    ItemRegistry.DOOMICORN_LEGGINGS,
                    ItemRegistry.DOOMICORN_BOOTS,
                    ItemRegistry.MARAUDER_SPAWN_EGG,
                    ItemRegistry.DOOMHUNTER_SPAWN_EGG,
                    ItemRegistry.MANUL_SPAWN_EGG,
                    ItemRegistry.JURAVENATOR_SPAWN_EGG,
                    ItemRegistry.MARINE_SPAWN_EGG,
                    ItemRegistry.GREMLIN_SPAWN_EGG
                )
                .title(Component.translatable(CommonStrings.CREATIVE_TAB))
                .icon(() -> BlockRegistry.STARGATE_ITEM.get().getDefaultInstance())
                .build()
        );

    public static void initialize() {}

}
