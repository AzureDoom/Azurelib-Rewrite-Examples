package mod.azure.azexamples.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import mod.azure.azexamples.services.AzExampleServices;

public class StargateBlockItem extends BlockItem {

    private final StargateBlockItemAnimator dispatcher;

    public StargateBlockItem(Block block) {
        super(block, new Properties().tab(AzExampleServices.COMMON_REGISTRY.getCreativeTab()));
        this.dispatcher = new StargateBlockItemAnimator();
    }
}
