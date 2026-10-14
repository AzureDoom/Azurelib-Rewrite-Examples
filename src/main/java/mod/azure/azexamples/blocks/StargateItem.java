package mod.azure.azexamples.blocks;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

import mod.azure.azexamples.platform.ExMc;
import mod.azure.azexamples.registry.BlockRegistry;

/**
 * The stargate's item: a plain item that places the block.
 * <p>
 * Why not the block's own item: StationAPI's Arsenic renderer gives block items the block's JSON model and draws them
 * itself in the GUI, first person and third person, cancelling before AzureLib's item hooks run. Plain items without a
 * JSON model use Arsenic's vanilla fallback, which is where AzureLib's hooks take over (the same path the pistol and
 * diamond sword use), so this item gets AzureLib rendering everywhere, including its {@code display} transforms.
 */
public class StargateItem extends Item {

    private static final int[][] OFFSETS = {
        { 0, -1, 0 },
        { 0, 1, 0 },
        { 0, 0, -1 },
        { 0, 0, 1 },
        { -1, 0, 0 },
        { 1, 0, 0 } };

    public StargateItem(Identifier identifier) {
        super(ItemTemplate.getNextId());
        ItemTemplate.onConstructor(this, identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
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
