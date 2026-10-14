package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.platform.mc.Mc;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.vertex.OverlayTexture;
import mod.azure.azurelib.util.ResourceLocation;
import mod.azure.azurelib.util.math.Mth;
import net.minecraft.entity.mob.CreeperEntity;

import java.util.UUID;

import mod.azure.azexamples.CommonMod;

public class CreeperRenderer extends AzEntityRenderer<CreeperEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/possessed_engineer.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/possessed_engineer.png");

    public CreeperRenderer() {
        super(
            AzEntityRendererConfig.<CreeperEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(CreeperAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setPrerenderEntry(contextPipeline -> {
                    doSwellOverlay(contextPipeline);
                    return contextPipeline;
                })
                .setShadowRadius(0.5F)
                .build()
        );
    }

    private static void doSwellOverlay(AzRendererPipelineContext<UUID, CreeperEntity> contextPipeline) {
        CreeperEntity entity = contextPipeline.animatable();
        float partialTick = contextPipeline.partialTick();
        float swelling = CreeperFuseTracker.swelling(entity, partialTick);

        float wobble = 1 + Mth.sin(swelling * 100f) * swelling * 0.01f;
        float grow = (float) Math.pow(Mth.clamp(swelling, 0f, 1f), 3);
        float horizontalSwell = (1 + grow * 0.4f) * wobble;
        float verticalSwell = (1 + grow * 0.1f) / wobble;
        contextPipeline.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);

        float whiteFlash = swelling <= 0 || (int) (swelling * 10.0F) % 2 == 0
            ? 0
            : Mth.clamp(swelling, 0.5F, 1.0F);
        boolean isEntityHurtOrDead = Mc.hurtTime(entity) > 0 || Mc.deathTime(entity) > 0;

        contextPipeline.setPackedOverlay(
            OverlayTexture.pack(OverlayTexture.u(whiteFlash), OverlayTexture.v(isEntityHurtOrDead))
        );
    }
}
