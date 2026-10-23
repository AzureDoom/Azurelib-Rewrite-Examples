package mod.azure.azexamples.profiler;

import net.minecraftforge.fml.common.Loader;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.profiling.AzProfiler;
import mod.azure.azurelib.profiling.AzProfilerListener;

public final class ProfilerControl {

    public static final String OUTPUT_DIR = "azurelibprofiler";

    private static ProfilerSession session;

    private ProfilerControl() {}

    public static boolean isRunning() {
        return session != null;
    }

    public static ProfilerSession session() {
        return session;
    }

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

    public static void reset() {
        if (session == null) {
            return;
        }

        session.stop();
        session = new ProfilerSession(Thread.currentThread());
        AzProfiler.setListener(session);
    }

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
        String stamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String base = stamp + (label == null || label.trim().isEmpty() ? "" : "_" + sanitize(label));
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
        return Loader.instance().getConfigDir().toPath().getParent().resolve(OUTPUT_DIR);
    }

    private static String sanitize(String label) {
        return label.trim().replaceAll("[^A-Za-z0-9._-]+", "-");
    }

    public static final class Result {

        private final ProfilerSession session;

        private final List<ReportRow> rows;

        private final Path report;

        private final Path jfr;

        Result(ProfilerSession session, List<ReportRow> rows, Path report, Path jfr) {
            this.session = session;
            this.rows = rows;
            this.report = report;
            this.jfr = jfr;
        }

        public ProfilerSession session() {
            return session;
        }

        public List<ReportRow> rows() {
            return rows;
        }

        public Path report() {
            return report;
        }

        public Path jfr() {
            return jfr;
        }
    }
}
