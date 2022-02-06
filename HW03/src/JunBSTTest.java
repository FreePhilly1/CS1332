import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JunBSTTest {
    private static final int TIMEOUT = 200;
    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              2
             /
            0
             \
              1
        */

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        tree = new BST<>(data);

        assertEquals(3, tree.size());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getRight()
                .getData());
    }

    @Test(timeout = TIMEOUT)
    public void add() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(5); // Adding the value that is already in the tree

        assertEquals(6, tree.size());
        assertEquals((Integer) 5, tree.getRoot().getData());
        assertEquals((Integer) 2, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 8, tree.getRoot().getRight().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 9, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 15, tree.getRoot().getRight().getRight().getRight().getData());

        tree.add(3);
        tree.add(6); // Adds to somewhere in the right side of the tree

        assertEquals(8, tree.size());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getLeft().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void remove() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(5);
        tree.add(3);
        tree.add(6);

        tree.remove(8); // Remove the node with two child (7 should replace its place)

        assertEquals(7, tree.size());
        assertEquals((Integer) 7, tree.getRoot().getRight().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 9, tree.getRoot().getRight().getRight().getData());

        tree.remove(2); // Remove node with one child (3 should replace its place)

        assertEquals(6, tree.size());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getData());

        tree.remove(3); // Remove node with zero child

        assertEquals(5, tree.size());
        assertEquals((Integer) 5, tree.getRoot().getData());

        tree.remove(7); // Again remove node with two child
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertEquals((Integer) 9, tree.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void get() {
        Integer temp5 = 5;
        Integer temp8 = 8;
        Integer temp9 = 9;
        Integer temp7 = 7;
        Integer temp15 = 15;
        Integer temp2 = 2;
        Integer temp3 = 3;
        Integer temp6 = 6;

        tree.add(temp5);
        tree.add(temp8);
        tree.add(temp9);
        tree.add(temp7);
        tree.add(temp2);
        tree.add(temp15);
        tree.add(temp3);
        tree.add(temp6);

        assertEquals(8, tree.size());

        assertSame(temp2, tree.get(2));
        assertSame(temp3, tree.get(3));
        assertSame(temp5, tree.get(5));
        assertSame(temp6, tree.get(6));
        assertSame(temp7, tree.get(7));
        assertSame(temp8, tree.get(8));
        assertSame(temp9, tree.get(9));
        assertSame(temp15, tree.get(15));
    }

    @Test(timeout = TIMEOUT)
    public void contains() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(5);
        tree.add(3);
        tree.add(6);

        assertTrue(tree.contains(5));
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(9));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(15));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(6));

        assertTrue(!tree.contains(4));
    }

    @Test(timeout = TIMEOUT)
    public void preorder() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(3);
        list.add(8);
        list.add(7);
        list.add(6);
        list.add(9);
        list.add(15);

        assertEquals(list, tree.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void inorder() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(15);

        // Should be equal to [2 3 5 6 7 8 9 15]
        assertEquals(list, tree.inorder());

        tree.clear();
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> inorder = new ArrayList<>();
        inorder.add(0);
        inorder.add(1);
        inorder.add(2);
        inorder.add(3);
        inorder.add(4);
        inorder.add(5);
        inorder.add(6);
        inorder.add(7);
        inorder.add(8);

        // Should be [0, 1, 2, 3, 4, 5, 6, 7, 8]
        assertEquals(inorder, tree.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void postorder() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(6);
        list.add(7);
        list.add(15);
        list.add(9);
        list.add(8);
        list.add(5);

        // Should be [3 2 6 7 15 9 8 5]
        assertEquals(list, tree.postorder());

        tree.clear();

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> postorder = new ArrayList<>();
        postorder.add(2);
        postorder.add(1);
        postorder.add(0);
        postorder.add(5);
        postorder.add(7);
        postorder.add(6);
        postorder.add(4);
        postorder.add(8);
        postorder.add(3);

        // Should be [2, 1, 0, 5, 7, 6, 4, 8, 3]
        assertEquals(postorder, tree.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void levelorder() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(3);
        list.add(7);
        list.add(9);
        list.add(6);
        list.add(15);

        // Should be [5 2 8 3 7 9 6 15]
        assertEquals(list, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void height() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        assertEquals(3, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void clear() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        tree.clear();
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }

    @Test(timeout = TIMEOUT)
    public void findPathBetween() {
        tree.add(5);
        tree.add(8);
        tree.add(9);
        tree.add(7);
        tree.add(2);
        tree.add(15);
        tree.add(3);
        tree.add(6);

        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(5);
        list.add(8);
        list.add(9);
        list.add(15);

        // The common ancestor is the root, and leaf to leaf
        assertEquals(list, tree.findPathBetween(3, 15));

        List<Integer> list2 = new ArrayList<>();
        list2.add(6);
        list2.add(7);
        list2.add(8);
        list2.add(9);
        list2.add(15);

        // Common ancestor should be 8
        assertEquals(list2, tree.findPathBetween(6, 15));

        tree.add(1);
        tree.add(0);

        List<Integer> list3 = new ArrayList<>();
        list3.add(0);
        list3.add(1);
        list3.add(2);
        list3.add(3);

        // Common ancestor should be 2
        assertEquals(list3, tree.findPathBetween(0, 3));

        List<Integer> list4 = new ArrayList<>();
        list4.add(15);
        list4.add(9);
        list4.add(8);
        list4.add(5);

        // When data1 is bigger than data2
        assertEquals(list4, tree.findPathBetween(15, tree.getRoot().getData()));

        list4.add(2);
        list4.add(1);

        // Goes from right to left
        assertEquals(list4, tree.findPathBetween(15, 1));
    }
}