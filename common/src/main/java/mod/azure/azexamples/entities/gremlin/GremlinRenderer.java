package mod.azure.azexamples.entities.gremlin;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzCustomBoneTextureLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class GremlinRenderer extends AzEntityRenderer<GremlinEntity> {

    private static final Identifier MODEL = CommonMod.modResource(
        "geo/entity/gremlin.geo.json"
    );

    private static final Identifier TEXTURE = CommonMod.modResource(
        "textures/entity/gremlin.png"
    );

    private static final Identifier EXTRA_TEX = CommonMod.modResource(
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
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }
}
