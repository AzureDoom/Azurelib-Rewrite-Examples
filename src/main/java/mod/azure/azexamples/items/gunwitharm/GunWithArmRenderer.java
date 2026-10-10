package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.item.AzItemDisplayContext;
import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;

import mod.azure.azexamples.CommonMod;

public class GunWithArmRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/peacemaker.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/peacemaker.png");

    public GunWithArmRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(GunWithArmAnimator::new)
                .disableAnimationInContexts(AzItemDisplayContext.GUI)
                .setShouldAnimateInContext(
                    context -> context != AzItemDisplayContext.GUI
                        && context != AzItemDisplayContext.FIXED
                ) // Custom animation logic with predicate
                .build()
        );
    }
}
