package mod.azure.azexamples.items.netheritereplace;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.core2.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.core2.animation.primitive.AzLoopType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NetheriteSwordAnimationDespatcher {

    private static final AzCommand OPENING_COMMAND = AzCommand.builder()
            .playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("opening", props -> props.withLoopType(AzLoopType.PLAY_ONCE))
                                    .queue("open", props -> props.withLoopType(AzLoopType.LOOP))
            )
            .build();

    public void serverOpening(Entity entity, ItemStack itemStack) {
        OPENING_COMMAND.sendForItem(entity, itemStack);
    }
}
