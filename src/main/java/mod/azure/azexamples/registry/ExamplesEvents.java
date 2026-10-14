package mod.azure.azexamples.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

import java.lang.invoke.MethodHandles;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

public final class ExamplesEvents {

    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    public static final Namespace NAMESPACE = Namespace.of(CommonStrings.MOD_ID);

    public static Identifier id(String path) {
        return NAMESPACE.id(path);
    }

    /** Translation key {@code item.azexamples.<name>} / {@code tile.azexamples.<name>}, as in the lang file. */
    public static void name(Item item, String name) {
        item.setTranslationKey(NAMESPACE, name);
    }

    public static void name(Block block, String name) {
        block.setTranslationKey(NAMESPACE, name);
    }

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        BlockRegistry.register();
    }

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        ItemRegistry.register();
        CommonMod.init();
    }

    @EventListener
    private static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(id("stargate_block_entity").toString(), StargateBlockEntity.class);
    }

    @EventListener
    private static void registerEntities(EntityRegisterEvent event) {
        for (EntityRegistry.Entry entry : EntityRegistry.entries()) {
            event.register(id(entry.name), entry.type);
        }
    }
}
