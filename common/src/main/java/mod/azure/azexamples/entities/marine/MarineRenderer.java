package mod.azure.azexamples.entities.marine;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class MarineRenderer { // extends AzEntityRenderer<MarineEntity> {

    private static final Identifier MODEL = CommonMod.modResource("geo/entity/marine.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/entity/marine.png");

    public MarineRenderer(EntityRendererProvider.Context context) {
        // super(
        // AzEntityRendererConfig.<MarineEntity>builder(MODEL, TEXTURE)
        // .addRenderLayer(new MarineArmorLayer())
        // .addRenderLayer(new MarineItemLayer())
        // .setShadowRadius(0.5F)
        // .build(),
        // context
        // );
    }
}
