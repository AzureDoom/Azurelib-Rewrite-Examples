package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.common.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.common.util.client.RenderUtils;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class DoomHunterRenderer extends AzEntityRenderer<DoomHunterEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/doomhunter.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/doomhunter.png");

    public DoomHunterRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<DoomHunterEntity>builder(MODEL, TEXTURE)
                .setRenderEntry(contextPipeline -> {
                    RenderUtils.faceRotation(
                        contextPipeline.poseStack(),
                        contextPipeline.animatable(),
                        contextPipeline.partialTick()
                    );

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
