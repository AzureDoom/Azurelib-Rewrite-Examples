package mod.azure.azexamples.items;

import mod.azure.azurelib.core2.render.item.AzItemRenderer;
import mod.azure.azurelib.core2.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class PistolRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/pistol.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/pistol.png");

    public PistolRenderer() {
        super(
                AzItemRendererConfig.builder(MODEL, TEXTURE).setAnimatorProvider(PistolAnimator::new).build()
        );
    }
}
