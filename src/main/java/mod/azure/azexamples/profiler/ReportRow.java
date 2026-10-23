package mod.azure.azexamples.profiler;

import mod.azure.azurelib.profiling.AzProfileStage;

public final class ReportRow {

    private final AzProfileStage stage;

    private final String subject;

    private final long calls;

    private final long totalNanos;

    private final long selfNanos;

    private final long p50Nanos;

    private final long p99Nanos;

    private final long maxNanos;

    public ReportRow(
        AzProfileStage stage,
        String subject,
        long calls,
        long totalNanos,
        long selfNanos,
        long p50Nanos,
        long p99Nanos,
        long maxNanos
    ) {
        this.stage = stage;
        this.subject = subject;
        this.calls = calls;
        this.totalNanos = totalNanos;
        this.selfNanos = selfNanos;
        this.p50Nanos = p50Nanos;
        this.p99Nanos = p99Nanos;
        this.maxNanos = maxNanos;
    }

    public AzProfileStage stage() {
        return stage;
    }

    public String subject() {
        return subject;
    }

    public long calls() {
        return calls;
    }

    public long totalNanos() {
        return totalNanos;
    }

    public long selfNanos() {
        return selfNanos;
    }

    public long p50Nanos() {
        return p50Nanos;
    }

    public long p99Nanos() {
        return p99Nanos;
    }

    public long maxNanos() {
        return maxNanos;
    }

    public double meanNanos() {
        return calls == 0 ? 0 : (double) totalNanos / calls;
    }
}
