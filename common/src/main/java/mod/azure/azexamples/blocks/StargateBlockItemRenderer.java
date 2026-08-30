package mod.azure.azexamples.blocks;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class StargateBlockItemRenderer extends AzItemRenderer {

    private static final Identifier MODEL = CommonMod.modResource("geo/block/stargate.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/block/stargate.png");

    /**
     * A custom renderer for the Stargate block item, extending the base functionality provided by the AzItemRenderer
     * class. This renderer is responsible for rendering the Stargate block item in the game world using a specified
     * model, texture, and animator configuration. The StargateBlockItemRenderer uses a pre-defined model and texture
     * for the Stargate block item. It also incorporates an animation system provided through the
     * StargateBlockItemAnimator, enabling dynamic and interactive animations for the item. The
     * {@code AzItemRendererConfig} is used to configure the renderer with the associated model, texture, and animator.
     */
    public StargateBlockItemRenderer() {
        super(
            AzItemRendererConfig.builder(MODEL, TEXTURE).setAnimatorProvider(StargateBlockItemAnimator::new).build()
        );
    }
}
