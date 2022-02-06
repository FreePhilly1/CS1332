import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Your implementation of an AVL.
 *
 * @author Phillip Kim
 * @version 1.0
 * @userid pkim90
 * @GTID 903376077
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be added to the AVL");
        }
        for (T data1 : data) {
            if (data1 == null) {
                throw new IllegalArgumentException("Null cannot be added to the AVL");
            }
            add(data1);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be added to the AVL");
        }
        root = rAdd(root, data);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node. Do NOT use the provided public 
     * predecessor method to remove a 2-child node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be in the AVL");
        }
        AVLNode<T> node = new AVLNode<>(null);
        root = rRemove(root, data, node);
        return node.getData();
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be in the tree");
        }
        return rGet(root, data);
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return rContains(root, data);
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return Math.max(root.getLeft().getHeight(), root.getRight().getHeight()) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 3 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * 3: If the data passed in is the minimum data in the tree, return null.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be added to the AVL");
        }
        List<T> list = new ArrayList<>();
        rPredecessor(root, data, list);
        if (list.size() == 0) {
            throw new NoSuchElementException("Data is not in the AVL");
        } else if (list.size() == 1) {
            return null;
        } else {
            return list.get(list.size() - 2);
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("The value for k is not in the range of the size of the AVL");
        }
        List<T> list = new ArrayList<>();
        rInorder(root, list, k);
        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the new height of the node.
     *
     * @param node the node to be examined
     * @return the height of the node passed in
     */
    private int calcHeight(AVLNode<T> node) {
        int leftHeight;
        int rightHeight;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Returns the new balance factor of the node.
     *
     * @param node the node to be examined
     * @return the balance factor of the node passed in
     */
    private int calcBalanceFactor(AVLNode<T> node) {
        int leftHeight;
        int rightHeight;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }
        return leftHeight - rightHeight;
    }

    /**
     * Method that performs a left rotation on the passed in node.
     *
     * @param curr the node to be operated on
     * @return the rotated node
     */
    private AVLNode<T> rLeftRotate(AVLNode<T> curr) {
        AVLNode<T> newNode = curr.getRight();
        curr.setRight(newNode.getLeft());
        newNode.setLeft(curr);
        curr.setHeight(calcHeight(curr));
        curr.setBalanceFactor(calcBalanceFactor(curr));
        newNode.setHeight(calcHeight(newNode));
        newNode.setBalanceFactor(calcBalanceFactor(newNode));
        return newNode;
    }

    /**
     * Method that performs a right rotation on the passed in node.
     *
     * @param curr the node to be operated on
     * @return the rotated node
     */
    private AVLNode<T> rRightRotate(AVLNode<T> curr) {
        AVLNode<T> newNode = curr.getLeft();
        curr.setLeft(newNode.getRight());
        newNode.setRight(curr);
        curr.setHeight(calcHeight(curr));
        curr.setBalanceFactor(calcBalanceFactor(curr));
        newNode.setHeight(calcHeight(newNode));
        newNode.setBalanceFactor(calcBalanceFactor(newNode));
        return newNode;
    }

    /**
     * Recursive method that adds data to the tree
     *
     * @param curr the current AVLNode
     * @param data the data to be added to the tree
     * @return the node that should be in that direction of the tree
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        } else {
            return curr;
        }

        curr.setHeight(calcHeight(curr));
        curr.setBalanceFactor(calcBalanceFactor(curr));
        int balanceFactor = calcBalanceFactor(curr);

        if (balanceFactor < -1) {
            if (data.compareTo(curr.getRight().getData()) < 0) {
                curr.setRight(rRightRotate(curr.getRight()));
            }
            return rLeftRotate(curr);
        } else if (balanceFactor > 1) {
            if (data.compareTo(curr.getLeft().getData()) > 0) {
                curr.setLeft(rLeftRotate(curr.getLeft()));
            }
            return rRightRotate(curr);
        }
        return curr;
    }

    /**
     * Method that recursively removes the node with the target data
     *
     * @param curr the current node to inspect
     * @param data the data to be removed
     * @param node the node to keep track of the removed node if any
     * @return the node that should be in that direction of the tree
     */
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> node) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the AVL");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, node));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, node));
        } else {
            node.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else {
                AVLNode<T> node1 = new AVLNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), node1));
                curr.setData(node1.getData());
            }
        }

        curr.setHeight(calcHeight(curr));
        curr.setBalanceFactor(calcBalanceFactor(curr));
        int balanceFactor = calcBalanceFactor(curr);
        if (balanceFactor < -1) {
            if (data.compareTo(curr.getRight().getData()) < 0) {
                curr.setRight(rRightRotate(curr.getRight()));
            }
            return rLeftRotate(curr);
        } else if (balanceFactor > 1) {
            if (data.compareTo(curr.getLeft().getData()) > 0) {
                curr.setLeft(rLeftRotate(curr.getLeft()));
            }
            return rRightRotate(curr);
        }
        return curr;
    }

    /**
     * Helper function that removes the successor node
     *
     * @param curr the current BSTNode
     * @param node a temporary node to keep track of the predecessor node
     * @return the node that should in that direction of the tree
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> node) {
        if (curr.getLeft() == null) {
            node.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), node));
            return curr;
        }
    }

    /**
     * Method that recursively retrieves a data
     *
     * @param curr the current node to inspect
     * @param data the data to be retrieved
     * @return the data to be found
     */
    private T rGet(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            if (data.compareTo(curr.getData()) == 0) {
                return curr.getData();
            } else if (data.compareTo(curr.getData()) < 0) {
                return rGet(curr.getLeft(), data);
            } else {
                return rGet(curr.getRight(), data);
            }
        }
    }

    /**
     * Method that recursively searches for a data in the tree
     *
     * @param curr current node to search
     * @param data the data to be found
     * @return whether the data is in the tree
     */
    private boolean rContains(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else {
            if (data.compareTo(curr.getData()) == 0) {
                return true;
            } else if (data.compareTo(curr.getData()) < 0) {
                return rContains(curr.getLeft(), data);
            } else {
                return rContains(curr.getRight(), data);
            }
        }
    }

    /**
     * Method that finds the predecessor of the specified data
     *
     * @param curr the current node to be traversed
     * @param data the data we are finding the predecessor for
     * @param list list of the current traversal
     */
    private void rPredecessor(AVLNode<T> curr, T data, List<T> list) {
        if (curr != null && !list.contains(data)) {
            rPredecessor(curr.getLeft(), data, list);
            if (!list.contains(data)) {
                list.add(curr.getData());
            }
            rPredecessor(curr.getRight(), data, list);
        }
    }

    /**
     * Method that recursively traverse the AVL inorder
     *
     * @param curr the current AVLNode to be inspected
     * @param list list of the traversal
     * @param k the number of elements to be returned
     */
    private void rInorder(AVLNode<T> curr, List<T> list, int k) {
        if (curr != null && list.size() < k) {
            rInorder(curr.getLeft(), list, k);
            if (list.size() != k) {
                list.add(curr.getData());
            }
            rInorder(curr.getRight(), list, k);
        }
    }
}
