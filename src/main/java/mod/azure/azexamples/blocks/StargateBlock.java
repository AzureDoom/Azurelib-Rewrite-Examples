package mod.azure.azexamples.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

public class StargateBlock extends Block implements ITileEntityProvider {

    public StargateBlock() {
        super(Material.IRON);
        this.setSoundType(SoundType.METAL);
        this.setHardness(5.0F);
        this.setResistance(8.0F);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new StargateBlockEntity();
    }

    /**
     * Only the tile entity renderer draws this block.
     */
    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }
}
