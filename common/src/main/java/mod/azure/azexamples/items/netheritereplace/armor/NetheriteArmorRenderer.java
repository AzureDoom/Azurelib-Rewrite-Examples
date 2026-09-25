package mod.azure.azexamples.items.netheritereplace.armor;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.items.DoomArmorBoneProvider;

public class NetheriteArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/armor/cultist_armor.geo.json");

    private static final ResourceLocation MODEL2 = CommonMod.modResource("geo/armor/doomicorn.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/cultist_armor.png");

    private static final ResourceLocation TEXTURE2 = CommonMod.modResource("textures/item/doomicorn.png");

    public NetheriteArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(((entity, itemStack) -> {
                if (entity instanceof Player) {
                    return MODEL2;
                }
                return MODEL;
            }), ((entity, itemStack) -> {
                if (entity instanceof Player) {
                    return TEXTURE2;
                }
                return TEXTURE;
            }))
                .setAnimatorProvider(NetheriteArmorAnimator::new)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .setBoneProvider(new DoomArmorBoneProvider())
                .build()
        );
    }
}
