import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot initialize a AVL tree with a null Collection."
            );
        }
        for (T x : data) {
            if (x == null) {
                throw new IllegalArgumentException(
                        "Cannot insert null data in AVL"
                );
            }
            this.add(x);
        }

    }

    /**
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        }

        if (root == null) {
            root = new AVLNode<>(data);
            updatedHeightAndBL(root);
            size++;
            return;
        }
        root = addHelper(data, root);


    }

    /**
     * Recursive helper method for add method
     *
     * @param data    the data to be added
     * @param current the parent of the node to be added
     * @return updated, balanced AVLNode
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> current) {
        int compareDigit = current.getData().compareTo(data);
        if (compareDigit == 0) {
            return current;
        } else if (compareDigit > 0) {
            if (current.getLeft() == null) {
                AVLNode<T> newNode = new AVLNode<>(data);
                updatedHeightAndBL(newNode);
                current.setLeft(newNode);
                size++;
            } else {
                AVLNode<T> newChild = addHelper(data, current.getLeft());
                current.setLeft(newChild);
            }
        } else {
            if (current.getRight() == null) {
                AVLNode<T> newNode = new AVLNode<>(data);
                updatedHeightAndBL(newNode);
                current.setRight(newNode);
                size++;
            } else {
                AVLNode<T> newChild = addHelper(data, current.getRight());
                current.setRight(newChild);
            }
        }

        updatedHeightAndBL(current);
        return balanceSubTree(current);

    }

    /**
     * Rotates subtree of {@param a} left
     * a's right child = b's left child
     * b's left child = a
     *
     * @param a the current root node of the subtree
     * @return the new root of the subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);

        updatedHeightAndBL(a);
        updatedHeightAndBL(b);
        return b;

    }

    /**
     * Rotates subtree of {@param a} left
     * a's left child = b's right child
     * b's right child = a
     *
     * @param a the current root node of the subtree
     * @return the new root of the subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);

        updatedHeightAndBL(a);
        updatedHeightAndBL(b);
        return b;
    }

    /**
     * Update height and balance factor of {@param node}
     *
     * @param node the node to update
     */
    private void updatedHeightAndBL(AVLNode<T> node) {
        int leftHeight = node.getLeft() == null
                ? -1 : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null
                ? -1 : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);

    }

    /**
     * Balance subtree that has a balance factor || > 1
     *
     * @param node = the node to balance
     * @return the balanced node
     */
    private AVLNode<T> balanceSubTree(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        if (Math.abs(node.getBalanceFactor()) > 1) {
            if (node.getBalanceFactor() > 0) {
                if (node.getLeft().getBalanceFactor() < 0) {
                    node.setLeft(rotateLeft(node.getLeft()));
                }
                return rotateRight(node);
            } else {
                if (node.getRight().getBalanceFactor() > 0) {
                    node.setRight(rotateRight(node.getRight()));
                }
                return rotateLeft(node);
            }
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
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
        T data2Return;
        if (root.getData().equals(data)) {
            data2Return = root.getData();
            if (root.getRight() == null || root.getLeft() == null) {
                root = root.getLeft() == null
                        ? root.getRight() : root.getLeft();
            } else {
                root = findPredecessor(root);
            }
            size--;
            if (root != null) {
                updatedHeightAndBL(root);
            }
        } else {
            data2Return = searchAndDestroy(data, root);
        }

        root = balanceSubTree(root);
        return data2Return;


    }

    /**
     * Find node to be removed in AVL and remove it
     * Rotate to maintain balance factor of -1 < bf < 1
     *
     * @param data    the data in the node to be removed
     * @param current the current node whose data
     *                is being compared to passed data
     * @return the data in the removed node
     */
    private T searchAndDestroy(T data, AVLNode<T> current) {
        int compareDigit = current.getData().compareTo(data);

        T data2Return = null;

        if (compareDigit > 0) {
            AVLNode<T> left = current.getLeft();
            if (left == null) {
                throw new NoSuchElementException(
                        "Node with input data not found."
                );
            } else if (left.getData().equals(data)) {
                data2Return = left.getData();
                if (left.getRight() == null || left.getLeft() == null) {
                    current.setLeft(left.getLeft() == null
                            ? left.getRight() : left.getLeft());
                } else {
                    current.setLeft(findPredecessor(left));
                }
                size--;
            } else {
                data2Return = searchAndDestroy(data, left);
            }
            current.setLeft(balanceSubTree(current.getLeft()));

        } else if (compareDigit < 0) {
            AVLNode<T> right = current.getRight();
            if (right == null) {
                throw new NoSuchElementException(
                        "Node with input data not found."
                );
            } else if (right.getData().equals(data)) {
                data2Return = right.getData();
                if (right.getRight() == null || right.getLeft() == null) {
                    current.setRight(right.getLeft() == null
                            ? right.getRight() : right.getLeft());
                } else {
                    current.setRight(findPredecessor(right));
                }
                size--;
            } else {
                data2Return = searchAndDestroy(data, right);
            }
            current.setRight(balanceSubTree(current.getRight()));
        }
        updatedHeightAndBL(current);
        return data2Return;
    }

    /**
     * Find the predecessor of {@param node},
     * updating predecessor in the process
     *
     * @param node the node to find the predecessor of
     * @return the predecessor of {@param node}
     */
    private AVLNode<T> findPredecessor(AVLNode<T> node) {
        AVLNode<T> predecessor;
        if (node.getLeft().getRight() == null) {
            predecessor = node.getLeft();

        } else {
            predecessor = findPredecessorHelper(node.getLeft());
            predecessor.setLeft(balanceSubTree(node.getLeft()));
        }
        predecessor.setRight(node.getRight());
        updatedHeightAndBL(predecessor);
        return balanceSubTree(predecessor);
    }

    /**
     * Return the final right child of {@param node}
     * set the (parent of the final right child)'s
     * right child to final right's left child
     *
     * @param node the node to find the final right child of
     * @return the final right child
     */
    private AVLNode<T> findPredecessorHelper(AVLNode<T> node) {
        AVLNode<T> returningNode;
        if (node.getRight().getRight() == null) {
            returningNode = node.getRight();
            node.setRight(returningNode.getLeft());
        } else {
            returningNode = findPredecessorHelper(node.getRight());
        }

        updatedHeightAndBL(node);
        node.setRight(balanceSubTree(node.getRight()));
        updatedHeightAndBL(node);
        return balanceSubTree(returningNode);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
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

        return getHelper(data, root);
    }

    /**
     * recursive helper method for get
     *
     * @param current the node whose data to compare to {@param data}
     * @param data    the data we are looking for in the tree
     * @return the data in the node with data equivalent to {@param data}
     */
    private T getHelper(T data, AVLNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("Node with input data not found.");
        }

        int compareDigit = current.getData().compareTo(data);
        if (compareDigit == 0) {
            return current.getData();
        } else if (compareDigit < 0) {
            return getHelper(data, current.getRight());
        } else {
            return getHelper(data, current.getLeft());
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data.");
        }

        return containsHelper(data, root);
    }

    /**
     * recursive helper method for contains
     *
     * @param current the node whose data to compare to {@param data}
     * @param data    the data we are looking for in the tree
     * @return whether or not the tree contains
     * a node with data equivalent to {@param data}
     */
    private boolean containsHelper(T data, AVLNode<T> current) {
        if (current == null) {
            return false;
        }
        int compareDigit = current.getData().compareTo(data);
        if (compareDigit == 0) {
            return true;
        } else if (compareDigit < 0) {
            return containsHelper(data, current.getRight());
        } else {
            return containsHelper(data, current.getLeft());
        }
    }


    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     * <p>
     * Must run in O(log n) for all cases
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        return maxDeepestNodeHelper(root);
    }

    /**
     * Recursively find deepest node using balance factor
     * to find deeper branch
     *
     * @param current = node to traverse down
     * @return data from deepest, right-most node
     */
    private T maxDeepestNodeHelper(AVLNode<T> current) {
        if (current.getLeft() == null && current.getRight() == null) {
            return current.getData();
        }

        return maxDeepestNodeHelper(
                current.getBalanceFactor() > 0
                        ? current.getLeft() : current.getRight()
        );
    }

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     * <p>
     * Must run in O(log n) for all cases
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * deepestCommonAncestor(3, 1): 2
     * <p>
     * Example
     * Tree:
     * 3
     * /    \
     * 1      4
     * / \
     * 0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @return the data of the deepest common ancestor
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *                                            are null
     * @throws java.util.NoSuchElementException   if one or more of the data are
     *                                            not in the tree
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot search with null data.");
        }
        if (data1.equals(data2)) {
            return this.get(data1);
        }
        return deepestCommonAncestorHelper(data1, data2, root);


    }

    /**
     * Recursively find deepest common ancestor
     * if one datum is greater than current node and the other is less,
     * and the subtree contains both pieces of data, return the current node
     *
     * @param data1   = data1 inputted by user
     * @param data2   = data2 inputted by user
     * @param current = current node comparing against
     * @return deepest common ancestor
     */
    private T deepestCommonAncestorHelper(T data1,
                                          T data2,
                                          AVLNode<T> current) {
        int compareDigit1 = current.getData().compareTo(data1);
        int compareDigit2 = current.getData().compareTo(data2);

        if (compareDigit1 == 0 && this.containsHelper(data2, current)) {
            return current.getData();
        }
        if (compareDigit2 == 0 && this.containsHelper(data1, current)) {
            return current.getData();
        }
        if (compareDigit1 > 0 && compareDigit2 > 0) {
            return deepestCommonAncestorHelper(data1, data2, current.getLeft());
        }
        if (compareDigit1 < 0 && compareDigit2 < 0) {
            return deepestCommonAncestorHelper(
                    data1, data2, current.getRight()
            );
        }
        if ((compareDigit1 < 0) != (compareDigit2 < 0)) {
            if (this.containsHelper(data1, current)
                    && this.containsHelper(data2, current)) {
                return current.getData();
            }
        }
        throw new NoSuchElementException("Neither datum is in this tree.");
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
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
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
