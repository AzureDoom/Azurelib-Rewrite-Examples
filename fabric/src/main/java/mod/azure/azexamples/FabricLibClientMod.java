package mod.azure.azexamples;

import net.fabricmc.api.ClientModInitializer;

public class FabricLibClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // TODO: Uncomment/fix when Azurelib 26.2 is finished.
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
        // EntityRenderers.register(EntityRegistry.DOOMHUNTER.get(), DoomHunterRenderer::new);
        // EntityRenderers.register(EntityRegistry.MARAUDER.get(), MarauderRenderer::new);
        // EntityRenderers.register(EntityRegistry.MANUL.get(), ManulRenderer::new);
        // EntityRenderers.register(EntityRegistry.JURAVENATOR.get(), JuravenatorRenderer::new);
        // EntityRenderers.register(EntityRegistry.MARINE.get(), MarineRenderer::new);
        // EntityRenderers.register(EntityRegistry.GREMLIN.get(), GremlinRenderer::new);
        // EntityRenderers.register(EntityTypes.CREEPER, CreeperRenderer::new);
        // BlockEntityRenderers.register(
        // EntityRegistry.STARGATE_BLOCK_ENTITY.get(),
        // (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new StargateBlockRenderer()
        // );

        // TODO: Find 26.2 replacement
        // BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.STARGATE.get(), RenderTypes.translucentMovingBlock());
    }
}
