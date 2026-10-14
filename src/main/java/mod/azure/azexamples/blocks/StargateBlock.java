package mod.azure.azexamples.blocks;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.registry.ItemRegistry;

public class StargateBlock extends BlockWithEntity {

    public StargateBlock(Identifier identifier) {
        super(BlockTemplate.getNextId(), Material.METAL);
        BlockTemplate.onConstructor(this, identifier);
        this.setHardness(5.0F);
        this.setResistance(8.0F);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new StargateBlockEntity();
    }

    @Override
    public int getDroppedItemId(int meta, java.util.Random random) {
        return ItemRegistry.STARGATE.id;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }
}
