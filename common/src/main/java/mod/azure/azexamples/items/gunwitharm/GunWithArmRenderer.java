package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;

import mod.azure.azexamples.CommonMod;

public class GunWithArmRenderer extends AzItemRenderer {

    private static final Identifier MODEL = CommonMod.modResource("geo/item/peacemaker.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/item/peacemaker.png");

    public GunWithArmRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEXTURE)
                .setAnimatorProvider(GunWithArmAnimator::new)
                .disableAnimationInContexts(ItemDisplayContext.GUI)
                .build()
        );
    }
}
