package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;

/**
 * The StargateBlockAnimationDispatcher class is responsible for managing and triggering animation commands for block
 * entities, specifically for the Stargate block entity.
 */
public class StargateBlockAnimationDispatcher {

    private static final AzCommand SPINNING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.SPIN_ANIMATION_NAME
    );

    private final StargateBlockEntity stargateBlockEntity;

    public StargateBlockAnimationDispatcher(StargateBlockEntity stargateBlockEntity) {
        this.stargateBlockEntity = stargateBlockEntity;
    }

    public void serverSpin() {
        SPINNING_COMMAND.sendForBlockEntity(stargateBlockEntity);
    }
}
