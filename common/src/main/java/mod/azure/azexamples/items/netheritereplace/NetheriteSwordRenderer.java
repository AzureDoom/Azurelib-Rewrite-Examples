package mod.azure.azexamples.items.netheritereplace;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class NetheriteSwordRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/crucible.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/crucible.png");

    public NetheriteSwordRenderer() {
        super(
            AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build()
        );
    }
}
