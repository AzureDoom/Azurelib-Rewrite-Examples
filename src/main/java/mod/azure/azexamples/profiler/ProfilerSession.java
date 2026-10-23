package mod.azure.azexamples.profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import mod.azure.azurelib.profiling.AzProfileStage;
import mod.azure.azurelib.profiling.AzProfilerListener;

public final class ProfilerSession implements AzProfilerListener {

    private static final int MAX_DEPTH = 64;

    private static final AzProfileStage[] STAGES = AzProfileStage.values();

    private static final Function<Object, Map<Object, StageStats>> NEW_INNER = key -> new HashMap<>();

    private static final Function<Object, StageStats> NEW_STATS = key -> new StageStats();

    private final Thread owner;

    private final long startNanos;

    private long stopNanos;

    private final Map<Object, Map<Object, StageStats>>[] stats;

    private final int[] stageStack = new int[MAX_DEPTH];

    private final long[] startStack = new long[MAX_DEPTH];

    private final long[] childStack = new long[MAX_DEPTH];

    private final Object[] keyStack = new Object[MAX_DEPTH];

    private final Object[] rootStack = new Object[MAX_DEPTH];

    private int depth;

    private int overflowDepth;

    private long unbalanced;

    private long overflowed;

    @SuppressWarnings("unchecked")
    public ProfilerSession(Thread owner) {
        this.owner = owner;
        this.stats = new Map[STAGES.length];

        for (int i = 0; i < STAGES.length; i++) {
            stats[i] = new HashMap<>();
        }

        this.startNanos = System.nanoTime();
    }

    @Override
    public void begin(AzProfileStage stage, Object subject) {
        if (Thread.currentThread() != owner || stopNanos != 0) {
            return;
        }

        if (depth == MAX_DEPTH) {
            overflowDepth++;
            overflowed++;
            return;
        }

        Object key = SubjectKeys.key(subject);
        stageStack[depth] = stage.ordinal();
        keyStack[depth] = key;
        rootStack[depth] = depth == 0 ? key : rootStack[depth - 1];
        childStack[depth] = 0;
        startStack[depth++] = System.nanoTime();
    }

    @Override
    public void end(AzProfileStage stage) {
        long now = System.nanoTime();

        if (Thread.currentThread() != owner || stopNanos != 0) {
            return;
        }

        if (overflowDepth > 0) {
            overflowDepth--;
            return;
        }

        int target = stage.ordinal();
        int index = depth - 1;

        while (index >= 0 && stageStack[index] != target) {
            index--;
        }

        if (index < 0) {
            unbalanced++;
            return;
        }

        if (index != depth - 1) {
            unbalanced += depth - 1 - index;
        }

        long total = now - startStack[index];
        long self = total - childStack[index];

        if (index > 0) {
            childStack[index - 1] += total;
        }

        stats[target].computeIfAbsent(rootStack[index], NEW_INNER)
            .computeIfAbsent(keyStack[index], NEW_STATS)
            .record(total, self);

        for (int i = index; i < depth; i++) {
            keyStack[i] = null;
            rootStack[i] = null;
        }

        depth = index;
    }

    public void stop() {
        if (stopNanos == 0) {
            stopNanos = System.nanoTime();
        }
    }

    public long wallNanos() {
        return (stopNanos == 0 ? System.nanoTime() : stopNanos) - startNanos;
    }

    public long unbalanced() {
        return unbalanced;
    }

    public long overflowed() {
        return overflowed;
    }

    public int entryCount() {
        int count = 0;

        for (Map<Object, Map<Object, StageStats>> byRoot : stats) {
            for (Map<Object, StageStats> bySubject : byRoot.values()) {
                count += bySubject.size();
            }
        }

        return count;
    }

    public List<ReportRow> rows() {
        List<ReportRow> rows = new ArrayList<>();

        for (int stage = 0; stage < STAGES.length; stage++) {
            for (Map.Entry<Object, Map<Object, StageStats>> byRoot : stats[stage].entrySet()) {
                String rootLabel = SubjectKeys.label(byRoot.getKey());

                for (Map.Entry<Object, StageStats> bySubject : byRoot.getValue().entrySet()) {
                    String subjectLabel = bySubject.getKey() == byRoot.getKey()
                        ? rootLabel
                        : rootLabel + " > " + SubjectKeys.label(bySubject.getKey());
                    StageStats s = bySubject.getValue();
                    rows.add(
                        new ReportRow(
                            STAGES[stage],
                            subjectLabel,
                            s.count,
                            s.totalNanos,
                            s.selfNanos,
                            s.percentile(0.50),
                            s.percentile(0.99),
                            s.maxNanos
                        )
                    );
                }
            }
        }

        return rows;
    }
}
