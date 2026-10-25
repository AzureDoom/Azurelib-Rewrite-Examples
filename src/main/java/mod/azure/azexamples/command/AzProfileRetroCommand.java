package mod.azure.azexamples.command;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.util.SharedCommandSource;

import java.util.Arrays;

/**
 * {@code /azprofile} as a RetroCommands command. Only loaded when RetroCommands is installed (see
 * {@link RetroCommandsCompat}), so the mod still runs without it.
 * <p>
 * API: written against RetroCommands 0.5.x's {@code Command} interface ({@code name}, {@code command}, {@code manual},
 * plus the optional {@code suggestion} hook). The optional hooks are deliberately not marked {@code @Override}, so this
 * compiles whether or not your version has them.
 */
public final class AzProfileRetroCommand implements Command {

    @Override
    public String name() {
        return AzProfilerCommands.NAME;
    }

    @Override
    public void command(SharedCommandSource source, String[] parameters) {
        AzProfilerCommands.execute(arguments(parameters), source::sendFeedback);
    }

    @Override
    public void manual(SharedCommandSource source) {
        source.sendFeedback("Usage: " + AzProfilerCommands.USAGE);
        source.sendFeedback(
            "Info: profiles AzureLib's animation and render stages; reports go to <game dir>/azurelibprofiler/"
        );
    }

    public String[] suggestion(SharedCommandSource source, int parameterNum, String currentInput, String totalInput) {
        String[] typed = arguments(totalInput.trim().split(" +"));
        return AzProfilerCommands.suggestions(Math.max(0, parameterNum - 1), typed);
    }

    public boolean disableInMultiplayer() {
        return true;
    }

    private static String[] arguments(String[] parameters) {
        if (parameters.length > 0) {
            String first = parameters[0].startsWith("/") ? parameters[0].substring(1) : parameters[0];
            if (first.equalsIgnoreCase(AzProfilerCommands.NAME)) {
                return Arrays.copyOfRange(parameters, 1, parameters.length);
            }
        }
        return parameters;
    }
}
