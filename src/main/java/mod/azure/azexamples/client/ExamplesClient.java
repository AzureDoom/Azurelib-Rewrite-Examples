package mod.azure.azexamples.client;

import mod.azure.azexamples.entities.optimusprime.OptimusPrimeEntity;
import mod.azure.azexamples.entities.optimusprime.OptimusPrimeRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.render.item.AzItemRendererRegistry;
import mod.azure.azurelib.render.item.icon.AzItemIcons;
import mod.azure.azurelib.render.registry.AzBlockEntityRendererRegistry;
import mod.azure.azurelib.render.registry.AzEntityRendererRegistry;
import net.minecraft.entity.mob.monster.CreeperEntity;
import net.minecraft.item.Item;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;

import mod.azure.azexamples.CommonMod;
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
import mod.azure.azexamples.platform.ExMc;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.ItemRegistry;

/**
 * OSL {@code client-init} entrypoint. Entity and block entity renderers are registered straight away; item and armor
 * renderers wait for the examples' items (see {@link ItemRegistry#whenRegistered(Runnable)}).
 */
public final class ExamplesClient implements ClientModInitializer {

    @Override
    public void initClient() {
        AzEntityRendererRegistry.register(DoomHunterEntity.class, DoomHunterRenderer::new);
        AzEntityRendererRegistry.register(MarauderEntity.class, MarauderRenderer::new);
        AzEntityRendererRegistry.register(ManulEntity.class, ManulRenderer::new);
        AzEntityRendererRegistry.register(JuravenatorEntity.class, JuravenatorRenderer::new);
        AzEntityRendererRegistry.register(MarineEntity.class, MarineRenderer::new);
        AzEntityRendererRegistry.register(GremlinEntity.class, GremlinRenderer::new);
        AzEntityRendererRegistry.register(OptimusPrimeEntity.class, OptimusPrimeRenderer::new);
        // Replaces the vanilla creeper renderer, showing AzureLib driving a vanilla entity.
        AzEntityRendererRegistry.register(CreeperEntity.class, CreeperRenderer::new);

        AzBlockEntityRendererRegistry.register(StargateBlockEntity.class, StargateBlockRenderer::new);

        // Item and armor renderers need the items, which OSL's item register event creates; that may happen before
        // or after this entrypoint runs.
        ItemRegistry.whenRegistered(ExamplesClient::registerItemRenderers);
    }

    private static void registerItemRenderers() {
        // Inventory, held and dropped icons. Doomicorn armor gets its own; vanilla diamond armor is reskinned with the
        // cultist icons to match its AzureLib armor model.
        icon(ItemRegistry.DOOMICORN_HELMET, "doomicorn_helmet");
        icon(ItemRegistry.DOOMICORN_CHESTPLATE, "doomicorn_chestplate");
        icon(ItemRegistry.DOOMICORN_LEGGINGS, "doomicorn_leggings");
        icon(ItemRegistry.DOOMICORN_BOOTS, "doomicorn_boots");
        icon(ExMc.diamondHelmet(), "cultist_helmet");
        icon(ExMc.diamondChestplate(), "cultist_chestplate");
        icon(ExMc.diamondLeggings(), "cultist_leggings");
        icon(ExMc.diamondBoots(), "cultist_boots");

        // AzureLib can't derive an item's model JSON path (for Blockbench "display" transforms) from the item on Beta,
        // so it is given explicitly.
        AzItemRendererRegistry.register(
            ExMc.diamondSword(),
            CommonMod.modResource("models/item/diamond_sword.json"),
            DiamondSwordRenderer::new
        );
        AzItemRendererRegistry.register(
            ItemRegistry.PISTOL,
            CommonMod.modResource("models/item/pistol.json"),
            PistolRenderer::new
        );
        AzItemRendererRegistry.register(
            ItemRegistry.PEACEMAKER,
            CommonMod.modResource("models/item/peacemaker.json"),
            GunWithArmRenderer::new
        );
        AzItemRendererRegistry.register(
            BlockRegistry.stargateItem(),
            CommonMod.modResource("models/item/stargate.json"),
            StargateBlockItemRenderer::new
        );
        AzArmorRendererRegistry.register(
            DiamondArmorRenderer::new,
            ExMc.diamondHelmet(),
            ExMc.diamondChestplate(),
            ExMc.diamondLeggings(),
            ExMc.diamondBoots()
        );
        AzArmorRendererRegistry.register(
            DoomicornArmorRenderer::new,
            ItemRegistry.DOOMICORN_HELMET,
            ItemRegistry.DOOMICORN_CHESTPLATE,
            ItemRegistry.DOOMICORN_LEGGINGS,
            ItemRegistry.DOOMICORN_BOOTS
        );
    }

    private static void icon(Item item, String name) {
        AzItemIcons.register(item, CommonMod.modResource("textures/icons/item/" + name + ".png"));
    }
}
