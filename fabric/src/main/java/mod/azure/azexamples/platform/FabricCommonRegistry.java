package mod.azure.azexamples.platform;

import mod.azure.azexamples.ExampleRegistry;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.CreativeModeTab;

import mod.azure.azexamples.FabricLibMod;
import mod.azure.azexamples.services.CommonRegistry;

public class FabricCommonRegistry implements CommonRegistry {

    @Override
    public BlockEntityType<StargateBlockEntity> stargateBlockEntity() {
        return ExampleRegistry.STARGATE_BLOCK_ENTITY;
    }

	@Override
	public SoundEvent firingSound() {
		return ExampleRegistry.SHOOT_GUN;
	}

	@Override
    public CreativeModeTab getCreativeTab() {
        return FabricLibMod.EXAMPLEMOD_TAB;
    }
}
