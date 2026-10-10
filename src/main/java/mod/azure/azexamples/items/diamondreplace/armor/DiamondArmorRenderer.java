package mod.azure.azexamples.items.diamondreplace.armor;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.items.DoomArmorBoneProvider;

/**
 * Replaces the vanilla diamond armor's look (1.12.2 has no netherite).
 */
public class DiamondArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/cultist_armor.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/cultist_armor.png");

    public DiamondArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(DiamondArmorAnimator::new)
                .setBoneProvider(new DoomArmorBoneProvider())
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build()
        );
    }
}
