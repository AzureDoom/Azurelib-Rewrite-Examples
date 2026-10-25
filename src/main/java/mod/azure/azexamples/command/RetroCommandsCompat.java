package mod.azure.azexamples.command;

import com.matthewperiut.retrocommands.api.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Optional integration with RetroCommands. Call {@link #init()} from your entrypoint; the RetroCommands classes are
 * only touched when the mod is present, as RetroCommands asks dependants to do.
 */
public final class RetroCommandsCompat {

    private RetroCommandsCompat() {}

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {
            Registration.register();
        }
    }

    /** Separate class so RetroCommands' types are only resolved when it is installed. */
    private static final class Registration {

        static void register() {
            CommandRegistry.add(new AzProfileRetroCommand());
        }
    }
}
