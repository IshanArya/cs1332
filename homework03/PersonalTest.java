import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

/*
 * Fuzzer test for the ArrayDeque and LinkedDeque classes.
 * I take no responsibility for the correctness of these tests.
 *
 * @author Kyle Stachowicz
 * @version 1.1
 */
public class PersonalTest {

    private ArrayDeque<Integer> array;
    private LinkedDeque<Integer> linked;

    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void test() {
        populateList();
        printArray(array.getBackingArray());

    }

    @Test(timeout = TIMEOUT)
    public void testResetOfArray() {
        populateList();
        printArray(array.getBackingArray());
        for (int i = 0; i < 10; i++) {
            array.removeLast();
            System.out.print("Array at " + i + ": ");
            printArray(array.getBackingArray());
        }
        printArray(array.getBackingArray());
        array.addLast(5);
        array.addFirst(4);
        array.removeFirst();
        printArray(array.getBackingArray());
    }

    public void populateList() {
        for (int i = 0; i < 11; i++) {
            array.addLast(i);
            linked.addFirst(i);
        }
    }

    public void printArray(Object[] arr) {
        for (Object x : arr) {
            System.out.print(x + ", ");
        }
        System.out.println();
    }
}