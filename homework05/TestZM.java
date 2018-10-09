import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

public class TestZM {
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
        final int NUM_RUNS = 10000;

        for (int i = 0; i < NUM_RUNS; i++) {
            /*
            System.out.print("[");
            for (int j = 0; j < minHeap.getBackingArray().length; j++) {
                System.out.print(minHeap.getBackingArray()[j] + ", ");
            }
            System.out.println("]");
            */
            Integer value = random.nextInt(100);
            switch (random.nextInt(5)) {
                case 0:
                    minHeap.add(value);
                    priorityQueue.add(value);
                    assertEquals(priorityQueue.size(), minHeap.size());
                    assertArrayEquals(getArray(minHeap), priorityQueue.toArray());
                    break;
                case 1:
                    assertEquals(priorityQueue.isEmpty(), minHeap.isEmpty());
                    if (!priorityQueue.isEmpty()) {
                        int pQ = priorityQueue.remove();
                        int mH = minHeap.remove();
                        assertEquals(pQ, mH);
                    } else {
                        minHeap.add(value);
                        priorityQueue.add(value);
                    }
                    assertEquals(priorityQueue.size(), minHeap.size());
                    assertArrayEquals(getArray(minHeap), priorityQueue.toArray());
                    break;
                case 2:
                    minHeap.clear();
                    priorityQueue.clear();
                    assertArrayEquals(getArray(minHeap), priorityQueue.toArray());

                    boolean thrown = false;
                    try {
                        minHeap.remove();
                    } catch (NoSuchElementException e) {
                        thrown = true;
                    }

                    assertTrue(thrown);
                    break;
                case 3:
                    ArrayList<Integer> data = new ArrayList<>();

                    for (int j = 0; j < value; j++) {
                        data.add(random.nextInt(1000));
                    }

                    minHeap = new MinHeap<>(data);
                    priorityQueue = new PriorityQueue<>(data);

                    assertEquals(priorityQueue.size(), minHeap.size());
                    assertArrayEquals(getArray(minHeap), priorityQueue.toArray());
                    break;
                case 4:
                    ArrayList<Integer> list = null;

                    boolean wasThrown = false;
                    try {
                        minHeap = new MinHeap<>(list);
                    } catch (IllegalArgumentException e) {
                        wasThrown = true;
                    }

                    assertTrue(wasThrown);

                    list = new ArrayList<>();

                    list.add(1);
                    list.add(null);
                    list.add(2);

                    try {
                        minHeap = new MinHeap<>(list);
                    } catch (IllegalArgumentException e) {
                        wasThrown = true;
                    }

                    assertTrue(wasThrown);

                    minHeap = new MinHeap<>();
                    priorityQueue = new PriorityQueue<>();

                    try {
                        minHeap.add(null);
                    } catch (IllegalArgumentException e) {
                        wasThrown = true;
                    }

                    assertTrue(wasThrown);
            }
        }
    }

    private Integer[] getArray(MinHeap<Integer> minHeap) {
        Integer[] ints = new Integer[minHeap.size()];

        for (int i = 1; i < minHeap.getBackingArray().length; i++) {
            Object obj = minHeap.getBackingArray()[i];

            if (obj == null) {
                break;
            }

            ints[i - 1] = (Integer) obj;
        }

        return ints;
    }
}