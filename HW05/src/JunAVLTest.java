import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Jun Yeop Kim's HW5 JUnit test.
 * Used csvistool.com to check tree shape.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 */
public class JunAVLTest {
    private static final int TIMEOUT = 1000;
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
                   3
                 1    6
                    5   7
         */
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(3);
        list.add(1);
        list.add(7);
        list.add(6);
        tree = new AVL<>(list);

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 1, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 6, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void add() {
        tree.add(6);
        tree.add(4);
        tree.add(9);
        tree.add(2);
        tree.add(5);
        tree.add(1); // Right Rotation

        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 2, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(1, left.getBalanceFactor());
        assertEquals((Integer) 1, left.getLeft().getData());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 6, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        assertEquals((Integer) 5, right.getLeft().getData());
        assertEquals((Integer) 9, right.getRight().getData());

        tree.clear();

        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(9);
        tree.add(6);
        tree.add(12); // Left Rotation

        root = tree.getRoot();
        assertEquals((Integer) 7, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 5, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());

        right = root.getRight();
        assertEquals((Integer) 9, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(-1, right.getBalanceFactor());

        tree.clear();
        tree.add(5);
        tree.add(4);
        tree.add(9);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(8); // Right-Left Rotation

        /*
                 7
              5     9
            4  6  8  12
         */

        root = tree.getRoot();
        assertEquals((Integer) 7, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        left = root.getLeft();
        assertEquals((Integer) 5, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        assertEquals((Integer) 4, left.getLeft().getData());
        assertEquals((Integer) 6, left.getRight().getData());

        right = root.getRight();
        assertEquals((Integer) 9, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
        assertEquals((Integer) 8, right.getLeft().getData());
        assertEquals((Integer) 12, right.getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void remove() {
        /*
                   3
                 1    6
                    5   7
         */
        Integer temp = 6;

        tree.add(5);
        tree.add(3);
        tree.add(1);
        tree.add(7);
        tree.add(temp);

        assertSame(temp, tree.remove(6)); // Remove node with two child
        assertEquals(4, tree.size());
        assertEquals(2, tree.getRoot().getHeight());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 7, root.getRight().getData());

        assertSame(1, tree.remove(1)); // Remove node with zero child
        assertEquals(3, tree.size());

        tree.add(2);

        assertSame(3, tree.remove(3)); // Remove node with one child
        assertEquals(3, tree.size());
        assertEquals((Integer) 2, root.getLeft().getData());

        tree.clear();
        /*
                4
              3  12
            1   6   17
               5 9     33
         */

        tree.add(4);
        tree.add(12);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        tree.add(17);
        tree.add(5);
        tree.add(9);
        tree.add(33);

        /*
                4
              3  12
            1   9   17
               5     33  Result after remove 6
         */
        assertSame(6, tree.remove(6)); // Remove node with two child
        root = tree.getRoot();
        assertEquals((Integer) 9, root.getRight().getLeft().getData());
        assertEquals((Integer) 12, root.getRight().getData());
        assertEquals((Integer) 17, root.getRight().getRight().getData());
        assertEquals(8, tree.size());

        /*
                12
              4    17
            1   9    33  result after remove 3
               5
         */
        assertSame(3, tree.remove(3)); // Remove node with one child and then left rotation
        assertEquals(7, tree.size());
        root = tree.getRoot();
        assertEquals((Integer) 12, root.getData());

        assertEquals(3, tree.height());
        assertEquals(1, root.getBalanceFactor()); // Check height and balance

        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 4, left.getData());
        assertEquals((Integer) 1, left.getLeft().getData());
        assertEquals((Integer) 9, left.getRight().getData());
        assertEquals((Integer) 5, left.getRight().getLeft().getData());

        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 17, right.getData());
        assertEquals((Integer) 33, right.getRight().getData());

        tree.clear();

        /*
                4
              3  12
            1   6   17
               5 9     33
         */

        tree.add(4);
        tree.add(12);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        tree.add(17);
        tree.add(5);
        tree.add(9);
        tree.add(33);

        /*
                6
             4    17
           3  5  9  33

         */
        assertSame(12, tree.remove(12));
        assertSame(1, tree.remove(1));
        assertEquals(2, tree.height());

        root = tree.getRoot();

        assertEquals((Integer) 6, root.getData());
        assertEquals((Integer) 4, root.getLeft().getData());
        assertEquals((Integer) 17, root.getRight().getData());

        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(0, root.getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void get() {
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

        tree.clear();

        temp0 = 5;
        temp1 = 3;
        temp2 = 1;
        temp3 = 6;
        Integer temp4 = 7;

        tree.add(temp0);
        tree.add(temp1);
        tree.add(temp2);
        tree.add(temp3);
        tree.add(temp4);

        assertSame(temp0, tree.get(5));
        assertSame(temp1, tree.get(3));
        assertSame(temp2, tree.get(1));
        assertSame(temp3, tree.get(6));
        assertSame(temp4, tree.get(7)); // Just double check

    }

    @Test(timeout = TIMEOUT)
    public void contains() {
        tree.add(5);
        tree.add(4);
        tree.add(9);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(8);

        assertTrue(tree.contains(5));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(9));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(6));
        assertTrue(tree.contains(8));

        assertFalse(tree.contains(0));
        assertFalse(tree.contains(2));
    }

    @Test(timeout = TIMEOUT)
    public void height() {
        tree.add(5);
        tree.add(4);
        tree.add(9);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(8);

        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void clear() {
        tree.add(5);
        tree.add(4);
        tree.add(9);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(8);

        tree.clear();

        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void predecessor() {
        /*
                4
              3  12
            1   6   17
               5 9     33
         */
        tree.add(4);
        tree.add(12);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        tree.add(17);
        tree.add(5);
        tree.add(9);
        tree.add(33);

        assertEquals((Integer) 3, tree.predecessor(4));
        assertEquals((Integer) 4, tree.predecessor(5));
        assertEquals((Integer) 5, tree.predecessor(6));
        assertEquals((Integer) 1, tree.predecessor(3));
        assertEquals((Integer) 12, tree.predecessor(17));
        assertEquals((Integer) 17, tree.predecessor(33));
        assertEquals((Integer) 6, tree.predecessor(9)); // All of them have a deepest ancestor

        assertNull(tree.predecessor(1));
    }

    @Test(timeout = TIMEOUT)
    public void kSmallest() {
        /*
                4
              3  12
            1   6   17
               5 9     33
         */

        tree.add(4);
        tree.add(12);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        tree.add(17);
        tree.add(5);
        tree.add(9);
        tree.add(33);

        List<Integer> list = new ArrayList<>();

        assertEquals(list, tree.kSmallest(0));

        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);

        assertEquals(list, tree.kSmallest(4));

        list.add(6);
        list.add(9);

        assertEquals(list, tree.kSmallest(6));

        list.add(12);
        list.add(17);
        list.add(33);

        assertEquals(list, tree.kSmallest(tree.size()));
    }

}