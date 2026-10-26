package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Locale;

/**
 * 1.12.2 tile entities tick through {@link ITickable} instead of a block-provided ticker.
 */
public class StargateBlockEntity extends TileEntity implements ITickable {

    /**
     * The playback demo the gate is showing. Right-clicking the block steps through these in order, which also reads as
     * a natural story: dial in, shut down, try again and jam, rest.
     */
    public enum Mode {

        /** Closed and still. */
        IDLE,
        /** Sequence with per-stage reverse, ending on a ping-pong. */
        DIAL,
        /** Controller-level reverse: the kawoosh collapses back into the ring. */
        SHUTDOWN,
        /** Ring spin frozen partway through. */
        JAM;

        public Mode next() {
            StargateBlockEntity.Mode[] values = values();
            return values[(ordinal() + 1) % values.length];
        }

        public static Mode byId(int id) {
            StargateBlockEntity.Mode[] values = values();
            return id >= 0 && id < values.length ? values[id] : IDLE;
        }

        public String translationKey() {
            return "message.azexamples.stargate." + name().toLowerCase(Locale.ROOT);
        }
    }

    private static final String MODE_KEY = "mode";

    public final StargateBlockAnimationDispatcher animationDispatcher;

    private Mode mode = Mode.IDLE;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    public Mode getMode() {
        return mode;
    }

    /** Server side: advances to the next demo and syncs it to every client watching the gate. */
    public void cycleMode() {
        if (world == null || world.isRemote) {
            return;
        }

        mode = mode.next();
        markDirty();

        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger(MODE_KEY, mode.ordinal());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        mode = Mode.byId(tag.getInteger(MODE_KEY));
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(MODE_KEY, mode.ordinal());

        return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        NBTTagCompound tag = packet.getNbtCompound();
        mode = Mode.byId(tag.getInteger(MODE_KEY));

        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void update() {
        if (this.world != null && this.world.isRemote) {
            this.animationDispatcher.clientPlay(this.getMode());
        }
    }
}
