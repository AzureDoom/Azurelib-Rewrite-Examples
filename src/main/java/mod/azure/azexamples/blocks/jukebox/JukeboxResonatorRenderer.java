package mod.azure.azexamples.blocks.jukebox;

import net.minecraft.block.BlockJukebox;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.render.block.AzBlockEntityRendererConfig;

import mod.azure.azexamples.CommonMod;

/**
 * Replaces how vanilla's jukebox looks: bound as the tile entity special renderer for
 * {@link BlockJukebox.TileEntityJukebox}, while {@code JukeboxBlockMixin} stops the vanilla block model from drawing.
 * The jukebox keeps all of its vanilla behavior; only the visuals change.
 * <p>
 * Bind it from your client proxy:
 *
 * <pre>
 * {@code
 * ClientRegistry.bindTileEntitySpecialRenderer(BlockJukebox.TileEntityJukebox.class, new JukeboxResonatorRenderer());
 * }
 * </pre>
 */
public class JukeboxResonatorRenderer extends AzBlockEntityRenderer<BlockJukebox.TileEntityJukebox> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/block/resonator.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/block/resonator.png");

    public JukeboxResonatorRenderer() {
        super(
            AzBlockEntityRendererConfig.<BlockJukebox.TileEntityJukebox>builder(MODEL, TEXTURE)
                .setAnimatorProvider(JukeboxResonatorAnimator::new)
                .build()
        );
    }
}
