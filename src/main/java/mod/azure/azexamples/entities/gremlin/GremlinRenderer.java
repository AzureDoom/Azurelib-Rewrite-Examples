package mod.azure.azexamples.entities.gremlin;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

public class GremlinRenderer extends AzEntityRenderer<GremlinEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/gremlin.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/gremlin.png");

    private static final ResourceLocation EXTRA_TEX = CommonMod.modResource("textures/entity/gremlin_cape.png");

    public GremlinRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<GremlinEntity>builder(MODEL, TEXTURE)
                .setBoneTextureOverrideProvider(
                    bone -> "bipedCape".equals(bone.getName()) ? EXTRA_TEX : null
                )
                .setShadowRadius(0.5F)
                .build(),
            renderManager
        );
    }
}
