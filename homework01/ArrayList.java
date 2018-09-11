/**
 * Your implementation of an ArrayList.
 *
 * @author Ishan Arya
 * @userid iarya3
 * @GTID 903399427
 * @version 1.0
 */
public class ArrayList<T> {

	// Do not add new instance variables.
	private T[] backingArray;
	private int size;

	/**
	 * The initial capacity of the array list.
	 */
	public static final int INITIAL_CAPACITY = 13;

	/**
	 * Constructs a new ArrayList.
	 *
	 * You may add statements to this method.
	 */
	public ArrayList() {
		this.clear();
	}

	/**
	 * Adds the element to the index specified.
	 *
	 * Remember that this add may require elements to be shifted.
	 *
	 * Adding to index {@code size} should be amortized O(1),
	 * all other adds are O(n).
	 *
	 * @param index The index where you want the new element.
	 * @param data The data to add to the list.
	 * @throws java.lang.IndexOutOfBoundsException if index is negative
	 * or index > size
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public void addAtIndex(int index, T data) {
		if(index < 0) {
			throw new IndexOutOfBoundsException("Cannot insert data at negative index.");
		}
		if(index > size) {
			throw new IndexOutOfBoundsException("Cannot insert data at index greater than ArrayList size. Index: " + index + ", Size: " + size);
		}
		if(data == null) {
			throw new IllegalArgumentException("Cannot insert null data into ArrayList.");
		}
		if(size == backingArray.length) {
			T[] newArray = (T[]) new Object[(int)(backingArray.length * 2)];
			for(int i = 0; i < backingArray.length; i++) {
				newArray[i] = backingArray[i];
			}

			backingArray = newArray;
		}
		for(int i = size; i > index; i--) {
			backingArray[i] = backingArray[i - 1];
		}
		backingArray[index] = data;
		size++;


	}

	/**
	 * Add the given data to the front of your array list.
	 *
	 * Remember that this add may require elements to be shifted.
	 *
	 * Must be O(n).
	 *
	 * @param data The data to add to the list.
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public void addToFront(T data) {
		if(data == null) {
			throw new IllegalArgumentException("Cannot insert null data into ArrayList.");
		}
		addAtIndex(0, data);
	}

	/**
	 * Add the given data to the back of your array list.
	 *
	 * Must be amortized O(1).
	 *
	 * @param data The data to add to the list.
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public void addToBack(T data) {
		if(data == null) {
			throw new IllegalArgumentException("Cannot insert null data into ArrayList.");
		}
		addAtIndex(size, data);
	}

	/**
	 * Removes and returns the element at index.
	 *
	 * Remember that this remove may require elements to be shifted.
	 *
	 * This method should be O(1) for index {@code size - 1} and O(n) in
	 * all other cases.
	 *
	 * @param index The index of the element
	 * @return The object that was formerly at that index.
	 * @throws java.lang.IndexOutOfBoundsException if index < 0 or
	 * index >= size
	 */
	public T removeAtIndex(int index) {
		if(index < 0) {
			throw new IndexOutOfBoundsException("Cannot remove data from negative index.");
		}
		if(index >= size) {
			throw new IndexOutOfBoundsException("Cannot remove data from index greater than ArrayList size. Index: " + index + ", Size: " + size);
		}

		T removedObject = backingArray[index];

		for(int i = index; i < size - 1; i++) {
			backingArray[i] = backingArray[i + 1];
		}
		backingArray[size - 1] = null;
		size--;


		return removedObject;
	}

	/**
	 * Remove the first element in the list and return it.
	 *
	 * Remember that this remove may require elements to be shifted.
	 *
	 * Must be O(n).
	 *
	 * @return The data from the front of the list or null if the list is empty
	 */
	public T removeFromFront() {
		if(size == 0) {
			return null;
		}
		return removeAtIndex(0);
	}

	/**
	 * Remove the last element in the list and return it.
	 *
	 * Must be O(1).
	 *
	 * @return The data from the back of the list or null if the list is empty
	 */
	public T removeFromBack() {
		if(size == 0) {
			return null;
		}
		return removeAtIndex(size - 1);
	}

	/**
	 * Returns the element at the given index.
	 *
	 * Must be O(1).
	 *
	 * @param index The index of the element
	 * @return The data stored at that index.
	 * @throws java.lang.IndexOutOfBoundsException if index < 0 or
	 * index >= size
	 */
	public T get(int index) {
		if(index < 0) {
			throw new IndexOutOfBoundsException("Cannot get data from negative index.");
		}
		if(index >= size) {
			throw new IndexOutOfBoundsException("Cannot get data from index greater than ArrayList size. Index: " + index + ", Size: " + size);
		}
		return backingArray[index];
	}

	/**
	 * Return a boolean value representing whether or not the list is empty.
	 *
	 * Must be O(1).
	 *
	 * @return true if empty; false otherwise
	 */
	public boolean isEmpty() {
		if(backingArray[0] == null) {
			return true;
		}
		return false;
	}

	/**
	 * Clear the list. Reset the backing array to a new array of the initial
	 * capacity.
	 *
	 * Must be O(1).
	 */
	public void clear() {
		this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Return the size of the list as an integer.
	 *
	 * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
	 *
	 * @return the size of the list
	 */
	public int size() {
		// DO NOT MODIFY THIS METHOD!
		return size;
	}

	/**
	 * Return the backing array for this list.
	 *
	 * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
	 *
	 * @return the backing array for this list
	 */
	public Object[] getBackingArray() {
		// DO NOT MODIFY THIS METHOD!
		return backingArray;
	}
}
