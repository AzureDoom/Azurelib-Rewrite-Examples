package mod.azure.azexamples.client;

import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.render.item.AzItemRendererRegistry;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

import java.lang.invoke.MethodHandles;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.blocks.StargateBlockItemRenderer;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.blocks.blockentity.StargateBlockRenderer;
import mod.azure.azexamples.blocks.jukebox.JukeboxResonatorRenderer;
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
import mod.azure.azexamples.entities.optimusprime.OptimusPrimeEntity;
import mod.azure.azexamples.entities.optimusprime.OptimusPrimeRenderer;
import mod.azure.azexamples.items.PistolRenderer;
import mod.azure.azexamples.items.armors.DoomicornArmorRenderer;
import mod.azure.azexamples.items.diamondreplace.DiamondSwordRenderer;
import mod.azure.azexamples.items.diamondreplace.armor.DiamondArmorRenderer;
import mod.azure.azexamples.items.gunwitharm.GunWithArmRenderer;
import mod.azure.azexamples.platform.ExMc;
import mod.azure.azexamples.registry.BlockRegistry;
import mod.azure.azexamples.registry.ExamplesEvents;
import mod.azure.azexamples.registry.ItemRegistry;

public final class ExamplesClientEvents {

    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @EventListener
    private static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(DoomHunterEntity.class, new DoomHunterRenderer());
        event.renderers.put(MarauderEntity.class, new MarauderRenderer());
        event.renderers.put(ManulEntity.class, new ManulRenderer());
        event.renderers.put(JuravenatorEntity.class, new JuravenatorRenderer());
        event.renderers.put(MarineEntity.class, new MarineRenderer());
        event.renderers.put(GremlinEntity.class, new GremlinRenderer());
        event.renderers.put(OptimusPrimeEntity.class, new OptimusPrimeRenderer());
        // Replaces the vanilla creeper renderer, showing AzureLib driving a vanilla entity.
        event.renderers.put(CreeperEntity.class, new CreeperRenderer());
    }

    @EventListener
    private static void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event) {
        event.renderers.put(StargateBlockEntity.class, new StargateBlockRenderer());
        event.renderers.put(JukeboxBlockEntity.class, new JukeboxResonatorRenderer());
    }

    @EventListener
    private static void registerTextures(TextureRegisterEvent event) {
        icon(ItemRegistry.DOOMICORN_HELMET, "item/doomicorn_helmet");
        icon(ItemRegistry.DOOMICORN_CHESTPLATE, "item/doomicorn_chestplate");
        icon(ItemRegistry.DOOMICORN_LEGGINGS, "item/doomicorn_leggings");
        icon(ItemRegistry.DOOMICORN_BOOTS, "item/doomicorn_boots");

        // The diamond armor is replaced by the cultist armor model, so give the vanilla items the cultist icons too.
        icon(ExMc.diamondHelmet(), "item/cultist_helmet");
        icon(ExMc.diamondChestplate(), "item/cultist_chestplate");
        icon(ExMc.diamondLeggings(), "item/cultist_leggings");
        icon(ExMc.diamondBoots(), "item/cultist_boots");

        registerAzureLibRenderers();
    }

    private static boolean azureLibRenderersRegistered;

    private static void registerAzureLibRenderers() {
        if (azureLibRenderersRegistered) {
            return;
        }
        azureLibRenderersRegistered = true;

        // Beta items have no registry names, so the item model JSON (for Blockbench "display" transforms) is given
        // explicitly.
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

    /**
     * Icon at {@code assets/azexamples/stationapi/textures/<path>.png}. MAPPING/API: StationAPI's
     * {@code StationRendererItem#setTexture(Identifier)}.
     */
    private static void icon(Item item, String path) {
        item.setTexture(ExamplesEvents.id(path));
    }
}
