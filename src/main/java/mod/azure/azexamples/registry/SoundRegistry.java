package mod.azure.azexamples.registry;

import net.minecraft.util.SoundEvent;

import mod.azure.azexamples.CommonMod;

public final class SoundRegistry {

    public static final SoundEvent SHOOT_GUN = create("vigilance_fire");

    private SoundRegistry() {}

    private static SoundEvent create(String name) {
        SoundEvent event = new SoundEvent(CommonMod.modResource(name));
        event.setRegistryName(CommonMod.modResource(name));
        return event;
    }
}
