package mod.azure.azexamples.items;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class TestRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/test.geo.json");

    private static final ResourceLocation TEX = CommonMod.modResource("textures/item/test.png");

    public TestRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEX)
                .build()
        );
    }
}
