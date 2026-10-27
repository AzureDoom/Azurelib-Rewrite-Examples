package mod.azure.azexamples.blocks.jukebox;

import mod.azure.azurelib.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.render.block.AzBlockEntityRendererConfig;
import mod.azure.azurelib.util.ResourceLocation;
import net.minecraft.block.entity.JukeboxBlockEntity;

import mod.azure.azexamples.CommonMod;

/**
 * Replaces how vanilla's jukebox looks: bound as the tile entity special renderer for {@link JukeboxBlockEntity}, while
 * {@code JukeboxBlockMixin} stops the vanilla block model from drawing. The jukebox keeps all of its vanilla behavior;
 * only the visuals change.
 */
public class JukeboxResonatorRenderer extends AzBlockEntityRenderer<JukeboxBlockEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/block/resonator.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/block/resonator.png");

    public JukeboxResonatorRenderer() {
        super(
            AzBlockEntityRendererConfig.<JukeboxBlockEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(JukeboxResonatorAnimator::new)
                .build()
        );
    }
}
