package mod.azure.azexamples.platform;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.services.CommonRegistry;

/**
 * The FabricCommonRegistry class provides an implementation of the CommonRegistry interface specifically designed for
 * the Fabric platform. It facilitates the registration of various Minecraft modding constructs such as blocks, items,
 * entities, block entities, and creative tabs. This class provides a centralized approach to managing registrations,
 * ensuring compatibility, and reducing redundancy by leveraging the Fabric API's mechanisms for modded object
 * registration. Methods include registering game objects like blocks, entities, items, and block entities as well as
 * creating other utilities for modding, such as spawn eggs and creative mode tabs.
 */
@SuppressWarnings("unchecked")
public class FabricCommonRegistry implements CommonRegistry {

    private static <T, R extends Registry<? super T>> Supplier<T> registerSupplier(
        R registry,
        String id,
        Supplier<T> object
    ) {
        final T registeredObject = Registry.register(
            (Registry<T>) registry,
            CommonMod.modResource(id),
            object.get()
        );

        return () -> registeredObject;
    }

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String registryName, Supplier<? extends T> supplier) {
        return (Supplier<T>) registerSupplier(registry, registryName, supplier);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(
        String registryName,
        Function<BlockBehaviour.Properties, T> factory,
        BlockBehaviour.Properties properties
    ) {
        var key = ResourceKey.create(
            Registries.BLOCK,
            CommonMod.modResource(registryName)
        );

        T block = factory.apply(properties.setId(key));

        Registry.register(
            BuiltInRegistries.BLOCK,
            key,
            block
        );

        return () -> block;
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(
        String registryName,
        Function<Item.Properties, T> factory,
        UnaryOperator<Item.Properties> properties
    ) {
        var key = ResourceKey.create(
            Registries.ITEM,
            CommonMod.modResource(registryName)
        );

        var item = factory.apply(
            properties.apply(
                new Item.Properties().setId(key)
            )
        );

        Registry.register(
            BuiltInRegistries.ITEM,
            key,
            item
        );

        return () -> item;
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(
        String registryName,
        Supplier<EntityType<E>> entityType
    ) {
        var key = ResourceKey.create(
            Registries.ITEM,
            CommonMod.modResource(registryName)
        );

        var item = new SpawnEggItem(
            new Item.Properties()
                .setId(key)
                .spawnEgg(entityType.get())
        );

        Registry.register(
            BuiltInRegistries.ITEM,
            key,
            item
        );

        return () -> item;
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder(
        Supplier<? extends ItemLike>... items
    ) {
        return FabricCreativeModeTab.builder()
            .displayItems((_, output) -> {
                for (var item : items) {
                    output.accept(item.get());
                }
            });
    }
}
