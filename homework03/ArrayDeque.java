import java.util.NoSuchElementException;

/**
 * Your implementation of an array deque.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayDeque with an initial capacity of
     * the {@code INITIAL_CAPACITY} constant above.
     */
    public ArrayDeque() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        back = 0;
        size = 0;
    }

    /**
     * Adds the data to the front of the deque.
     * <p>
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current capacity. If a regrow is necessary,
     * you should copy elements to the beginning of the new array and reset
     * front to the beginning of the array (and move {@code back}
     * appropriately). After the regrow, the new data should be at index 0 of
     * the array.
     * <p>
     * This method must run in amortized O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into deque."
            );
        }
        if (size == backingArray.length) {
            regrowArrayAndAddData(data);
            return;
        }
        front = mod(front - 1, backingArray.length);
        backingArray[front] = data;
        size++;

    }

    /**
     * Adds the data to the back of the deque.
     * <p>
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current capacity. If a regrow is necessary,
     * you should copy elements to the front of the new array and reset
     * front to the beginning of the array (and move {@code back}
     * appropriately).
     * <p>
     * This method must run in amortized O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into deque."
            );
        }
        if (size == backingArray.length) {
            regrowArrayAndAddData(null);
        }
        backingArray[back] = data;
        back = mod(back + 1, backingArray.length);
        size++;

    }

    /**
     * Regrows array to 2x its current length.
     * Adds @param data to front of array before regrowing if provided.
     *
     * @param data the data to be added to the array.
     *             Should be null if adding to last
     */
    private void regrowArrayAndAddData(T data) {
        T[] newArray = (T[]) new Object[backingArray.length * 2];
        int index = 0;
        int newSize = size;
        if (data != null) {
            newArray[index++] = data;
            newSize++;
        }
        while (index < newSize) {
            newArray[index++] = removeFirst();
        }
        front = 0;
        back = newSize;
        size = newSize;
        backingArray = newArray;
    }

    /**
     * Removes the data at the front of the deque.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, you should
     * explicitly reset front and back to the beginning of the array.
     * <p>
     * You should replace any spots that you remove from with null. Failure to
     * do so will result in a major loss of points.
     * <p>
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty deque.");
        }
        T removedData = backingArray[front];
        backingArray[front] = null;
        front = mod(front + 1, backingArray.length);
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        return removedData;
    }

    /**
     * Removes the data at the back of the deque.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, you should
     * explicitly reset front and back to the beginning of the array.
     * <p>
     * You should replace any spots that you remove from with null. Failure to
     * do so will result in a major loss of points.
     * <p>
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty deque.");
        }
        back = mod(back - 1, backingArray.length);
        T removedData = backingArray[back];
        backingArray[back] = null;
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        return removedData;
    }

    /**
     * Returns the smallest non-negative remainder when dividing {@code index}
     * by {@code modulo}. So, for example, if modulo is 5, then this method will
     * return either 0, 1, 2, 3, or 4, depending on what the remainder is.
     * <p>
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     * <p>
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     * <p>
     * DO NOT MODIFY THIS METHOD. This helper method is here to make the math
     * part of the circular behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive.");
        } else {
            int newIndex = index % modulo;
            return newIndex >= 0 ? newIndex : newIndex + modulo;
        }
    }

    /**
     * Returns the number of elements in the deque.
     * <p>
     * Runs in O(1) for all cases.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the backing array of this deque.
     * Normally, you would not do this, but we need it for grading your work.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}