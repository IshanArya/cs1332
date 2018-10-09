import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.PriorityQueue;
import java.util.Random;

public class TestOR {
    private static final int TIMEOUT = 2000;
    private MinHeap<Integer> minHeap;
    private PriorityQueue<Integer> priorityQueue;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
        priorityQueue = new PriorityQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void fuzz() {
        Random random = new Random();
        final int NUM_RUNS = 1000;

        for (int i = 0; i < NUM_RUNS; i++) {
            /*
            System.out.print("[");
            for (int j = 0; j < minHeap.getBackingArray().length; j++) {
                System.out.print(minHeap.getBackingArray()[j] + ", ");
            }
            System.out.println("]");
            */
            Integer value = random.nextInt(100);
            switch (random.nextInt(2)) {
                case 0:
                    minHeap.add(value);
                    priorityQueue.add(value);
                    break;
                case 1:
                    assertEquals(priorityQueue.isEmpty(), minHeap.isEmpty());
                    if (!priorityQueue.isEmpty()) {
                        int pQ = priorityQueue.remove();
                        int mH = minHeap.remove();
                        assertEquals(pQ, mH);
                    }
                    break;
                case 2:
                    assertEquals(priorityQueue.size(), minHeap.size());
                    break;
            }
        }
    }
}