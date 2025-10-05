package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortTest {
    @Test
    public void testEmpty() {
        SelectionSort s = new SelectionSort(new PerformanceTracker());
        int[] a = new int[0];
        s.sort(a);
        assertArrayEquals(new int[0], a);
    }

    @Test
    public void testSingle() {
        SelectionSort s = new SelectionSort(new PerformanceTracker());
        int[] a = new int[] {42};
        s.sort(a);
        assertArrayEquals(new int[] {42}, a);
    }

    @Test
    public void testDuplicates() {
        SelectionSort s = new SelectionSort(new PerformanceTracker());
        int[] a = new int[] {3,1,2,3,1,2};
        s.sort(a);
        assertArrayEquals(new int[] {1,1,2,2,3,3}, a);
    }

    @Test
    public void testAlreadySorted() {
        SelectionSort s = new SelectionSort(new PerformanceTracker());
        int[] a = new int[] {1,2,3,4,5};
        s.sort(a);
        assertArrayEquals(new int[] {1,2,3,4,5}, a);
        assertEquals(0, s.getTracker().getSwaps());
    }

    @Test
    public void testReverse() {
        SelectionSort s = new SelectionSort(new PerformanceTracker());
        int[] a = new int[] {5,4,3,2,1};
        s.sort(a);
        assertArrayEquals(new int[] {1,2,3,4,5}, a);
    }
}
