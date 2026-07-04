package mod.azure.azexamples.registry;

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

    // public static final Supplier<CreativeModeTab> EXAMPLEMOD_TAB = AzExampleServices.COMMON_REGISTRY
    // .register(
    // BuiltInRegistries.CREATIVE_MODE_TAB,
    // "examplemod_items",
    // () -> AzExampleServices.COMMON_REGISTRY.newCreativeTabBuilder()
    // .title(Component.translatable(CommonStrings.CREATIVE_TAB))
    // .icon(() -> new ItemStack(BlockRegistry.STARGATE_ITEM.get()))
    // .displayItems((enabledFeatures, entries) -> {
    // // TODO: Cannot access 'accept(net.minecraft.world.level.ItemLike)' in
    // // 'net.minecraft.world.item.CreativeModeTab.Output'?
    // // entries.accept(BlockRegistry.STARGATE_ITEM.get());
    // // entries.accept(ItemRegistry.PISTOL.get());
    // // entries.accept(ItemRegistry.PEACEMAKER.get());
    // // entries.accept(ItemRegistry.DOOMICORN_HELMET.get());
    // // entries.accept(ItemRegistry.DOOMICORN_CHESTPLATE.get());
    // // entries.accept(ItemRegistry.DOOMICORN_LEGGINGS.get());
    // // entries.accept(ItemRegistry.DOOMICORN_BOOTS.get());
    // // entries.accept(ItemRegistry.MARAUDER_SPAWN_EGG.get());
    // // entries.accept(ItemRegistry.DOOMHUNTER_SPAWN_EGG.get());
    // // entries.accept(ItemRegistry.MANUL_SPAWN_EGG.get());
    // // entries.accept(ItemRegistry.JURAVENATOR_SPAWN_EGG.get());
    // // entries.accept(ItemRegistry.MARINE_SPAWN_EGG.get());
    // // entries.accept(ItemRegistry.GREMLIN_SPAWN_EGG.get());
    // })
    // .build()
    // );

    public static void initialize() {}

}
