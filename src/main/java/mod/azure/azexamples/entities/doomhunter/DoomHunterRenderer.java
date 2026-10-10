package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;

import mod.azure.azexamples.CommonMod;

public class DoomHunterRenderer extends AzEntityRenderer<DoomHunterEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/doomhunter.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/doomhunter.png");

    public DoomHunterRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<DoomHunterEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    if (!contextPipeline.animatable().isAggressive()) {
                        contextPipeline.animatable().animationDispatcher.clientIdle();
                    }
                    contextPipeline.animatable().updateAnimations();
                    return contextPipeline;
                })
                .setAnimatorProvider(DoomHunterAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setShadowRadius(3.0F)
                .build(),
            renderManager
        );
    }
}
