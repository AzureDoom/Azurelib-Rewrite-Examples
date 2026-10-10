package mod.azure.azexamples.entities.creeper;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.vertex.OverlayTexture;
import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.CommonMod;

public class CreeperRenderer extends AzEntityRenderer<EntityCreeper> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/possessed_engineer.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/possessed_engineer.png");

    public CreeperRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<EntityCreeper>builder(MODEL, TEXTURE)
                .setAnimatorProvider(CreeperAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setPrerenderEntry(contextPipeline -> {
                    doSwellOverlay(contextPipeline);
                    return contextPipeline;
                })
                .setShadowRadius(0.5F)
                .build(),
            renderManager
        );
    }

    private static void doSwellOverlay(AzRendererPipelineContext<UUID, EntityCreeper> contextPipeline) {
        EntityCreeper entity = contextPipeline.animatable();
        float partialTick = contextPipeline.partialTick();
        float swellFactor = entity.getCreeperFlashIntensity(partialTick);
        float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = (float) Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
        float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;
        boolean isSwellFactorEvenlyScaled = (swellFactor * 10) % 2 == 0;
        float normalizedSwellFactor = Mth.clamp(swellFactor, 0.5F, 1);
        boolean isEntityHurtOrDead = entity.hurtTime > 0 || entity.deathTime > 0;

        contextPipeline.setPackedOverlay(
            OverlayTexture.pack(
                OverlayTexture.u(isSwellFactorEvenlyScaled ? 0 : normalizedSwellFactor),
                OverlayTexture.v(isEntityHurtOrDead)
            )
        );
        contextPipeline.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);
    }
}
