import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class LinkedDeque<T> {
    // Do not add new instance variables and do not add a new constructor.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     * <p>
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into deque"
            );
        }
        LinkedNode<T> newNode = new LinkedNode<>(null, data, head);
        if (head != null) {
            head.setPrevious(newNode);
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    /**
     * Adds the data to the back of the deque.
     * <p>
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into deque"
            );
        }
        LinkedNode<T> newNode = new LinkedNode<>(tail, data, null);
        if (tail != null) {
            tail.setNext(newNode);
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Removes the data at the front of the deque.
     * <p>
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Cannot remove from empty deque");
        }
        LinkedNode<T> removedNode = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return removedNode.getData();
    }

    /**
     * Removes the data at the back of the deque.
     * <p>
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("Cannot remove from empty deque");
        }
        LinkedNode<T> removedNode = tail;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return removedNode.getData();
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
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}