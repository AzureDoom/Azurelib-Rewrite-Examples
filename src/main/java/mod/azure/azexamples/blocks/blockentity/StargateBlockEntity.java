package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * 1.12.2 tile entities tick through {@link ITickable} instead of a block-provided ticker.
 */
public class StargateBlockEntity extends TileEntity implements ITickable {

    public final StargateBlockAnimationDispatcher animationDispatcher;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    @Override
    public void update() {
        if (this.world != null && this.world.isRemote) {
            this.animationDispatcher.serverSpin();
        }
    }
}
