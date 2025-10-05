package cli;

import algorithms.SelectionSort;
import metrics.PerformanceTracker;
import java.io.FileWriter;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage: <n> <distribution> <runs> [outputCsv]");
            System.exit(1);
        }
        int n = Integer.parseInt(args[0]);
        String dist = args[1];
        int runs = Integer.parseInt(args[2]);
        String out = args.length >= 4 ? args[3] : null;

        PerformanceTracker acc = new PerformanceTracker();
        SelectionSort sorter = new SelectionSort(acc);

        StringBuilder csv = new StringBuilder();
        csv.append("run,n,duration_ns,comparisons,swaps,accesses\n");

        for (int r = 0; r < runs; r++) {
            int[] arr = generateArray(n, dist, r);

            acc.reset();
            long t0 = System.nanoTime();
            sorter.sort(arr);
            long t1 = System.nanoTime();
            acc.setLastDurationNanos(t1 - t0);

            if (!isSorted(arr)) throw new IllegalStateException("Sort failed");

            csv.append(String.format("%d,%d,%d,%d,%d,%d\n", r, n, acc.getLastDurationNanos(),
                    acc.getComparisons(), acc.getSwaps(), acc.getAccesses()));
            System.out.printf("run=%d n=%d time=%dms comps=%d swaps=%d accesses=%d\n",
                    r, n, acc.getLastDurationNanos() / 1_000_000,
                    acc.getComparisons(), acc.getSwaps(), acc.getAccesses());
        }

        if (out != null) {
            try (FileWriter fw = new FileWriter(out)) {
                fw.write(csv.toString());
            }
            System.out.println("Wrote results to " + out);
        }
    }

    private static int[] generateArray(int n, String dist, int seed) {
        Random rnd = new Random(seed + 12345);
        int[] arr = new int[n];
        switch (dist) {
            case "random":
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) arr[i] = n - i;
                break;
            case "nearly-sorted":
            case "nearly":
                for (int i = 0; i < n; i++) arr[i] = i;
                for (int k = 0; k < Math.max(1, n / 100); k++) {
                    int a = rnd.nextInt(n);
                    int b = rnd.nextInt(n);
                    int t = arr[a];
                    arr[a] = arr[b];
                    arr[b] = t;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown distribution: " + dist);
        }
        return arr;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
