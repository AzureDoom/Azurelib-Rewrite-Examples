package mod.azure.azexamples.profile;

import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.profiling.AzProfiler;
import mod.azure.azurelib.profiling.AzProfilerListener;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.jfr.JfrRecorder;

/**
 * Owns the active session. The listener is only installed while a session runs, so AzureLib's hooks cost nothing
 * otherwise. All methods are meant to be called from the client thread (chat commands run there).
 */
public final class ProfilerControl {

    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private static ProfilerSession session;

    private ProfilerControl() {}

    public static boolean isRunning() {
        return session != null;
    }

    public static ProfilerSession session() {
        return session;
    }

    /** Starts a session on the calling thread. Returns {@code false} if one is already running. */
    public static boolean start() {
        if (session != null) {
            return false;
        }

        session = new ProfilerSession(Thread.currentThread());
        AzProfilerListener previous = AzProfiler.setListener(session);

        if (previous != null) {
            AzureLib.LOGGER.warn("Replaced an existing AzureLib profiler listener: {}", previous);
        }

        return true;
    }

    /** Discards the collected data and keeps profiling. */
    public static void reset() {
        if (session == null) {
            return;
        }

        session.stop();
        session = new ProfilerSession(Thread.currentThread());
        AzProfiler.setListener(session);
    }

    /**
     * Stops the session and writes its reports, plus the JFR recording if one is running.
     *
     * @param label optional name added to the file names, for telling runs apart
     */
    public static Result stop(String label) throws Exception {
        if (session == null) {
            return null;
        }

        ProfilerSession finished = session;
        finished.stop();

        if (AzProfiler.getListener() == finished) {
            AzProfiler.setListener(null);
        }

        session = null;

        Path dir = outputDirectory();
        String base = STAMP.format(LocalDateTime.now()) + (label == null || label.isBlank()
            ? ""
            : "_" + sanitize(label));
        List<ReportRow> rows = ReportWriter.sortedRows(finished);
        Path txt = dir.resolve(base + ".txt");
        ReportWriter.write(finished, rows, dir.resolve(base + ".csv"), txt);

        Path jfr = null;

        if (JfrRecorder.isRecording()) {
            jfr = dir.resolve(base + ".jfr");
            JfrRecorder.stop(jfr);
        }

        return new Result(finished, rows, txt, jfr);
    }

    public static Path outputDirectory() {
        return FabricLoader.getInstance().getGameDir().resolve(CommonStrings.MOD_ID);
    }

    private static String sanitize(String label) {
        return label.trim().replaceAll("[^A-Za-z0-9._-]+", "-");
    }

    public record Result(
        ProfilerSession session,
        List<ReportRow> rows,
        Path report,
        Path jfr
    ) {}
}
