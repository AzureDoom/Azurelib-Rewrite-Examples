package mod.azure.azexamples.registry;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.item.Item;
import net.ornithemc.osl.core.api.util.NamespacedIdentifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.blocks.StargateItem;
import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;
import mod.azure.azexamples.items.spawnegg.ExampleSpawnEggItem;
import mod.azure.azexamples.platform.ExMc;

/**
 * The examples' items, registered through OSL's Items API.
 * <p>
 * {@link #register()} runs from OSL's {@code ItemEvents.REGISTER_ITEMS}, right after Vanilla's items, so the fields are
 * only set from then on. Anything that needs the items (AzureLib identity and renderer registration) goes through
 * {@link #whenRegistered(Runnable)}, which works whether it is called before or after the event has fired. Numeric ids
 * are assigned by OSL.
 */
public final class ItemRegistry {

    private static final Logger LOGGER = LogManager.getLogger(CommonStrings.MOD_NAME);

    public static Item PISTOL;

    public static Item PEACEMAKER;

    public static Item STARGATE;

    public static Item DOOMICORN_HELMET;

    public static Item DOOMICORN_CHESTPLATE;

    public static Item DOOMICORN_LEGGINGS;

    public static Item DOOMICORN_BOOTS;

    public static final Map<String, Item> SPAWN_EGGS = new LinkedHashMap<>();

    private static final List<Runnable> AFTER_REGISTRATION = new ArrayList<>();

    private static boolean registered;

    private ItemRegistry() {}

    public static void register() {
        PISTOL = register("pistol", new PistolItem(Item.AUTO_ASSIGN_ID));
        PEACEMAKER = register("peacemaker", new GunWithArmItem(Item.AUTO_ASSIGN_ID));
        STARGATE = register("stargate", new StargateItem(Item.AUTO_ASSIGN_ID));
        // Beta armor slots: 0 helmet, 1 chestplate, 2 leggings, 3 boots.
        DOOMICORN_HELMET = register("doomicorn_helmet", new DoomicornArmor(Item.AUTO_ASSIGN_ID, 0));
        DOOMICORN_CHESTPLATE = register("doomicorn_chestplate", new DoomicornArmor(Item.AUTO_ASSIGN_ID, 1));
        DOOMICORN_LEGGINGS = register("doomicorn_leggings", new DoomicornArmor(Item.AUTO_ASSIGN_ID, 2));
        DOOMICORN_BOOTS = register("doomicorn_boots", new DoomicornArmor(Item.AUTO_ASSIGN_ID, 3));

        for (EntityRegistry.Entry entry : EntityRegistry.entries()) {
            String name = entry.name + "_spawn_egg";
            SPAWN_EGGS.put(entry.name, register(name, new ExampleSpawnEggItem(Item.AUTO_ASSIGN_ID, entry.type)));
        }

        // AzureLib item identities need the items to exist.
        CommonMod.init();

        List<Runnable> pending;
        synchronized (AFTER_REGISTRATION) {
            registered = true;
            pending = new ArrayList<>(AFTER_REGISTRATION);
            AFTER_REGISTRATION.clear();
        }
        for (Runnable action : pending) {
            action.run();
        }
    }

    /** Runs {@code action} once the items exist: right away if they already do, otherwise right after registration. */
    public static void whenRegistered(Runnable action) {
        synchronized (AFTER_REGISTRATION) {
            if (!registered) {
                AFTER_REGISTRATION.add(action);
                return;
            }
        }
        action.run();
    }

    /** Registers the item under {@code azexamples:<name>} and gives it the {@code item.azexamples.<name>} name key. */
    private static <T extends Item> T register(String name, T item) {
        net.ornithemc.osl.items.api.ItemRegistry.register(NamespacedIdentifiers.from(CommonStrings.MOD_ID, name), item);
        LOGGER.info("{}:{} -> item id {}", CommonStrings.MOD_ID, name, Mc.itemId(item));
        ExMc.name(item, name);
        return item;
    }
}
