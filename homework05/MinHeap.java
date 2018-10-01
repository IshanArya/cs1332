import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     * <p>
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * <p>
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     * <p>
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed list cannot be null.");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[size * 2 + 1];

        for (int i = 1; i < size + 1; i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException(
                        "Cannot insert null data into backing array."
                );
            }
            backingArray[i] = data.get(i - 1);
        }

        for (int i = size / 2; i > 0; i--) {
            heapifyDown(i);
        }

    }

    /**
     * Compare element at {@param index} to children, if they exist
     * and swap the element with the min child, if children are lower value
     *
     * @param index the root index of subtree to heapify down
     */
    private void heapifyDown(int index) {
        int leftChildIndex = 2 * index;
        int rightChildIndex = leftChildIndex + 1;
        int smallestIndex = index;
        if (leftChildIndex <= size
                && backingArray[smallestIndex].compareTo(
                        backingArray[leftChildIndex]) > 0) {
            smallestIndex = leftChildIndex;
        }
        if (rightChildIndex <= size
                && backingArray[smallestIndex].compareTo(
                        backingArray[rightChildIndex]) > 0) {
            smallestIndex = rightChildIndex;
        }

        if (smallestIndex != index) {
            T swapItem = backingArray[index];
            backingArray[index] = backingArray[smallestIndex];
            backingArray[smallestIndex] = swapItem;
            heapifyDown(smallestIndex);
        }
    }

    /**
     * Compare element at {@param index} to parent and swap if parent > child
     *
     * @param index the index of child to compare to parent
     */
    private void heapifyUp(int index) {
        int parentIndex = index % 2 == 0 ? (index / 2) : ((index - 1) / 2);
        if (parentIndex > 0) {
            if (backingArray[parentIndex].compareTo(backingArray[index]) > 0) {
                T swapItem = backingArray[parentIndex];
                backingArray[parentIndex] = backingArray[index];
                backingArray[index] = swapItem;
                heapifyUp(parentIndex);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @param item the item to be added to the heap
     * @throws IllegalArgumentException if the item is null
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to heap.");
        }
        if (backingArray.length - 1 == size) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        size++;
        backingArray[size] = item;
        heapifyUp(size);
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @return the removed item
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty heap.");
        }
        T removedItem = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapifyDown(1);

        return removedItem;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            return null;
        }
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     * <p>
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}