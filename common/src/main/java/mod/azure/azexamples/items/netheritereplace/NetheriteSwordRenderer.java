package mod.azure.azexamples.items.netheritereplace;

import mod.azure.azexamples.CommonMod;
import mod.azure.azurelib.core2.render.item.AzItemRenderer;
import mod.azure.azurelib.core2.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

public class NetheriteSwordRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/crucible.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/crucible.png");

    public NetheriteSwordRenderer() {
        super(
                AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                        .setAnimatorProvider(NetheriteSwordAnimator::new)
                        .build()
        );
    }
}
