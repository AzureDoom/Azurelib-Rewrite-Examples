package mod.azure.azexamples.items;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class PistolRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/pistol.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/pistol.png");

    public PistolRenderer() {
        super(
            AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                .setAnimatorProvider(PistolAnimator::new)
                .build()
        );
    }
}
