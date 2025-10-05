package mod.azure.azexamples.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.services.AzExampleServices;

public class CreativeTabRegistry {

    private CreativeTabRegistry() {}

    public static final Supplier<CreativeModeTab> EXAMPLEMOD_TAB = AzExampleServices.COMMON_REGISTRY
        .register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            "examplemod_items",
            () -> AzExampleServices.COMMON_REGISTRY.newCreativeTabBuilder()
                .title(Component.translatable(CommonStrings.CREATIVE_TAB))
                .icon(() -> new ItemStack(BlockRegistry.STARGATE_ITEM.get()))
                .displayItems((enabledFeatures, entries) -> {
                    entries.accept(BlockRegistry.STARGATE_ITEM.get());
                    entries.accept(ItemRegistry.PISTOL.get());
                    entries.accept(ItemRegistry.PEACEMAKER.get());
                    entries.accept(ItemRegistry.DOOMICORN_HELMET.get());
                    entries.accept(ItemRegistry.DOOMICORN_CHESTPLATE.get());
                    entries.accept(ItemRegistry.DOOMICORN_LEGGINGS.get());
                    entries.accept(ItemRegistry.DOOMICORN_BOOTS.get());
                    entries.accept(ItemRegistry.MARAUDER_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.DOOMHUNTER_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.MANUL_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.JURAVENATOR_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.MARINE_SPAWN_EGG.get());
                    entries.accept(ItemRegistry.GREMLIN_SPAWN_EGG.get());
                })
                .build()
        );

    public static void initialize() {}

}
