package mod.azure.azexamples.entities.juravenator;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

public class JuravenatorRenderer extends AzEntityRenderer<JuravenatorEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/juravenator.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/juravenator.png");

    public JuravenatorRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<JuravenatorEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    contextPipeline.animatable().updateAnimations();
                    return contextPipeline;
                })
                .setAnimatorProvider(JuravenatorAnimator::new)
                .setShadowRadius(0.5F)
                .build(),
            renderManager
        );
    }
}
