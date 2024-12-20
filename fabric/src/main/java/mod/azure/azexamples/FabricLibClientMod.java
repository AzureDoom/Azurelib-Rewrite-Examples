package mod.azure.azexamples;

import mod.azure.azurelib.core2.render.item.AzItemRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.entities.doomhunter.DoomHunterRenderer;
import mod.azure.azexamples.entities.marauder.MarauderRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public class FabricLibClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AzItemRendererRegistry.register(ItemRegistry.PISTOL.get(), new PistolRenderer());
        AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM.get(), new StargateBlockItemRenderer());
        EntityRendererRegistry.register(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.STARGATE.get(), RenderType.translucent());
        // BlockEntityRenderers.register(
        // EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
        // (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateRender()
        // );
    }
}
