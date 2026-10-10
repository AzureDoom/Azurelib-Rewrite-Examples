package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.render.block.AzBlockEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

public class StargateBlockRenderer extends AzBlockEntityRenderer<StargateBlockEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/block/stargate.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/block/stargate.png");

    public StargateBlockRenderer() {
        super(
            AzBlockEntityRendererConfig.<StargateBlockEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(StargateBlockEntityAnimator::new)
                .build()
        );
    }
}
