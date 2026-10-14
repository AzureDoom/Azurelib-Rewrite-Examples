package mod.azure.azexamples.registry;

import net.minecraft.item.Item;

import mod.azure.azexamples.blocks.StargateBlock;

public final class BlockRegistry {

    public static StargateBlock STARGATE;

    private BlockRegistry() {}

    public static void register() {
        STARGATE = new StargateBlock(ExamplesEvents.id("stargate"));
        ExamplesEvents.name(STARGATE, "stargate");
    }

    public static Item stargateItem() {
        return ItemRegistry.STARGATE;
    }
}
