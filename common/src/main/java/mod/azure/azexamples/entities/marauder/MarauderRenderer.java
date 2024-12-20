package mod.azure.azexamples.entities.marauder;

import mod.azure.azurelib.core2.render.entity.AzEntityRenderer;
import mod.azure.azurelib.core2.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.core2.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class MarauderRenderer extends AzEntityRenderer<MarauderEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/marauder.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/marauder.png");

    public MarauderRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<MarauderEntity>builder(MODEL, TEXTURE)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setAnimatorProvider(MarauderAnimator::new)
                        .setDeathMaxRotation(0F)
                        .build(),
                context
        );
    }
}
