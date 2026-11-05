package mod.azure.azexamples.entities.meshtest;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

/**
 * One renderer for every mesh test; the model comes from the entity's {@link MeshTestVariant}.
 */
public class MeshTestRenderer extends AzEntityRenderer<MeshTestEntity> {

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/mesh_test.png");

    public MeshTestRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<MeshTestEntity>builder(entity -> entity.variant().model(), entity -> TEXTURE)
                .setAnimatorProvider(MeshTestAnimator::new)
                .setRenderType(entity -> RenderType.entityCutoutNoCull(TEXTURE))
                .setShadowRadius(0.4F)
                .build(),
            context
        );
    }
}
