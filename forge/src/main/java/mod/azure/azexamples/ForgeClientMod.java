package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
import mod.azure.azexamples.items.netheritereplace.NetheriteSwordRenderer;
import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorRenderer;

@Mod.EventBusSubscriber(modid = CommonStrings.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientMod {

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
        AzItemRendererRegistry.register(ExampleRegistry.PISTOL.get(), PistolRenderer::new);
        AzItemRendererRegistry.register(ExampleRegistry.STARGATE_ITEM.get(), StargateBlockItemRenderer::new);
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ExampleRegistry.DOOMICORN_HELMET.get(),
            ExampleRegistry.DOOMICORN_CHESTPLATE.get(),
            ExampleRegistry.DOOMICORN_LEGGINGS.get(),
            ExampleRegistry.DOOMICORN_BOOTS.get()
        );
        ItemBlockRenderTypes.setRenderLayer(ExampleRegistry.STARGATE.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ExampleRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        event.registerEntityRenderer(ExampleRegistry.MARAUDER.get(), MarauderRenderer::new);
        event.registerEntityRenderer(ExampleRegistry.MANUL.get(), ManulRenderer::new);
        event.registerEntityRenderer(ExampleRegistry.JURAVENATOR.get(), JuravenatorRenderer::new);
        event.registerEntityRenderer(ExampleRegistry.MARINE.get(), MarineRenderer::new);
        event.registerEntityRenderer(ExampleRegistry.GREMLIN.get(), GremlinRenderer::new);
        event.registerBlockEntityRenderer(
            ExampleRegistry.STARGATE_BLOCK_ENTITY.get(),
            (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        );
        event.registerEntityRenderer(EntityType.CREEPER, CreeperRenderer::new);
    }
}
