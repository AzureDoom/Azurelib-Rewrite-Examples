package mod.azure.azexamples.mixins;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Hands the jukebox's visuals over to the AzureLib block entity renderer ({@code JukeboxResonatorRenderer}).
 * <ul>
 * <li>Render shape: vanilla draws the jukebox as a block model. {@code ENTITYBLOCK_ANIMATED} draws nothing for the
 * block itself, leaving it to the block entity renderer, and still uses the block's particles when it's broken.</li>
 * <li>Occlusion shape: vanilla's jukebox is a solid, opaque cube, which causes two problems for a model smaller than
 * a block: neighbouring blocks skip drawing the faces touching it (leaving see-through holes around the resonator),
 * and no light reaches inside it (so the resonator would render pitch black). An empty occlusion shape fixes both.
 * Collision, selection, redstone and mob spawning use other shapes and are unchanged.</li>
 * </ul>
 * Register in the common mixin list (not client-only): occlusion also drives lighting, which the server computes
 * too.
 */
@Mixin(JukeboxBlock.class)
public abstract class JukeboxBlockMixin extends BaseEntityBlock {

    protected JukeboxBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    /**
     * JukeboxBlock doesn't override this, so the mixin adds the override rather than injecting into it.
     */
    @Override
    protected @NotNull VoxelShape getOcclusionShape(
        @NotNull BlockState state
    ) {
        return Shapes.empty();
    }
}
