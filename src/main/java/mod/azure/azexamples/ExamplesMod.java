package mod.azure.azexamples;

import mod.azure.azurelib.registry.AzBlockEntityRegistry;
import mod.azure.azurelib.registry.AzEntityRegistry;
import net.ornithemc.osl.blocks.api.BlockEvents;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.items.api.ItemEvents;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ExamplesIds;
import mod.azure.azexamples.registry.ItemRegistry;

/**
 * OSL {@code init} entrypoint, run on both the client and the dedicated server.
 * <p>
 * Blocks and items must not be created here: this can run before OSL has set up its synced block and item registries,
 * and the first touch of {@code Block} or {@code Item} would then crash. They are created in OSL's register events
 * instead. Entity and block entity classes don't touch either, so they are registered directly. Their names are legacy
 * Beta names, which OSL converts to namespaced ids itself, so they use {@code _} rather than {@code :}.
 */
public final class ExamplesMod implements ModInitializer {

    @Override
    public void init() {
        BlockEvents.REGISTER_BLOCKS.register(BlockRegistry::register);
        ItemEvents.REGISTER_ITEMS.register(ItemRegistry::register);

        int entityId = ExamplesIds.FIRST_ENTITY;
        for (EntityRegistry.Entry entry : EntityRegistry.entries()) {
            AzEntityRegistry.register(entry.type, CommonStrings.MOD_ID + "_" + entry.name, entityId++);
        }
        AzBlockEntityRegistry.register(StargateBlockEntity.class, CommonStrings.MOD_ID + "_stargate_block_entity");
    }
}
