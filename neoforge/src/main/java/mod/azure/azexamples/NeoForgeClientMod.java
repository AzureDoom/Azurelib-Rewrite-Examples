package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.blocks.blockentity.StargateBlockRenderer;
import mod.azure.azexamples.entities.creeper.CreeperRenderer;
import mod.azure.azexamples.entities.doomhunter.DoomHunterRenderer;
import mod.azure.azexamples.entities.gremlin.GremlinRenderer;
import mod.azure.azexamples.entities.juravenator.JuravenatorRenderer;
import mod.azure.azexamples.entities.manul.ManulRenderer;
import mod.azure.azexamples.entities.marauder.MarauderRenderer;
import mod.azure.azexamples.entities.marine.MarineRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.items.armors.DoomicornArmorRenderer;
import mod.azure.azexamples.items.gunwitharm.GunWithArmRenderer;
import mod.azure.azexamples.items.netheritereplace.NetheriteSwordRenderer;
import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorRenderer;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

@EventBusSubscriber(modid = CommonStrings.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeClientMod {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        AzItemRendererRegistry.register(Items.NETHERITE_SWORD, NetheriteSwordRenderer::new);
        AzArmorRendererRegistry.register(
            NetheriteArmorRenderer::new,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS
        );
        AzItemRendererRegistry.register(ItemRegistry.PISTOL.get(), PistolRenderer::new);
        AzItemRendererRegistry.register(ItemRegistry.PEACEMAKER.get(), GunWithArmRenderer::new);
        AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM.get(), StargateBlockItemRenderer::new);
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ItemRegistry.DOOMICORN_HELMET.get(),
            ItemRegistry.DOOMICORN_CHESTPLATE.get(),
            ItemRegistry.DOOMICORN_LEGGINGS.get(),
            ItemRegistry.DOOMICORN_BOOTS.get()
        );
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.STARGATE.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MANUL.get(), ManulRenderer::new);
        event.registerEntityRenderer(EntityRegistry.JURAVENATOR.get(), JuravenatorRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MARINE.get(), MarineRenderer::new);
        event.registerEntityRenderer(EntityRegistry.GREMLIN.get(), GremlinRenderer::new);
        event.registerBlockEntityRenderer(
            EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
            (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        );
        event.registerEntityRenderer(EntityType.CREEPER, CreeperRenderer::new);
    }
}
