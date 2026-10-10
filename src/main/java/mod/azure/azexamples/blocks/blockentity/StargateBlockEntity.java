package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.tileentity.TileEntity;

/**
 * 1.7.10 tile entities tick through {@link #updateEntity()}.
 */
public class StargateBlockEntity extends TileEntity {

    public final StargateBlockAnimationDispatcher animationDispatcher;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    @Override
    public void updateEntity() {
        if (this.worldObj != null && this.worldObj.isRemote) {
            this.animationDispatcher.serverSpin();
        }
    }
}
