import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The elements should be added to the BST in the order in 
     * which they appear in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for-loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        for (T singleData : data) {
            if (singleData == null) {
                throw new IllegalArgumentException("Null cannot be added to the BST");
            }
            add(singleData);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to BST");
        }
        root = rAdd(root, data);
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null cannot be in the BST");
        }
        BSTNode<T> node = new BSTNode<>(null);
        root = rRemove(root, data, node);
        return node.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be in the BST");
        }
        return rContains(root, data);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        rPreorder(root, list);
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        rInorder(root, list);
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        rPostorder(root, list);
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use. You may import java.util.Queue as well as an implmenting
     * class.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();
            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Hint: How can we use the order property of the BST to locate the deepest
     * common ancestor?
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Null cannot be in the BST");
        }
        BSTNode<T> curr = rFindAncestor(root, data1, data2);
        Deque<T> list = new LinkedList<>();
        if (data1.compareTo(curr.getData()) == 0 && data2.compareTo(curr.getData()) == 0) {
            list.add(curr.getData());
        } else if (data1.compareTo(curr.getData()) == 0 && data2.compareTo(curr.getData()) > 0) {
            list.add(curr.getData());
            rFindPath2(curr.getRight(), list, data2);
        } else if (data1.compareTo(curr.getData()) == 0 && data2.compareTo(curr.getData()) < 0) {
            list.add(curr.getData());
            rFindPath2(curr.getLeft(), list, data2);
        } else if (data1.compareTo(curr.getData()) > 0 && data2.compareTo(curr.getData()) == 0) {
            list.add(curr.getData());
            rFindPath1(curr.getRight(), list, data1);
        } else if (data1.compareTo(curr.getData()) < 0 && data2.compareTo(curr.getData()) == 0) {
            list.add(curr.getData());
            rFindPath1(curr.getLeft(), list, data1);
        } else if (data1.compareTo(curr.getData()) > 0) {
            list.add(curr.getData());
            rFindPath1(curr.getRight(), list, data1);
            rFindPath2(curr.getLeft(), list, data2);
        } else {
            list.add(curr.getData());
            rFindPath1(curr.getLeft(), list, data1);
            rFindPath2(curr.getRight(), list, data2);
        }
        return (List<T>) list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
     * Recursive method that adds data to the tree
     *
     * @param curr the current BSTNode
     * @param data the data to be added to the tree
     * @return the node that should be in that direction of the tree
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     *
     * @param curr the current BSTNode
     * @param data the data to be removed from the tree
     * @param node a BSTNode to keep track of the removed node if any
     * @return the node that should be in that direction of the tree
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> node) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the BST");
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
                BSTNode<T> node1 = new BSTNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), node1));
                curr.setData(node1.getData());
            }
        }
        return curr;
    }

    /**
     * Helper function that removes the predecessor node
     *
     * @param curr the current BSTNode
     * @param node a temporary node to keep track of the predecessor node
     * @return the node that should in that direction of the tree
     */
    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> node) {
        if (curr.getRight() == null) {
            node.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), node));
            return curr;
        }
    }
    /**
     * Recursive method that searches for a node with specific data
     *
     * @param curr the current BSTNode
     * @param data the data that is being searched
     * @return the desired data from the node in the tree
     */
    private T rGet(BSTNode<T> curr, T data) {
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
     * Recursive method that determines whether a specific data is in the BST
     *
     * @param curr the current BSTNode
     * @param data the data that is being searched
     * @return true if the data is in the tree and false if not
     */
    private boolean rContains(BSTNode<T> curr, T data) {
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
     * Recursive method that generates a preorder traversal of the BST
     *
     * @param curr the current BSTNode
     * @param list the list containing the current traversal
     */
    private void rPreorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            rPreorder(curr.getLeft(), list);
            rPreorder(curr.getRight(), list);
        }
    }

    /**
     * Recursive method that generates an inorder traversal of the BST
     *
     * @param curr the current BSTNode
     * @param list the list containing the current traversal
     */
    private void rInorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rInorder(curr.getLeft(), list);
            list.add(curr.getData());
            rInorder(curr.getRight(), list);
        }
    }

    /**
     * Recursive method that generates a postorder traversal of the BST
     *
     * @param curr the current BSTNode
     * @param list the list containing the current traversal
     */
    private void rPostorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rPostorder(curr.getLeft(), list);
            rPostorder(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Recursive method that calculates the height of the root
     *
     * @param curr the current BSTNode
     * @return the height of the current subtree
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int leftHeight = rHeight(curr.getLeft());
            int rightHeight = rHeight(curr.getRight());
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * Recursive helper function that finds the lowest common ancestor of the given data
     *
     * @param curr the current BSTNode
     * @param data1 the first data to be found in the tree
     * @param data2 the second data to be found in the tree
     * @return the next node in the traversal
     */
    private BSTNode<T> rFindAncestor(BSTNode<T> curr, T data1, T data2) {
        if (curr == null) {
            throw new NoSuchElementException("Data1 or data2 is not in the BST");
        } else if (data1.compareTo(curr.getData()) < 0 && data2.compareTo(curr.getData()) < 0) {
            return rFindAncestor(curr.getLeft(), data1, data2);
        } else if (data1.compareTo(curr.getData()) > 0 && data2.compareTo(curr.getData()) > 0) {
            return rFindAncestor(curr.getRight(), data1, data2);
        } else {
            return curr;
        }
    }

    /**
     * Recursive helper method that adds to the front of the list
     *
     * @param curr the current BSTNode
     * @param list the list of the traversal path between the two data items
     * @param data the first data element to be found
     */
    private void rFindPath1(BSTNode<T> curr, Deque<T> list, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data1 is not in the BST");
        } else if (data.compareTo(curr.getData()) == 0) {
            list.addFirst(curr.getData());
        } else if (data.compareTo(curr.getData()) < 0) {
            list.addFirst(curr.getData());
            rFindPath1(curr.getLeft(), list, data);
        } else if (data.compareTo(curr.getData()) > 0) {
            list.addFirst(curr.getData());
            rFindPath1(curr.getRight(), list, data);
        }
    }

    /**
     * Recursive helper method that adds to the back of the list
     *
     * @param curr the current BSTNode
     * @param list the list of the traversal path between the two data items
     * @param data the second data element to be found
     */
    private void rFindPath2(BSTNode<T> curr, Deque<T> list, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data2 is not in the BST");
        } else if (data.compareTo(curr.getData()) == 0) {
            list.addLast(curr.getData());
        } else if (data.compareTo(curr.getData()) < 0) {
            list.addLast(curr.getData());
            rFindPath2(curr.getLeft(), list, data);
        } else if (data.compareTo(curr.getData()) > 0) {
            list.addLast(curr.getData());
            rFindPath2(curr.getRight(), list, data);
        }
    }
}
