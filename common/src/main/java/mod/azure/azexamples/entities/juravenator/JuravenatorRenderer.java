package mod.azure.azexamples.entities.juravenator;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import mod.azure.azexamples.CommonMod;

/**
 * Credit to Collinvht of <a href="https://modrinth.com/mod/new-world-mod">New World</a> for the model/textures of this example.
 */
public class JuravenatorRenderer extends AzEntityRenderer<JuravenatorEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/juravenator.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/juravenator.png");

    public JuravenatorRenderer(
        EntityRendererProvider.Context context
    ) {
        super(
            AzEntityRendererConfig.<JuravenatorEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(JuravenatorAnimator::new)
                .build(),
            context
        );
    }

    @Override
    public void render(
        @NotNull JuravenatorEntity entity,
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
