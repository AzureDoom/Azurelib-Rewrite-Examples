package mod.azure.azexamples.blocks.jukebox;

import mod.azure.azexamples.CommonMod;
import mod.azure.azurelib.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.render.block.AzBlockEntityRendererConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;

/**
 * Replaces how vanilla's jukebox looks: registered as the block entity renderer for
 * {@link net.minecraft.world.level.block.entity.BlockEntityTypes#JUKEBOX}, while {@code JukeboxBlockMixin} stops the
 * vanilla block model from drawing. The jukebox keeps all of its vanilla behavior; only the visuals change.
 */
public class JukeboxResonatorRenderer extends AzBlockEntityRenderer<JukeboxBlockEntity> {

    private static final Identifier MODEL = CommonMod.modResource("geo/block/resonator.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/block/resonator.png");

    public JukeboxResonatorRenderer() {
        super(
            AzBlockEntityRendererConfig.<JukeboxBlockEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(JukeboxResonatorAnimator::new)
                .build()
        );
    }
}
