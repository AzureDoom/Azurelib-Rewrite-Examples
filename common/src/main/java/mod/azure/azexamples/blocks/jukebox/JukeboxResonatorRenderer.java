package mod.azure.azexamples.blocks.jukebox;

import mod.azure.azurelib.common.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.common.render.block.AzBlockEntityRendererConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;

import mod.azure.azexamples.CommonMod;

/**
 * Replaces how vanilla's jukebox looks: registered as the block entity renderer for
 * {@link net.minecraft.world.level.block.entity.BlockEntityType#JUKEBOX}, while {@code JukeboxBlockMixin} stops the
 * vanilla block model from drawing. The jukebox keeps all of its vanilla behavior; only the visuals change.
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
