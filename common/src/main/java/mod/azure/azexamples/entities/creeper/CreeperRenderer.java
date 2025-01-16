package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererPipeline;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;

import mod.azure.azexamples.CommonMod;

public class CreeperRenderer extends AzEntityRenderer<Creeper> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/possessedengineer.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/possessedengineer.png");

    public CreeperRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<Creeper>builder(MODEL, TEXTURE)
                .setAnimatorProvider(CreeperAnimator::new)
                .build(),
            context
        );
    }

    @Override
    protected AzEntityRendererPipeline<Creeper> createPipeline(AzEntityRendererConfig<Creeper> config) {
        return new AzEntityRendererPipeline<Creeper>(config, this) {

            @Override
            public void preRender(AzRendererPipelineContext<Creeper> context, boolean isReRender) {
                super.preRender(context, isReRender);

                var swellFactor = context.animatable().getSwelling(context.partialTick());
                var swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
                swellFactor = (float) Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
                var horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
                var verticalSwell = (1 + swellFactor * 0.1f) / swellMod;

                context.setPackedOverlay(
                    OverlayTexture.pack(
                        OverlayTexture.u(
                            getSwellOverlay(context.animatable(), context.partialTick())
                        ),
                        OverlayTexture.v(context.animatable().hurtTime > 0 || context.animatable().deathTime > 0)
                    )
                );
                context.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);
            }

            protected float getSwellOverlay(Creeper entity, float partialTick) {
                var swell = entity.getSwelling(partialTick);

                return (int) (swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
            }
        };
    }
}
