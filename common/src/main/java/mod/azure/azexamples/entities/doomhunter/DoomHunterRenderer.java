package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.core2.render.entity.AzEntityRenderer;
import mod.azure.azurelib.core2.render.entity.AzEntityRendererConfig;
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
                        .build(),
                context
        );
    }
}
