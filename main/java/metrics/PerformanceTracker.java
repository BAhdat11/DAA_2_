package metrics;

import java.util.concurrent.atomic.AtomicLong;

public class PerformanceTracker {
    private final AtomicLong comparisons = new AtomicLong();
    private final AtomicLong swaps = new AtomicLong();
    private final AtomicLong accesses = new AtomicLong();
    private long lastDurationNanos = 0;

    public void reset() {
        comparisons.set(0);
        swaps.set(0);
        accesses.set(0);
        lastDurationNanos = 0;
    }

    public void countComparisons(long delta) { comparisons.addAndGet(delta); }
    public void countSwaps(long delta) { swaps.addAndGet(delta); }
    public void countAccesses(long delta) { accesses.addAndGet(delta); }

    public long getComparisons() { return comparisons.get(); }
    public long getSwaps() { return swaps.get(); }
    public long getAccesses() { return accesses.get(); }

    public void setLastDurationNanos(long nanos) { this.lastDurationNanos = nanos; }
    public long getLastDurationNanos() { return lastDurationNanos; }
}
