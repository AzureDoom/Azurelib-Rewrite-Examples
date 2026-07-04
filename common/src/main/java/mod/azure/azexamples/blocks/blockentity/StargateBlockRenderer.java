package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class StargateBlockRenderer { // extends AzBlockEntityRenderer<StargateBlockEntity> {

    private static final Identifier MODEL = CommonMod.modResource("geo/block/stargate.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/block/stargate.png");

    public StargateBlockRenderer() {
        // super(
        // AzBlockEntityRendererConfig.<StargateBlockEntity>builder(MODEL, TEXTURE)
        // .setAnimatorProvider(StargateBlockEntityAnimator::new)
        // .build()
        // );
    }
}
