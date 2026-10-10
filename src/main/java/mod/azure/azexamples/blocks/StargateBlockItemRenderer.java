package mod.azure.azexamples.blocks;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;

import mod.azure.azexamples.CommonMod;

public class StargateBlockItemRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/block/stargate.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/block/stargate.png");

    public StargateBlockItemRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEXTURE).setAnimatorProvider(StargateBlockItemAnimator::new).build()
        );
    }
}
