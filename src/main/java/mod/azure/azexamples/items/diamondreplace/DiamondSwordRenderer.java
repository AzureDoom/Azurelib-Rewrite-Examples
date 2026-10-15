package mod.azure.azexamples.items.diamondreplace;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.util.ResourceLocation;

import mod.azure.azexamples.CommonMod;

/**
 * Replaces the vanilla diamond sword's look (Beta has no netherite). Its display transforms are read from
 * {@code assets/minecraft/models/item/diamond_sword.json}.
 */
public class DiamondSwordRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/crucible.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/crucible.png");

    public DiamondSwordRenderer() {
        super(
            AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build()
        );
    }
}
