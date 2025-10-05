package mod.azure.azexamples.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.services.AzExampleServices;

public class SoundRegistry {

    public static Supplier<SoundEvent> SHOOT_GUN = registerSound(
        "vigilance_fire"
    );

    static Supplier<SoundEvent> registerSound(String soundName) {
        return AzExampleServices.COMMON_REGISTRY.register(
            BuiltInRegistries.SOUND_EVENT,
            soundName,
            () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource(soundName))
        );
    }

    public static void initialize() {}
}
