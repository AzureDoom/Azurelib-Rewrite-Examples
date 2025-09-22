package mod.azure.azexamples;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.blocks.blockentity.StargateBlockRenderer;
import mod.azure.azexamples.entities.creeper.CreeperRenderer;
import mod.azure.azexamples.entities.doomhunter.DoomHunterRenderer;
import mod.azure.azexamples.entities.juravenator.JuravenatorRenderer;
import mod.azure.azexamples.entities.manul.ManulRenderer;
import mod.azure.azexamples.entities.marauder.MarauderRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.items.armors.DoomicornArmorRenderer;
import mod.azure.azexamples.items.netheritereplace.NetheriteSwordRenderer;
import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorRenderer;

public class FabricLibClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AzItemRendererRegistry.register(Items.NETHERITE_SWORD, NetheriteSwordRenderer::new);
        AzArmorRendererRegistry.register(
            NetheriteArmorRenderer::new,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS
        );
        AzItemRendererRegistry.register(ExampleRegistry.PISTOL, PistolRenderer::new);
        AzItemRendererRegistry.register(ExampleRegistry.STARGATE_ITEM, StargateBlockItemRenderer::new);
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ExampleRegistry.DOOMICORN_HELMET,
            ExampleRegistry.DOOMICORN_CHESTPLATE,
            ExampleRegistry.DOOMICORN_LEGGINGS,
            ExampleRegistry.DOOMICORN_BOOTS
        );
        EntityRendererRegistry.register(ExampleRegistry.DOOMHUNTER, DoomHunterRenderer::new);
        EntityRendererRegistry.register(ExampleRegistry.MARAUDER, MarauderRenderer::new);
        EntityRendererRegistry.register(ExampleRegistry.MANUL, ManulRenderer::new);
        EntityRendererRegistry.register(ExampleRegistry.JURAVENATOR, JuravenatorRenderer::new);
        EntityRendererRegistry.register(EntityType.CREEPER, CreeperRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ExampleRegistry.STARGATE, RenderType.translucent());
        BlockEntityRenderers.register(
            ExampleRegistry.STARGATE_BLOCK_ENTITY,
            (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        );
    }
}
