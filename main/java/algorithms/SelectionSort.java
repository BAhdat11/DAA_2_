package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

public class SelectionSort {
    private final PerformanceTracker tracker;

    public SelectionSort() {
        this.tracker = new PerformanceTracker();
    }

    public SelectionSort(PerformanceTracker tracker) {
        this.tracker = Objects.requireNonNull(tracker);
    }

    public void sort(int[] arr) {
        tracker.reset();
        if (arr == null) throw new IllegalArgumentException("Input array cannot be null");
        int n = arr.length;
        tracker.countAccesses(1);

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                tracker.countComparisons(1);
                tracker.countAccesses(2);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    private void swap(int[] arr, int a, int b) {
        tracker.countAccesses(3);
        tracker.countSwaps(1);
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }
}
