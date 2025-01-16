package mod.azure.azexamples.items.netheritereplace.armor;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.items.DoomArmorBoneProvider;

public class NetheriteArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/cultist_armor.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/cultist_armor.png");

    /**
     * Constructs a new renderer for the Doomicorn Armor. <br>
     * The configuration includes:
     * <ul>
     * <li>A {@code DoomicornArmorAnimator} for handling animations specific to the Doomicorn Armor.</li>
     * <li>A {@code DoomicornArmorBoneProvider} to correctly map model bones for animation.</li>
     * </ul>
     */
    public NetheriteArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(NetheriteArmorAnimator::new)
                .setBoneProvider(new DoomArmorBoneProvider())
                .build()
        );
    }
}
