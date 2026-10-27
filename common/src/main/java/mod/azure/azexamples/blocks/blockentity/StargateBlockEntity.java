package mod.azure.azexamples.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import mod.azure.azexamples.registry.EntityRegistry;

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
            var values = values();
            return values[(ordinal() + 1) % values.length];
        }

        public static Mode byId(int id) {
            var values = values();
            return id >= 0 && id < values.length ? values[id] : IDLE;
        }

        public String translationKey() {
            return "message.azexamples.stargate." + name().toLowerCase(Locale.ROOT);
        }
    }

    private static final String MODE_KEY = "mode";

    public final StargateBlockAnimationDispatcher animationDispatcher;

    private Mode mode = Mode.IDLE;

    public StargateBlockEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.STARGATE_BLOCK_ENTITY.get(), pos, blockState);
        this.animationDispatcher = new StargateBlockAnimationDispatcher(this);
    }

    public Mode getMode() {
        return mode;
    }

    /** Server side: advances to the next demo and syncs it to every client watching the gate. */
    public void cycleMode() {
        mode = mode.next();
        setChanged();

        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(MODE_KEY, mode.ordinal());
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        mode = Mode.byId(tag.getInt(MODE_KEY));
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveCustomOnly(registries);
    }

    /**
     * Handles the tick behavior for the StargateBlockEntity, triggering server-side spinning animations if the block
     * entity and level instance are valid and the method is executed on the client side.
     *
     * @param level       The current level or world instance.
     * @param pos         The position of the block in the world.
     * @param state       The current block state of the associated block.
     * @param blockEntity The StargateBlockEntity instance to operate on.
     */
    public static void tick(Level level, BlockPos pos, BlockState state, StargateBlockEntity blockEntity) {
        if (blockEntity.level != null && level.isClientSide()) {
            blockEntity.animationDispatcher.clientPlay(blockEntity.getMode());
        }
    }
}
