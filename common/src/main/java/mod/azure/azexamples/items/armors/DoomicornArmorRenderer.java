package mod.azure.azexamples.items.armors;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.items.DoomArmorBoneProvider;

public class DoomicornArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/doomicorn.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/doomicorn.png");

    public DoomicornArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(DoomicornArmorAnimator::new)
                .setBoneProvider(new DoomArmorBoneProvider())
                .build()
        );
    }
}
