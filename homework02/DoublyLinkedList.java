/**
 * Your implementation of a non-circular doubly linked list with a tail pointer.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class DoublyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     * <p>
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data  the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     *                                             index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than size: index=" + index + "; size=" + size);
        }

        if (index == 0) {
            addToFront(data);
            return;
        }
        if (index == size) {
            addToBack(data);
            return;
        }
        if (head == null) {
            this.addToEmptyList(data);
            return;
        }

        LinkedListNode<T> previousNode = this.getNodeAtIndex(index - 1);
        LinkedListNode<T> nextNode = previousNode.getNext();
        LinkedListNode<T> newNode = new LinkedListNode<>(previousNode, data, nextNode);
        previousNode.setNext(newNode);
        nextNode.setPrevious(newNode);
        size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        if (head == null) {
            this.addToEmptyList(data);
            return;
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(null, data, head);
        head.setPrevious(newNode);
        head = newNode;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        if (head == null) {
            this.addToEmptyList(data);
            return;
        }
        LinkedListNode<T> newNode = new LinkedListNode<>(tail, data, null);
        tail.setNext(newNode);
        tail = newNode;
        size++;
    }

    /**
     * Adds the element to an empty list. Called when size = 0
     *
     * @param data the data for the new element
     */
    private void addToEmptyList(T data) {
        if (head == null) {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);
            head = newNode;
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element from the index specified.
     * <p>
     * Removing from index 0 and {@code size - 1} should be O(1), all other
     * cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     *                                             index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to size: index=" + index + "; size=" + size);
        }

        if (index == 0) {
            return removeFromFront();
        }
        if (index == size - 1) {
            return removeFromBack();
        }

        LinkedListNode<T> removedNode = this.getNodeAtIndex(index);
        LinkedListNode<T> previousNode = removedNode.getPrevious();
        LinkedListNode<T> nextNode = removedNode.getNext();

        previousNode.setNext(nextNode);
        nextNode.setPrevious(previousNode);
        size--;

        return removedNode.getData();

    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     * <p>
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (head == null) {
            return null;
        }
        LinkedListNode<T> removedNode = head;
        size--;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = removedNode.getNext();
            head.setPrevious(null);
        }

        return removedNode.getData();


    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     * <p>
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (tail == null) {
            return null;
        }
        LinkedListNode<T> removedNode = tail;
        size--;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = removedNode.getPrevious();
            tail.setNext(null);
        }

        return removedNode.getData();
    }

    /**
     * Returns the index of the last occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     * <p>
     * If data is in the tail, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @return the index of the last occurrence or -1 if not in the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public int lastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        LinkedListNode<T> currentNode = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (currentNode.getData().equals(data)) {
                return i;
            }
            currentNode = currentNode.getPrevious();
        }

        return -1;
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     *                                             index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to size: index=" + index + "; size=" + size);
        }

        return getNodeAtIndex(index).getData();
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order from head to tail
     */
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];

        LinkedListNode<T> currentNode = head;


        for (int i = 0; i < size; i++) {
            arr[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }
        return arr;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     * <p>
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list of all data and resets the size.
     * <p>
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
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
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Optimally returns the node at the given index
     * Starts at tail if index is greater than size/2
     * Starts at head if index is less than size/2
     *
     * @param index index of node
     * @return node at given index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    private LinkedListNode<T> getNodeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to size: index=" + index + "; size=" + size);
        }

        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        int middle = size / 2;

        LinkedListNode<T> requestedNode = head;
        if (index >= middle) {
            requestedNode = tail;
            for (int i = 0; i < size - 1 - index; i++) {
                requestedNode = requestedNode.getPrevious();
            }
        } else if (index < middle) {
            for (int i = 0; i < index; i++) {
                requestedNode = requestedNode.getNext();
            }
        }
        return requestedNode;
    }
}