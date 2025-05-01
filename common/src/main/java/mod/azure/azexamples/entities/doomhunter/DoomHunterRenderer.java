package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class DoomHunterRenderer extends AzEntityRenderer<DoomHunterEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/doomhunter.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/doomhunter.png");

    public DoomHunterRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<DoomHunterEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(DoomHunterAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build(),
            context
        );
    }
}
