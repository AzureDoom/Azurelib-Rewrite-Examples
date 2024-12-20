package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.azurelib.core2.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.core2.render.block.AzBlockEntityRendererConfig;
import net.minecraft.resources.ResourceLocation;

public class StargateBlockRenderer  extends AzBlockEntityRenderer<StargateBlockEntity> {

    private static final ResourceLocation MODEL = AzureLib.modResource("geo/block/stargate.geo.json");

    private static final ResourceLocation TEXTURE = AzureLib.modResource("textures/block/stargate.png");

    public StargateBlockRenderer() {
        super(
                AzBlockEntityRendererConfig.<StargateBlockEntity>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(StargateBlockEntityAnimator::new)
                        .build()
        );
    }
}