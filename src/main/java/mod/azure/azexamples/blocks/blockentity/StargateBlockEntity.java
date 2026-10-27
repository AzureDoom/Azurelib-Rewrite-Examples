package mod.azure.azexamples.blocks.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;

import java.util.Locale;

public class StargateBlockEntity extends BlockEntity {

    /**
     * The playback demo the gate is showing. Right-clicking the block steps through these in order, which also reads as
     * a natural story: dial in, shut down, try again and jam, rest. Stored as the block's metadata, which vanilla saves
     * with the chunk and syncs to clients, so the block entity only ever reads it.
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
            Mode[] values = values();
            return values[(ordinal() + 1) % values.length];
        }

        public static Mode byId(int id) {
            Mode[] values = values();
            return id >= 0 && id < values.length ? values[id] : IDLE;
        }

        public String translationKey() {
            return "message.azexamples.stargate." + name().toLowerCase(Locale.ROOT);
        }
    }

    private static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

    public final StargateBlockAnimationDispatcher animationDispatcher;

    public StargateBlockEntity() {
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    public Mode getMode() {
        return world == null ? Mode.IDLE : Mode.byId(world.getBlockMetadata(x, y, z));
    }

    @Override
    public void tick() {
        if (CLIENT && world != null) {
            this.animationDispatcher.clientPlay(this.getMode());
        }
    }
}
