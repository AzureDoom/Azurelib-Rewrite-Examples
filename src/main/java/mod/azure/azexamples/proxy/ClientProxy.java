package mod.azure.azexamples.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;

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
    public void init() {
        // 1.7.10 renderers are instances; Minecraft gives them the RenderManager when they're registered.
        RenderingRegistry.registerEntityRenderingHandler(DoomHunterEntity.class, new DoomHunterRenderer());
        RenderingRegistry.registerEntityRenderingHandler(MarauderEntity.class, new MarauderRenderer());
        RenderingRegistry.registerEntityRenderingHandler(ManulEntity.class, new ManulRenderer());
        RenderingRegistry.registerEntityRenderingHandler(JuravenatorEntity.class, new JuravenatorRenderer());
        RenderingRegistry.registerEntityRenderingHandler(MarineEntity.class, new MarineRenderer());
        RenderingRegistry.registerEntityRenderingHandler(GremlinEntity.class, new GremlinRenderer());
        // Replaces the vanilla creeper renderer, showing AzureLib driving a vanilla entity.
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeper.class, new CreeperRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(StargateBlockEntity.class, new StargateBlockRenderer());

        // These also register Forge IItemRenderers for the items.
        AzItemRendererRegistry.register(Items.diamond_sword, DiamondSwordRenderer::new);
        AzItemRendererRegistry.register(ItemRegistry.PISTOL, PistolRenderer::new);
        AzItemRendererRegistry.register(ItemRegistry.PEACEMAKER, GunWithArmRenderer::new);
        AzItemRendererRegistry.register(BlockRegistry.stargateItem(), StargateBlockItemRenderer::new);

        AzArmorRendererRegistry.register(
            DiamondArmorRenderer::new,
            Items.diamond_helmet,
            Items.diamond_chestplate,
            Items.diamond_leggings,
            Items.diamond_boots
        );
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ItemRegistry.DOOMICORN_HELMET,
            ItemRegistry.DOOMICORN_CHESTPLATE,
            ItemRegistry.DOOMICORN_LEGGINGS,
            ItemRegistry.DOOMICORN_BOOTS
        );
    }
}
