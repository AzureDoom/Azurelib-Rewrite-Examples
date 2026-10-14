package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.util.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class ManulRenderer extends AzEntityRenderer<ManulEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/manul.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/manul.png");

    public ManulRenderer() {
        super(
            AzEntityRendererConfig.<ManulEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    contextPipeline.animatable().updateAnimations();
                    return contextPipeline;
                })
                .setAnimatorProvider(ManulAnimator::new)
                .setShadowRadius(0.5F)
                .build()
        );
    }
}
