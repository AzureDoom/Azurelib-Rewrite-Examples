package mod.azure.azexamples;

import mod.azure.azurelib.core2.render.item.AzItemRendererRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.entities.doomhunter.DoomHunterRenderer;
import mod.azure.azexamples.entities.marauder.MarauderRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

@EventBusSubscriber(modid = CommonMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeClientMod {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        AzItemRendererRegistry.register(ItemRegistry.PISTOL.get(), new PistolRenderer());
        AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM.get(), new StargateBlockItemRenderer());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.STARGATE.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
        // event.registerBlockEntityRenderer(
        // EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
        // (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        // );
    }
}
