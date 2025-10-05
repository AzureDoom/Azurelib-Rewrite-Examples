package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;

import mod.azure.azexamples.CommonMod;

public class GunWithArmRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/item/peacemaker.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/peacemaker.png");

    public GunWithArmRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(GunWithArmAnimator::new)
                .disableAnimationInContexts(ItemTransforms.TransformType.GUI)
                .setShouldAnimateInContext(
                    context -> context != ItemTransforms.TransformType.GUI
                        && context != ItemTransforms.TransformType.FIXED
                ) // Custom animation logic with predicate
                .build()
        );
    }
}
