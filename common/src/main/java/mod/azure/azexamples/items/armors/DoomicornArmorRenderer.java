package mod.azure.azexamples.items.armors;

import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class DoomicornArmorRenderer { // extends AzArmorRenderer {

    private static final Identifier MODEL = CommonMod.modResource("geo/item/doomicorn.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/item/doomicorn.png");

    public DoomicornArmorRenderer() {
        // super(
        // AzArmorRendererConfig.builder(MODEL, TEXTURE)
        // .setAnimatorProvider(DoomicornArmorAnimator::new)
        // .setBoneProvider(new DoomArmorBoneProvider())
        // .build()
        // );
    }
}
