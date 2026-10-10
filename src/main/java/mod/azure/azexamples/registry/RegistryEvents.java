package mod.azure.azexamples.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.blocks.blockentity.StargateBlockEntity;

@Mod.EventBusSubscriber(modid = CommonStrings.MOD_ID)
public final class RegistryEvents {

    private RegistryEvents() {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(BlockRegistry.STARGATE);
        GameRegistry.registerTileEntity(StargateBlockEntity.class, CommonMod.modResource("stargate_block_entity"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        BlockRegistry.STARGATE_ITEM.getClass();
        ItemRegistry.PISTOL.getClass();
        for (Item item : RegistryHelper.ITEMS) {
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        for (EntityEntry entry : EntityRegistry.ENTRIES) {
            event.getRegistry().register(entry);
        }
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(SoundRegistry.SHOOT_GUN);
    }
}
