package mod.azure.azexamples.blocks;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.azure.azexamples.platform.ExMc;
import mod.azure.azexamples.registry.BlockRegistry;

/**
 * The stargate's item: a plain item that places the block.
 * <p>
 * Why not a block item: Beta only creates block items for the vanilla blocks that exist when its block class first
 * loads, so a mod block has no item of its own. A plain item that places the block also takes the same AzureLib
 * rendering path as the pistol and diamond sword (GUI, first and third person, with its {@code display} transforms)
 * instead of Beta's 3D block-item path.
 */
public class StargateItem extends Item {

    private static final int[][] OFFSETS = {
        { 0, -1, 0 },
        { 0, 1, 0 },
        { 0, 0, -1 },
        { 0, 0, 1 },
        { -1, 0, 0 },
        { 1, 0, 0 } };

    public StargateItem(int id) {
        super(id);
    }

    @Override
    public boolean useOn(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (Mc.count(stack) <= 0) {
            return false;
        }

        int[] offset = OFFSETS[Math.max(0, Math.min(5, side))];
        int px = x + offset[0];
        int py = y + offset[1];
        int pz = z + offset[2];

        if (py < 0 || py >= 128 || Mc.blockId(world, px, py, pz) != 0) {
            return false;
        }

        if (ExMc.placeBlock(world, px, py, pz, BlockRegistry.STARGATE)) {
            ExMc.decrementStack(stack);
        }
        return true;
    }
}
