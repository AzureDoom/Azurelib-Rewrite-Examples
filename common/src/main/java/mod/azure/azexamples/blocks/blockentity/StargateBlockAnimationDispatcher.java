package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azurelib.core2.animation.dispatch.AzDispatcher;
import mod.azure.azurelib.core2.animation.dispatch.command.AzDispatchCommand;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StargateBlockAnimationDispatcher {

    private static final String SPIN_ANIMATION_NAME = "spinning";

    private static final AzDispatchCommand SPINNING_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", SPIN_ANIMATION_NAME)
        .build();

    public void serverSpin(BlockEntity entity) {
        AzDispatcher.fromClient(SPINNING_COMMAND).sendForBlockEntity(entity);
    }
}
