import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
/**
 * Extra Tests for the AVL code! Will test the rotations and the challenge methods more intensively.
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class AVLStudentExtraTest {
    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              1
             / \
            0   2
                 \
                  3
                   \
                    4
        AFTER LEFT ROTATION:
              1
             / \
            0   3
               / \
              2   4
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        toAdd.add(3);
        toAdd.add(4);
        tree = new AVL<>(toAdd);

        assertEquals(5, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> threeNode = root.getRight();
        assertEquals((Integer) 3, threeNode.getData());
        assertEquals(1, threeNode.getHeight());
        assertEquals(0, threeNode.getBalanceFactor());
        AVLNode<Integer> twoNode = root.getRight().getLeft();
        assertEquals((Integer) 2, twoNode.getData());
        assertEquals(0, twoNode.getHeight());
        assertEquals(0, twoNode.getBalanceFactor());
        AVLNode<Integer> fourthNode = root.getRight().getRight();
        assertEquals((Integer) 4, fourthNode.getData());
        assertEquals(0, fourthNode.getHeight());
        assertEquals(0, fourthNode.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        // Right rotate
        /*
              1
             / \
            0   2
           /
          -1
          /
        -2
        AFTER RIGHT ROTATION:
              1
             / \
           -1   2
           / \
         -2   0
         */

        tree.add(2);
        tree.add(1);
        tree.add(0);
        tree.add(-1);
        tree.add(-2);

        assertEquals(5, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) (-1), left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        AVLNode<Integer> leftRight = root.getLeft().getRight();
        assertEquals((Integer) 0, leftRight.getData());
        assertEquals(0, leftRight.getHeight());
        assertEquals(0, leftRight.getBalanceFactor());
        AVLNode<Integer> rightLeft = root.getLeft().getLeft();
        assertEquals((Integer) (-2), rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        // Right left rotate
        /*
            0
             \
              2
             /
            1

            ->

              1
             / \
            0   2
                 \
                  4
                 /
                3

             ->

              1
             / \
            0   3
               / \
              2   4
         */

        tree = new AVL<>();
        tree.add(0);
        tree.add(2);
        tree.add(1);
        tree.add(4);
        tree.add(3);

        assertEquals(5, tree.size());

        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 3, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        rightLeft = root.getRight().getLeft();
        assertEquals((Integer) 2, rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        assertEquals((Integer) 4, right.getRight().getData());
        assertEquals(0, right.getRight().getHeight());
        assertEquals(0, right.getRight().getBalanceFactor());

        // Left right rotate
        /*
            2
           /
          0
           \
            1

            ->

              1
             / \
            0   2
           /
          -2
           \
            -1

             ->

              1
             / \
           -1   2
           / \
         -2   0
         */

        tree = new AVL<>();
        tree.add(2);
        tree.add(0);
        tree.add(1);
        tree.add(-2);
        tree.add(-1);

        assertEquals(5, tree.size());

        root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) (-1), left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        rightLeft = root.getLeft().getLeft();
        assertEquals((Integer) (-2), rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        assertEquals((Integer) 0, left.getRight().getData());
        assertEquals(0, left.getRight().getHeight());
        assertEquals(0, left.getRight().getBalanceFactor());
    }


    @Test(timeout = TIMEOUT)
    public void testRemove1() {
        Integer temp = 1;

        /*
                  3
                /   \
              1      4
             / \
            0   2

            -> Remove(1)

                 3
               /   \
              2      4
             /
            0

            -> Remove(4)

                3            2
               /            / \
              2     ->     0   3
             /
            0
         */


        tree.add(3);
        tree.add(temp);
        tree.add(4);
        tree.add(0);
        tree.add(2);
        assertEquals(5, tree.size());

        //Remove of data 1
        assertSame(temp, tree.remove(1));
        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 2, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(1, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        AVLNode<Integer> leftLeft = left.getLeft();
        assertEquals((Integer) 0, leftLeft.getData());
        assertEquals(0, leftLeft.getHeight());
        assertEquals(0, leftLeft.getBalanceFactor());

        //Remove of data 4.
        assertSame((Integer) 4, tree.remove(4));
        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 2, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 3, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testRemove2() {
        Integer temp = 1;

        /*
                  3
                /   \
              1      4
             / \
            0   2

            -> Remove (3)

                4            1
               /            / \
              1     - >    0   4
             / \              /
            0   2            2

            -> Remove (1)
                 2
               /   \
              0     4
         */


        tree.add(3);
        tree.add(temp);
        tree.add(4);
        tree.add(0);
        tree.add(2);
        assertEquals(5, tree.size());
        //Remove data 3 from the tree
        assertSame((Integer) 3, tree.remove(3));
        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(1, right.getBalanceFactor());
        AVLNode<Integer> rightLeft = right.getLeft();
        assertEquals((Integer) 2, rightLeft.getData());
        assertEquals(0, rightLeft.getHeight());
        assertEquals(0, rightLeft.getBalanceFactor());

        //Remove data 1 from the tree
        assertSame(temp, tree.remove(1));
        assertEquals(3, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 2, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        right = root.getRight();
        assertEquals((Integer) 4, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp1 = 1;
        Integer temp0 = 0;
        Integer temp2 = 2;
        Integer temp3 = 3;

        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(temp1);
        tree.add(temp0);
        tree.add(temp2);
        tree.add(temp3);
        assertEquals(4, tree.size());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in.
        assertSame(temp0, tree.get(0));
        assertSame(temp1, tree.get(1));
        assertSame(temp2, tree.get(2));
        assertSame(temp3, tree.get(3));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
               1
             /   \
            0     2
                    \
                     3
         */

        tree.add(1);
        tree.add(0);
        tree.add(2);
        tree.add(3);
        assertEquals(4, tree.size());

        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                  3
                 /  \
                1     5
               / \   / \
              0   2 4   6
         */

        tree.add(3);
        tree.add(2);
        tree.add(4);
        tree.add(1);
        tree.add(5);
        tree.add(0);
        tree.add(6);

        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        /*
              1
             / \
            0   2
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(0);
        toAdd.add(2);
        tree = new AVL<>(toAdd);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessor1() {
        /*
                76
              /    \
            34      90
           /  \    /  \
          25  40  81   99
         */

        tree.add(76);
        tree.add(34);
        tree.add(90);
        tree.add(40);
        tree.add(81);
        tree.add(25);
        tree.add(99);

        assertEquals((Integer) 40, tree.predecessor(76));
        assertEquals((Integer) 34, tree.predecessor(40));
        assertEquals((Integer) 76, tree.predecessor(81));
        assertEquals((Integer) 81, tree.predecessor(90));
        assertEquals((Integer) 25, tree.predecessor(34));
        assertNull(tree.predecessor(25));
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallest() {
        /*
                    76
                 /      \
               34        90
              /  \
            20    40
         */

        tree.add(76);
        tree.add(34);
        tree.add(90);
        tree.add(20);
        tree.add(40);

        List<Integer> smallest = new ArrayList<>();

        assertEquals(smallest, tree.kSmallest(0));
        smallest.add(20);
        assertEquals(smallest, tree.kSmallest(1));
        smallest.add(34);
        assertEquals(smallest, tree.kSmallest(2));
        smallest.add(40);

        // Should be [20, 34, 40]
        assertEquals(smallest, tree.kSmallest(3));

        smallest.add(76);

        // Should be [20, 34, 40, 76]
        assertEquals(smallest, tree.kSmallest(4));

        smallest.add(90);

        // Should be [20, 34, 40, 76, 81, 90]
        assertEquals(smallest, tree.kSmallest(5));
    }
}