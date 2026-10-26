package mod.azure.azexamples.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;

import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import mod.azure.azexamples.registry.ItemRegistry;

public class StargateBlock extends BlockWithEntity {

    public static final IntProperty MODE = IntProperty.of("mode", 0, StargateBlockEntity.Mode.values().length - 1);

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

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            BlockState state = world.getBlockState(x, y, z);
            int next = StargateBlockEntity.Mode.byId(state.get(MODE)).next().ordinal();
            world.setBlockState(x, y, z, state.with(MODE, next));
            world.blockUpdateEvent(x, y, z);
            player.sendMessage(StargateBlockEntity.Mode.byId(next).translationKey());
        }

        return true;
    }
}
