package mod.azure.azexamples.profiler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JfrRecorder {

    private static final Class<?> RECORDING_CLASS = find("jdk.jfr.Recording");

    private static final Class<?> CONFIGURATION_CLASS = find("jdk.jfr.Configuration");

    private static Object recording;

    private static boolean leakMode;

    private JfrRecorder() {}

    private static Class<?> find(String name) {
        try {
            return Class.forName(name);
        } catch (Throwable e) {
            return null;
        }
    }

    public static boolean isAvailable() {
        return RECORDING_CLASS != null && CONFIGURATION_CLASS != null;
    }

    public static boolean isRecording() {
        return recording != null;
    }

    public static boolean isLeakMode() {
        return recording != null && leakMode;
    }

    public static void start(boolean leaks) throws Exception {
        if (!isAvailable()) {
            throw new IllegalStateException("this Java runtime has no JFR (needs Java 8u272+ or 11+)");
        }

        if (recording != null) {
            return;
        }

        Method getConfiguration = CONFIGURATION_CLASS.getMethod("getConfiguration", String.class);
        Object configuration = getConfiguration.invoke(null, "profile");
        Constructor<?> constructor = RECORDING_CLASS.getConstructor(CONFIGURATION_CLASS);
        Object created = constructor.newInstance(configuration);

        RECORDING_CLASS.getMethod("setName", String.class).invoke(created, "AzureLib Profiler");

        if (leaks) {
            Object settings = RECORDING_CLASS.getMethod("enable", String.class).invoke(created, "jdk.OldObjectSample");
            Method with = settings.getClass().getMethod("with", String.class, String.class);
            with.setAccessible(true);
            with.invoke(settings, "cutoff", "infinity");
        }

        RECORDING_CLASS.getMethod("start").invoke(created);
        recording = created;
        leakMode = leaks;
    }

    public static void stop(Path file) throws Exception {
        if (recording == null) {
            return;
        }

        Object current = recording;
        recording = null;
        leakMode = false;

        try {
            RECORDING_CLASS.getMethod("stop").invoke(current);
            Files.createDirectories(file.getParent());
            RECORDING_CLASS.getMethod("dump", Path.class).invoke(current, file);
        } finally {
            RECORDING_CLASS.getMethod("close").invoke(current);
        }
    }
}
