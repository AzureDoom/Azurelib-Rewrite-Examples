package mod.azure.azexamples.blocks;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.block.BlockWithBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.registry.ItemRegistry;

public class StargateBlock extends BlockWithBlockEntity {

    public StargateBlock(int id) {
        super(id, Material.IRON);
        this.setStrength(5.0F);
        this.setBlastResistance(8.0F);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new StargateBlockEntity();
    }

    @Override
    public int getDropItem(int meta, Random random) {
        return Mc.itemId(ItemRegistry.STARGATE);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCube() {
        return false;
    }

    @Override
    public boolean use(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isMultiplayer) {
            int next = StargateBlockEntity.Mode.byId(world.getBlockMetadata(x, y, z)).next().ordinal();
            if (world.setBlockMetadataQuietly(x, y, z, next)) {
                world.notifyBlockChanged(x, y, z);
            }
        }

        return true;
    }
}
