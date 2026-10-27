package mod.azure.azexamples.mixins;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hands the jukebox's visuals over to the AzureLib tile entity renderer ({@code JukeboxResonatorRenderer}).
 * <ul>
 * <li>Render type: vanilla draws the jukebox as a block model. {@code ENTITYBLOCK_ANIMATED} draws nothing for the block
 * itself, leaving it to the tile entity renderer, and still uses the block's particles when it's broken.</li>
 * <li>Opacity: vanilla's jukebox is an opaque cube, which causes two problems for a model smaller than a block:
 * neighbouring blocks skip drawing the faces touching it (Forge's {@code doesSideBlockRendering} defaults to
 * {@code isOpaqueCube}), leaving see-through holes around the resonator, and it blocks all light, so the resonator
 * would render pitch black. In 1.12 both come from {@code isOpaqueCube}: the {@code Block} constructor reads it to set
 * {@code fullBlock} and {@code lightOpacity}, and because this override is merged into {@code BlockJukebox} itself, the
 * constructor already sees {@code false}. Collision, the selection box and redstone are unchanged.</li>
 * </ul>
 * Register in the common mixin list (not client-only): light opacity also drives lighting, which the server computes
 * too.
 */
@Mixin(BlockJukebox.class)
public abstract class JukeboxBlockMixin extends BlockContainer {

    protected JukeboxBlockMixin(Material material) {
        super(material);
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void azexamples$renderWithTileEntity(IBlockState state, CallbackInfoReturnable<EnumBlockRenderType> cir) {
        cir.setReturnValue(EnumBlockRenderType.ENTITYBLOCK_ANIMATED);
    }

    /**
     * BlockJukebox doesn't override this, so the mixin adds the override rather than injecting into it.
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
