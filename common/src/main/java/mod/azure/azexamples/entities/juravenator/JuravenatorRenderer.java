package mod.azure.azexamples.entities.juravenator;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to Collinvht of <a href="https://modrinth.com/mod/new-world-mod">New World</a> for the model/textures of this
 * example.
 */
public class JuravenatorRenderer extends AzEntityRenderer<JuravenatorEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/juravenator.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/juravenator.png");

    public JuravenatorRenderer(
        EntityRendererProvider.Context context
    ) {
        super(
            AzEntityRendererConfig.<JuravenatorEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    contextPipeline.animatable().updateAnimations();

                    return contextPipeline;
                })
                .setAnimatorProvider(JuravenatorAnimator::new)
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }
}
