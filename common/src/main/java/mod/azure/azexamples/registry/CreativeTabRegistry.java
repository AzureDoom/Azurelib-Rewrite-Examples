package mod.azure.azexamples.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
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
                    creativeTabItems().toArray(Supplier[]::new)
                )
                .title(Component.translatable(CommonStrings.CREATIVE_TAB))
                .icon(() -> BlockRegistry.STARGATE_ITEM.get().getDefaultInstance())
                .build()
        );

    private static List<Supplier<? extends ItemLike>> creativeTabItems() {
        List<Supplier<? extends ItemLike>> items = new ArrayList<>();

        items.add(BlockRegistry.STARGATE_ITEM);
        items.add(ItemRegistry.PISTOL);
        items.add(ItemRegistry.PEACEMAKER);
        items.add(ItemRegistry.DOOMICORN_HELMET);
        items.add(ItemRegistry.DOOMICORN_CHESTPLATE);
        items.add(ItemRegistry.DOOMICORN_LEGGINGS);
        items.add(ItemRegistry.DOOMICORN_BOOTS);
        items.add(ItemRegistry.MARAUDER_SPAWN_EGG);
        items.add(ItemRegistry.DOOMHUNTER_SPAWN_EGG);
        items.add(ItemRegistry.MANUL_SPAWN_EGG);
        items.add(ItemRegistry.JURAVENATOR_SPAWN_EGG);
        items.add(ItemRegistry.MARINE_SPAWN_EGG);
        items.add(ItemRegistry.GREMLIN_SPAWN_EGG);
        items.add(ItemRegistry.OPTIMUSPRIME_SPAWN_EGG);

        items.addAll(ItemRegistry.MESH_TEST_SPAWN_EGGS);

        return items;
    }

    public static void initialize() {}

}
