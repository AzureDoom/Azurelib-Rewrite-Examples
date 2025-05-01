package mod.azure.azexamples.entities.manul;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

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
                .setAnimatorProvider(ManulAnimator::new)
                .build(),
            context
        );
    }

    @Override
    public void render(
        @NotNull ManulEntity entity,
        float entityYaw,
        float partialTick,
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource bufferSource,
        int packedLight
    ) {
        entity.updateAnimations();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
