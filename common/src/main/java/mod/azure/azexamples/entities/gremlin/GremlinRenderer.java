package mod.azure.azexamples.entities.gremlin;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class GremlinRenderer extends AzEntityRenderer<GremlinEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource(
        "geo/entity/gremlin.geo.json"
    );

    private static final ResourceLocation TEXTURE = CommonMod.modResource(
        "textures/entity/gremlin.png"
    );

    private static final ResourceLocation EXTRA_TEX = CommonMod.modResource(
        "textures/entity/gremlin_cape.png"
    );

    public GremlinRenderer(
        EntityRendererProvider.Context context
    ) {
        super(
            AzEntityRendererConfig.<GremlinEntity>builder(MODEL, TEXTURE)
                .setBoneTextureOverrideProvider(
                    bone -> "bipedCape".equals(bone.getName()) ? EXTRA_TEX : TEXTURE
                )
                .setBoneRenderTypeOverrideProvider(
                    bone -> "bipedCape".equals(bone.getName())
                        ? RenderType.entityTranslucent(EXTRA_TEX)
                        : RenderType.entityCutout(TEXTURE)
                )
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }
}
