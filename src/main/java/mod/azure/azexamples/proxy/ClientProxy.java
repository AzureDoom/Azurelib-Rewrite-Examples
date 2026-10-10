package mod.azure.azexamples.proxy;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.render.item.AzItemRendererRegistry;

import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.blocks.blockentity.StargateBlockRenderer;
import mod.azure.azexamples.entities.creeper.CreeperRenderer;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.doomhunter.DoomHunterRenderer;
import mod.azure.azexamples.entities.gremlin.GremlinEntity;
import mod.azure.azexamples.entities.gremlin.GremlinRenderer;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorRenderer;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.manul.ManulRenderer;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marauder.MarauderRenderer;
import mod.azure.azexamples.entities.marine.MarineEntity;
import mod.azure.azexamples.entities.marine.MarineRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.items.armors.DoomicornArmorRenderer;
import mod.azure.azexamples.items.diamondreplace.DiamondSwordRenderer;
import mod.azure.azexamples.items.diamondreplace.armor.DiamondArmorRenderer;
import mod.azure.azexamples.items.gunwitharm.GunWithArmRenderer;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        RenderingRegistry.registerEntityRenderingHandler(DoomHunterEntity.class, DoomHunterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MarauderEntity.class, MarauderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ManulEntity.class, ManulRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(JuravenatorEntity.class, JuravenatorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MarineEntity.class, MarineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(GremlinEntity.class, GremlinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeper.class, CreeperRenderer::new);
    }

    @Override
    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(StargateBlockEntity.class, new StargateBlockRenderer());

        AzItemRendererRegistry.register(Items.DIAMOND_SWORD, DiamondSwordRenderer::new);
        AzArmorRendererRegistry.register(
            DiamondArmorRenderer::new,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS
        );
        AzItemRendererRegistry.register(ItemRegistry.PISTOL, PistolRenderer::new);
        AzItemRendererRegistry.register(ItemRegistry.PEACEMAKER, GunWithArmRenderer::new);
        AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM, StargateBlockItemRenderer::new);
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ItemRegistry.DOOMICORN_HELMET,
            ItemRegistry.DOOMICORN_CHESTPLATE,
            ItemRegistry.DOOMICORN_LEGGINGS,
            ItemRegistry.DOOMICORN_BOOTS
        );
    }
}
