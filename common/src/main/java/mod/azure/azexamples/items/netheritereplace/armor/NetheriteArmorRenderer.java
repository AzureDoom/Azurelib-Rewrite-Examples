package mod.azure.azexamples.items.netheritereplace.armor;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.items.DoomArmorBoneProvider;

public class NetheriteArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/cultist_armor.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/cultist_armor.png");

    public NetheriteArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(NetheriteArmorAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setBoneProvider(new DoomArmorBoneProvider())
                .build()
        );
    }
}
