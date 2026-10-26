package mod.azure.azexamples.entities.marine;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

public class MarineRenderer extends AzEntityRenderer<MarineEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/marine.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/marine.png");

    public MarineRenderer() {
        super(
            AzEntityRendererConfig.<MarineEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(MarineAnimator::new)
                .addRenderLayer(new MarineArmorLayer())
                .addRenderLayer(new MarineItemLayer())
                .setShadowRadius(0.5F)
                .build()
        );
    }
}
