package mod.azure.azexamples.blocks.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.Locale;

import mod.azure.azexamples.blocks.StargateBlock;

public class StargateBlockEntity extends BlockEntity {

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

    private static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

    public final StargateBlockAnimationDispatcher animationDispatcher;

    private Mode mode = Mode.IDLE;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    public Mode getMode() {
        if (world == null) {
            return Mode.IDLE;
        }
        BlockState state = world.getBlockState(x, y, z);
        return state.contains(StargateBlock.MODE) ? Mode.byId(state.get(StargateBlock.MODE)) : Mode.IDLE;
    }

    /** Server side: advances to the next demo and syncs it to every client watching the gate. */
    public void cycleMode() {
        if (world == null || world.isRemote) {
            return;
        }

        BlockState state = world.getBlockState(x, y, z);
        if (!state.contains(StargateBlock.MODE)) {
            return;
        }

        int next = Mode.byId(state.get(StargateBlock.MODE)).next().ordinal();
        world.setBlockState(x, y, z, state.with(StargateBlock.MODE, next));
    }

    @Override
    public void tick() {
        if (CLIENT && this.world != null) {
            this.animationDispatcher.clientPlay(this.getMode());
        }
    }
}
