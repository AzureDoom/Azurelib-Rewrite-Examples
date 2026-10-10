package mod.azure.azexamples.registry;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

import mod.azure.azexamples.items.PistolItem;
import mod.azure.azexamples.items.armors.DoomicornArmor;
import mod.azure.azexamples.items.gunwitharm.GunWithArmItem;

public final class ItemRegistry {

    public static final Item PISTOL = RegistryHelper.item(new PistolItem(), "pistol");

    public static final Item PEACEMAKER = RegistryHelper.item(new GunWithArmItem(), "peacemaker");

    public static final Item DOOMICORN_HELMET = RegistryHelper.item(
        new DoomicornArmor(EntityEquipmentSlot.HEAD),
        "doomicorn_helmet"
    );

    public static final Item DOOMICORN_CHESTPLATE = RegistryHelper.item(
        new DoomicornArmor(EntityEquipmentSlot.CHEST),
        "doomicorn_chestplate"
    );

    public static final Item DOOMICORN_LEGGINGS = RegistryHelper.item(
        new DoomicornArmor(EntityEquipmentSlot.LEGS),
        "doomicorn_leggings"
    );

    public static final Item DOOMICORN_BOOTS = RegistryHelper.item(
        new DoomicornArmor(EntityEquipmentSlot.FEET),
        "doomicorn_boots"
    );

    private ItemRegistry() {}
}
