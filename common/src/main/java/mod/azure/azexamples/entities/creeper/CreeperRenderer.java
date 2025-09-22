package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;

import mod.azure.azexamples.CommonMod;

public class CreeperRenderer extends AzEntityRenderer<Creeper> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/possessed_engineer.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/possessed_engineer.png");

    public CreeperRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<Creeper>builder(MODEL, TEXTURE)
                .setAnimatorProvider(CreeperAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setPrerenderEntry(contextPipeline -> {
                    doSwellOverlay(contextPipeline);

                    return contextPipeline;
                })
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }

    private static void doSwellOverlay(AzRendererPipelineContext<Creeper> contextPipeline) {
        var entity = contextPipeline.animatable();
        var partialTick = contextPipeline.partialTick();
        var swellFactor = entity.getSwelling(partialTick);
        var swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = (float) Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
        var horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        var verticalSwell = (1 + swellFactor * 0.1f) / swellMod;
        var isSwellFactorEvenlyScaled = (swellFactor * 10) % 2 == 0;
        var normalizedSwellFactor = Mth.clamp(swellFactor, 0.5F, 1);
        var isEntityHurtOrDead = entity.hurtTime > 0 || entity.deathTime > 0;

        contextPipeline.setPackedOverlay(
            OverlayTexture.pack(
                OverlayTexture.u(isSwellFactorEvenlyScaled ? 0 : normalizedSwellFactor),
                OverlayTexture.v(isEntityHurtOrDead)
            )
        );

        contextPipeline.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);
    }

}
