package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azurelib.rewrite.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.rewrite.render.block.AzBlockEntityRendererConfig;
import net.minecraft.resources.ResourceLocation;

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
