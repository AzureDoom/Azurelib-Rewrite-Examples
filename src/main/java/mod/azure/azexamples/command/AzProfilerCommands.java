package mod.azure.azexamples.command;

import mod.azure.azurelib.AzureLib;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

import mod.azure.azexamples.jfr.JfrRecorder;
import mod.azure.azexamples.profile.ProfilerControl;
import mod.azure.azexamples.profile.ReportWriter;

/**
 * The {@code /azprofile} command logic, independent of any command library:
 * <ul>
 * <li>{@code start} / {@code start jfr}: begin a session, optionally with a JFR recording alongside</li>
 * <li>{@code stop [label]}: end the session and write reports to {@code <game dir>/azurelibprofiler/}</li>
 * <li>{@code reset}: discard data collected so far and keep going</li>
 * <li>{@code status}: show elapsed time and entry count</li>
 * </ul>
 * Beta has no text components, so feedback is plain text ({@code §} colour codes work).
 */
public final class AzProfilerCommands {

    public static final String NAME = "azprofile";

    public static final String USAGE = "/azprofile <start [jfr] | stop [label] | reset | status>";

    private static final String[] SUBCOMMANDS = { "start", "stop", "reset", "status" };

    private static final int SUMMARY_ROWS = 8;

    private AzProfilerCommands() {}

    /**
     * Runs the command.
     *
     * @param args the arguments after the command name, e.g. {@code ["stop", "my", "label"]}
     * @return 1 on success, 0 on failure (like a Brigadier result)
     */
    public static int execute(String[] args, Consumer<String> feedback) {
        if (args.length == 0) {
            feedback.accept("§7Usage: " + USAGE);
            return 0;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "start":
                return start(feedback, args.length > 1 && args[1].equalsIgnoreCase("jfr"));
            case "stop":
                String label = args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : null;
                return stop(feedback, label);
            case "reset":
                return reset(feedback);
            case "status":
                return status(feedback);
            default:
                feedback.accept("§cUnknown subcommand: " + args[0]);
                feedback.accept("§7Usage: " + USAGE);
                return 0;
        }
    }

    /** Tab-completion candidates for the argument at {@code index} (0 = the subcommand). */
    public static String[] suggestions(int index, String[] args) {
        if (index == 0) {
            return SUBCOMMANDS.clone();
        }
        if (index == 1 && args.length > 0 && args[0].equalsIgnoreCase("start")) {
            return new String[] { "jfr" };
        }
        return new String[0];
    }

    private static int start(Consumer<String> feedback, boolean withJfr) {
        if (!ProfilerControl.start()) {
            feedback.accept("AzureLib profiler is already running.");
            return 0;
        }

        if (withJfr) {
            try {
                JfrRecorder.start();
            } catch (Exception e) {
                AzureLib.LOGGER.warn("Could not start JFR recording", e);
                feedback.accept("JFR unavailable (" + e.getMessage() + "), timing only.");
            }
        }

        feedback.accept("AzureLib profiler started" + (JfrRecorder.isRecording() ? " with JFR." : "."));
        return 1;
    }

    private static int stop(Consumer<String> feedback, String label) {
        try {
            var result = ProfilerControl.stop(label);

            if (result == null) {
                feedback.accept("AzureLib profiler is not running.");
                return 0;
            }

            var session = result.session();
            feedback.accept(
                String.format(Locale.ROOT, "Profiled %.1f s. Top stages by self time:", session.wallNanos() / 1e9)
            );

            for (var row : result.rows().subList(0, Math.min(SUMMARY_ROWS, result.rows().size()))) {
                feedback.accept(
                    String.format(
                        Locale.ROOT,
                        "  %5.2f%%  %s  %s  (p99 %.0f us)",
                        ReportWriter.threadPercent(row, session),
                        row.stage(),
                        row.subject(),
                        row.p99Nanos() / 1e3
                    )
                );
            }

            feedback.accept("Report: " + result.report());

            if (result.jfr() != null) {
                feedback.accept("JFR: " + result.jfr());
            }

            return 1;
        } catch (Exception e) {
            AzureLib.LOGGER.error("Failed to write AzureLib profiler report", e);
            feedback.accept("Failed to write report: " + e.getMessage());
            return 0;
        }
    }

    private static int reset(Consumer<String> feedback) {
        if (!ProfilerControl.isRunning()) {
            feedback.accept("AzureLib profiler is not running.");
            return 0;
        }

        ProfilerControl.reset();
        feedback.accept("AzureLib profiler data reset.");
        return 1;
    }

    private static int status(Consumer<String> feedback) {
        var session = ProfilerControl.session();

        if (session == null) {
            feedback.accept("AzureLib profiler is not running.");
            return 0;
        }

        feedback.accept(
            String.format(
                Locale.ROOT,
                "Running %.1f s, %d entries, %d unbalanced%s",
                session.wallNanos() / 1e9,
                session.entryCount(),
                session.unbalanced(),
                JfrRecorder.isRecording() ? ", JFR on" : ""
            )
        );
        return 1;
    }
}
