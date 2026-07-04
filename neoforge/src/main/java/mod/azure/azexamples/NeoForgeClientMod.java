package mod.azure.azexamples;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = CommonStrings.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientMod {

    // TODO: Solve for 26.2
    // @SubscribeEvent
    // public static void onClientSetup(final FMLClientSetupEvent event) {
    // AzItemRendererRegistry.register(Items.NETHERITE_SWORD, NetheriteSwordRenderer::new);
    // AzArmorRendererRegistry.register(
    // NetheriteArmorRenderer::new,
    // Items.NETHERITE_HELMET,
    // Items.NETHERITE_CHESTPLATE,
    // Items.NETHERITE_LEGGINGS,
    // Items.NETHERITE_BOOTS
    // );
    // AzItemRendererRegistry.register(ItemRegistry.PISTOL.get(), PistolRenderer::new);
    // AzItemRendererRegistry.register(ItemRegistry.PEACEMAKER.get(), GunWithArmRenderer::new);
    // AzItemRendererRegistry.register(BlockRegistry.STARGATE_ITEM.get(), StargateBlockItemRenderer::new);
    // AzArmorRendererRegistry.register(
    // DoomicornArmorRenderer::new,
    // ItemRegistry.DOOMICORN_HELMET.get(),
    // ItemRegistry.DOOMICORN_CHESTPLATE.get(),
    // ItemRegistry.DOOMICORN_LEGGINGS.get(),
    // ItemRegistry.DOOMICORN_BOOTS.get()
    // );
    // ItemBlockRenderTypes.setRenderLayer(BlockRegistry.STARGATE.get(), RenderTypes.translucentMovingBlock());
    // }
    //
    // @SubscribeEvent
    // public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
    // event.registerEntityRenderer(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
    // event.registerEntityRenderer(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
    // event.registerEntityRenderer(EntityRegistry.MANUL.get(), ManulRenderer::new);
    // event.registerEntityRenderer(EntityRegistry.JURAVENATOR.get(), JuravenatorRenderer::new);
    // event.registerEntityRenderer(EntityRegistry.MARINE.get(), MarineRenderer::new);
    // event.registerEntityRenderer(EntityRegistry.GREMLIN.get(), GremlinRenderer::new);
    // event.registerBlockEntityRenderer(
    // EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
    // (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
    // );
    // event.registerEntityRenderer(EntityTypes.CREEPER, CreeperRenderer::new);
    // }
}
