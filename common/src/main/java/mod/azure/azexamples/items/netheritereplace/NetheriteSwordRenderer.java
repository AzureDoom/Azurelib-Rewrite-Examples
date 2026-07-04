package mod.azure.azexamples.items.netheritereplace;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class NetheriteSwordRenderer { // extends AzItemRenderer {

    private static final Identifier MODEL = CommonMod.modResource("geo/item/crucible.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/item/crucible.png");

    public NetheriteSwordRenderer() {
        // super(
        // AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
        // .setAnimatorProvider(NetheriteSwordAnimator::new)
        // .addRenderLayer(new AzAutoGlowingLayer<>())
        // .build()
        // );
    }
}
