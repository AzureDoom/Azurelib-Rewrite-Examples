package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class DoomHunterRenderer extends AzEntityRenderer<DoomHunterEntity> {

    private static final Identifier MODEL = CommonMod.modResource("geo/entity/doomhunter.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/entity/doomhunter.png");

    public DoomHunterRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<DoomHunterEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    if (!contextPipeline.animatable().isAggressive()) {
                        contextPipeline.animatable().animationDispatcher.clientIdle();
                    }

                    return contextPipeline;
                })
                .setAnimatorProvider(DoomHunterAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setShadowRadius(3.0F)
                .build(),
            context
        );
    }
}
