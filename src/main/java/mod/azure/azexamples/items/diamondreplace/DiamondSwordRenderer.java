package mod.azure.azexamples.items.diamondreplace;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;

import mod.azure.azexamples.CommonMod;

/**
 * Replaces the vanilla diamond sword's look (1.12.2 has no netherite). The vanilla item model is overridden by
 * {@code assets/minecraft/models/item/diamond_sword.json}, which uses {@code builtin/entity}.
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
