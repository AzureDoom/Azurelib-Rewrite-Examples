package mod.azure.azexamples.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public class StargateBlockItem extends BlockItem {

    private final StargateBlockItemAnimator dispatcher;

    public StargateBlockItem(Block block, CreativeModeTab tab) {
        super(block, new Properties().tab(tab));
        this.dispatcher = new StargateBlockItemAnimator();
    }
}
