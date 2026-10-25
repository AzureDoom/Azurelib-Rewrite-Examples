package mod.azure.azexamples.client;

import net.fabricmc.api.ClientModInitializer;

import mod.azure.azexamples.command.RetroCommandsCompat;

public final class ExamplesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RetroCommandsCompat.init();
    }
}
