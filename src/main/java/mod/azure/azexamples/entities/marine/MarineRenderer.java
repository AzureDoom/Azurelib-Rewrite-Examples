package mod.azure.azexamples.entities.marine;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

public class MarineRenderer extends AzEntityRenderer<MarineEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/marine.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/marine.png");

    public MarineRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<MarineEntity>builder(MODEL, TEXTURE)
                .addRenderLayer(new MarineArmorLayer())
                .addRenderLayer(new MarineItemLayer())
                .setShadowRadius(0.5F)
                .build(),
            renderManager
        );
    }
}
