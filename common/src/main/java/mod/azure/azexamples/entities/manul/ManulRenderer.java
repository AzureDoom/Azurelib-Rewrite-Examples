package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to Crystalblackpaws for the model and texture of this example.
 */
public class ManulRenderer extends AzEntityRenderer<ManulEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/manul.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/manul.png");

    public ManulRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<ManulEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    contextPipeline.animatable().updateAnimations();

                    return contextPipeline;
                })
                .setAnimatorProvider(ManulAnimator::new)
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }
}
