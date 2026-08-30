package mod.azure.azexamples.items;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class PistolRenderer extends AzItemRenderer {

    private static final Identifier MODEL = CommonMod.modResource("geo/item/pistol.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/item/pistol.png");

    public PistolRenderer() {
        super(
            AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                .setAnimatorProvider(PistolAnimator::new)
                .build()
        );
    }
}
