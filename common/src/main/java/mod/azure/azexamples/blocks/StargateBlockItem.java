package mod.azure.azexamples.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class StargateBlockItem extends BlockItem {

    private final StargateBlockItemAnimator dispatcher;

    public StargateBlockItem(Block block) {
        super(block, new Properties());
        this.dispatcher = new StargateBlockItemAnimator();
    }
}
