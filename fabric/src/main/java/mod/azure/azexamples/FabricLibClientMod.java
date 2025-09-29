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
import mod.azure.azexamples.entities.marine.MarineRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.items.armors.DoomicornArmorRenderer;
import mod.azure.azexamples.items.netheritereplace.NetheriteSwordRenderer;
import mod.azure.azexamples.items.netheritereplace.armor.NetheriteArmorRenderer;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.EntityRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

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
        AzItemRendererRegistry.register(ItemRegistry.PISTOL.get(), PistolRenderer::new);
        AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM.get(), StargateBlockItemRenderer::new);
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ItemRegistry.DOOMICORN_HELMET.get(),
            ItemRegistry.DOOMICORN_CHESTPLATE.get(),
            ItemRegistry.DOOMICORN_LEGGINGS.get(),
            ItemRegistry.DOOMICORN_BOOTS.get()
        );
        EntityRendererRegistry.register(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MANUL.get(), ManulRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.JURAVENATOR.get(), JuravenatorRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MARINE.get(), MarineRenderer::new);
        EntityRendererRegistry.register(EntityType.CREEPER, CreeperRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.STARGATE.get(), RenderType.translucent());
        BlockEntityRenderers.register(
            EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
            (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        );
    }
}
