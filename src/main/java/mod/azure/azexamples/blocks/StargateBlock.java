package mod.azure.azexamples.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

public class StargateBlock extends BlockContainer {

    public StargateBlock() {
        super(Material.iron);
        this.setStepSound(soundTypeMetal);
        this.setHardness(5.0F);
        this.setResistance(8.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new StargateBlockEntity();
    }

    /**
     * -1 means the block itself draws nothing; only the tile entity renderer does.
     */
    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean onBlockActivated(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer player,
        int side,
        float subX,
        float subY,
        float subZ
    ) {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof StargateBlockEntity) {
            StargateBlockEntity stargate = (StargateBlockEntity) world.getTileEntity(x, y, z);

            stargate.cycleMode();
            player.addChatComponentMessage(
                new ChatComponentTranslation(stargate.getMode().translationKey())
            );
        }

        return true;
    }
}
