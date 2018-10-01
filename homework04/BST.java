import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a binary search tree.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to be added
     * @throws IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return;
        }
        addHelper(data, root);
    }

    /**
     * Recursive helper method for add method
     *
     * @param data    the data to be added
     * @param current the parent of the node to be added
     */
    private void addHelper(T data, BSTNode<T> current) {
        int compareDigit = current.getData().compareTo(data);
        if (compareDigit == 0) {
            return;
        } else if (compareDigit > 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTNode<>(data));
                size++;
                return;
            }
            addHelper(data, current.getLeft());
        } else {
            if (current.getRight() == null) {
                current.setRight(new BSTNode<>(data));
                size++;
                return;
            }
            addHelper(data, current.getRight());
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        if (root == null) {
            throw new NoSuchElementException("Tree has no data.");
        }
        if (root.getData().equals(data)) {
            T rootData = root.getData();
            if (root.getLeft() != null && root.getRight() != null) {
                BSTNode<T> newRoot = findSuccessor(root.getRight());
                newRoot.setLeft(root.getLeft());
                root = newRoot;
            } else {
                root = root.getLeft() == null
                        ? root.getRight() : root.getLeft();
            }
            size--;
            return rootData;
        } else {
            return searchAndDestroy(root, data);
        }
    }

    /**
     * Find node to be removed in BST and remove it
     *
     * @param data    the data in the node to be removed
     * @param current the current node whose data
     *                is being compared to passed data
     * @return the data in the removed node
     */
    private T searchAndDestroy(BSTNode<T> current, T data) {
        int compareDigit = current.getData().compareTo(data);
        if (compareDigit > 0) {
            BSTNode<T> left = current.getLeft();
            if (left == null) {
                throw new NoSuchElementException(
                        "Node with data not found in tree."
                );
            }
            if (left.getData().equals(data)) {
                if (left.getLeft() != null && left.getRight() != null) {
                    BSTNode<T> successor = findSuccessor(left.getRight());
                    successor.setLeft(left.getLeft());
                    current.setLeft(successor);
                } else {
                    current.setLeft(left.getLeft() == null
                            ? left.getRight() : left.getLeft());
                }
                size--;
                return left.getData();
            }
            return searchAndDestroy(left, data);
        } else if (compareDigit < 0) {
            BSTNode<T> right = current.getRight();
            if (right == null) {
                throw new NoSuchElementException(
                        "Node with data not found in tree."
                );
            }
            if (right.getData().equals(data)) {
                if (right.getLeft() != null && right.getRight() != null) {
                    BSTNode<T> successor = findSuccessor(right.getRight());
                    successor.setLeft(right.getLeft());
                    current.setRight(successor);
                } else {
                    current.setRight(right.getLeft() == null
                            ? right.getRight() : right.getLeft());
                }
                size--;
                return right.getData();
            }
            return searchAndDestroy(right, data);
        }
        throw new NoSuchElementException("Node with data not found in tree.");
    }

    /**
     * Finds the successor to the parent node of the passed parameter
     * Sets the right child
     *
     * @param current the node to start checking for successor
     * @return the successor to the node to be removed,
     * with the right child of the toRemove node as
     * the right child of the returned node
     */
    private BSTNode<T> findSuccessor(BSTNode<T> current) {
        BSTNode<T> left = current.getLeft();
        if (left == null) { //edge case for if the current node is the successor
            return current;
        }
        if (left.getLeft() == null) {
            current.setLeft(left.getRight());
            left.setRight(current);
            return left;
        }
        left = findSuccessor(left);
        left.setRight(current);
        return left;


    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data.");
        }

        return getHelper(root, data);

    }

    /**
     * recursive helper method for contains
     *
     * @param node the node whose data to compare to {@param data}
     * @param data the data we are looking for in the tree
     * @return the data in the node with data equivalent to {@param data}
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException(
                    "Element with data is not in tree."
            );
        }
        int compareDigit = node.getData().compareTo(data);
        if (compareDigit == 0) {
            return node.getData();
        } else if (compareDigit < 0) {
            return getHelper(node.getRight(), data);
        } else {
            return getHelper(node.getLeft(), data);
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data.");
        }

        return containsHelper(root, data);
    }

    /**
     * recursive helper method for contains
     *
     * @param node the node whose data to compare to {@param data}
     * @param data the data we are looking for in the tree
     * @return whether or not the tree contains
     * a node with data equivalent to {@param data}
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        int compareDigit = node.getData().compareTo(data);
        if (compareDigit == 0) {
            return true;
        } else if (compareDigit < 0) {
            return containsHelper(node.getRight(), data);
        } else {
            return containsHelper(node.getLeft(), data);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        return preorderHelper(root);
    }

    /**
     * recursive helper method for preorder
     *
     * @param node the node to traverse and add to the list
     * @return a list containing all the nodes in the tree in preorder
     */
    private List<T> preorderHelper(BSTNode<T> node) {
        List<T> list = new LinkedList<>();
        if (node == null) {
            return list;
        }

        list.add(node.getData());
        list.addAll(preorderHelper(node.getLeft()));
        list.addAll(preorderHelper(node.getRight()));
        return list;
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        return inorderHelper(root);
    }

    /**
     * recursive helper method for inorder
     *
     * @param node the node to traverse and add to the list
     * @return a list containing all the nodes in the tree in inorder
     */
    private List<T> inorderHelper(BSTNode<T> node) {
        List<T> list = new LinkedList<>();
        if (node == null) {
            return list;
        }

        list.addAll(inorderHelper(node.getLeft()));
        list.add(node.getData());
        list.addAll(inorderHelper(node.getRight()));
        return list;
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        return postorderHelper(root);
    }

    /**
     * recursive helper method for posterorder()
     *
     * @param node the node to traverse and add to the list
     * @return a list containing all the nodes in the tree in postorder
     */
    private List<T> postorderHelper(BSTNode<T> node) {
        List<T> list = new LinkedList<>();
        if (node == null) {
            return list;
        }

        list.addAll(postorderHelper(node.getLeft()));
        list.addAll(postorderHelper(node.getRight()));
        list.add(node.getData());
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     * <p>
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new LinkedList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            BSTNode<T> current = queue.poll();
            if (current != null) {
                list.add(current.getData());
                queue.add(current.getLeft());
                queue.add(current.getRight());
            }
        }

        return list;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
     * <p>
     * EXAMPLE: Given the BST below composed of Integers:
     * <p>
     * 50
     * /    \
     * 25      75
     * /  \
     * 12   37
     * /  \    \
     * 10  15    40
     * /
     * 13
     * <p>
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     * <p>
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException(
                    "Cannot return more elements than there are in the tree."
                            + "size=" + size + "; k=" + k
            );
        }
        if (k < 0) {
            throw new IllegalArgumentException(
                    "Cannot return a negative number of elements."
            );
        }

        return kLargestHelper(root, k);
    }

    /**
     * recursive helper function for kLargest(int k)
     *
     * @param node the node in the tree to
     *             recurse over to find the largest child
     * @param k    the number of largest elements to return in list
     * @return a list of size {@param k} that
     * contains the largest elements in the tree in order
     */
    private List<T> kLargestHelper(BSTNode<T> node, int k) {
        List<T> list;

        if (node == null) {
            list = new LinkedList<>();
            return list;
        }

        list = kLargestHelper(node.getRight(), k);
        if (list.size() < k) {
            list.add(0, node.getData());
        } else {
            return list;
        }
        if (list.size() < k) {
            list.addAll(0, kLargestHelper(node.getLeft(), k - list.size()));
        }

        return list;
    }

    /**
     * Clears the tree.
     * <p>
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     * <p>
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Recursive helper function for height()
     *
     * @param node the node whose height needs to be found
     * @return -1 if the node is null or
     * the height of the node passed as a parameter
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        return (Math.max(heightHelper(node.getLeft()),
                heightHelper(node.getRight())) + 1);
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * <p>
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
