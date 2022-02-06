import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * BST Extra Test Class: Includes more complicated and indepth tests of each of the methods!
 * Does strong tests on the find path between method!
 * And Good Luck on the first exam!
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class BSTDeepTest {

    private static final int TIMEOUT = 300;
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

    public void testConstructor() {
        List<Integer> list = new LinkedList<>();
        list.add(45);
        list.add(32);
        list.add(48);
        list.add(67);
        list.add(59);
        list.add(40);
        list.add(1);
        list.add(4);

        tree = new BST<>(list);
        /**
         * Tree being made!
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        assertEquals(8, tree.size());

        assertEquals((Integer) 45, tree.getRoot().getData());
        assertEquals((Integer) 32, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 48, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 40, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 67, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 59, tree.getRoot().getRight().getRight().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        /*
              2
             / \
            1   6
           /   / \
          0   4   7
        */

        tree.add(2);
        tree.add(1);
        tree.add(0);
        tree.add(2); //Shouldn't be added as it already exists!
        tree.add(6);
        tree.add(4);
        tree.add(7);

        assertEquals(6, tree.size());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        /*
              2
             / \
            1   6
           /   / \
          0   4   7
        */

        tree.add(2);
        tree.add(1);
        tree.add(0);
        tree.add(2); //Shouldn't be added as it already exists!
        tree.add(6);
        tree.add(4);
        tree.add(7);

        assertEquals(6, tree.size());
        assertEquals(2, tree.height());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getRight().getData());

        //Case of 0 child node removal:

        assertEquals((Integer) 4, tree.remove(4));
        /*
              2
             / \
            1   6
           /     \
          0       7
        */
        assertEquals(5, tree.size());
        assertEquals(2, tree.height());
        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals((Integer) 7, tree.getRoot().getRight().getRight().getData());

        //Case of 1 child node removal:

        assertEquals((Integer) 1, tree.remove(1));
        /*
              2
             / \
            0   6
                 \
                  7
        */
        assertEquals(4, tree.size());
        assertEquals(2, tree.height());
        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getRight().getData());

        //Case of 2 child node removal (Predecessor):

        assertEquals((Integer) 2, tree.remove(2));
        /*
              0
               \
                6
                 \
                  7


        */
        assertEquals(3, tree.size());
        assertEquals(2, tree.height());
        assertEquals((Integer) 0, tree.getRoot().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp3 = 3;
        Integer temp0 = 0;
        Integer temp1 = 1;
        Integer temp2 = 2;
        Integer temp6 = 6;
        Integer temp4 = 4;
        Integer temp5 = 5;

        /*
                3
             /     \
            0       6
             \     /
              1   4
               \   \
                2   5
        */

        tree.add(temp3);
        tree.add(temp0);
        tree.add(temp1);
        tree.add(temp2);
        tree.add(temp6);
        tree.add(temp4);
        tree.add(temp5);
        assertEquals(7, tree.size());
        assertEquals(3, tree.height());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in.
        assertSame(temp0, tree.get(0));
        assertSame(temp1, tree.get(1));
        assertSame(temp2, tree.get(2));
        assertSame(temp3, tree.get(3));
        assertSame(temp4, tree.get(4));
        assertSame(temp5, tree.get(5));
        assertSame(temp6, tree.get(6));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                3
             /     \
            0       6
             \     /
              1   4
               \   \
                2   5
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(6);
        tree.add(4);
        tree.add(5);
        assertEquals(7, tree.size());
        assertEquals(3, tree.height());

        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(6));
        assertFalse(tree.contains(7));
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder() {
        tree.add(45);
        tree.add(32);
        tree.add(48);
        tree.add(67);
        tree.add(59);
        tree.add(40);
        tree.add(1);
        tree.add(4);

        /**
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        assertEquals(8, tree.size());
        assertEquals(3, tree.height());

        assertEquals((Integer) 45, tree.getRoot().getData());
        assertEquals((Integer) 32, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 48, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 40, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 67, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 59, tree.getRoot().getRight().getRight().getLeft().getData());

        List<Integer> preorderList = tree.preorder();
        List<Integer> preorderActual = new LinkedList<>();
        preorderActual.add(45);
        preorderActual.add(32);
        preorderActual.add(1);
        preorderActual.add(4);
        preorderActual.add(40);
        preorderActual.add(48);
        preorderActual.add(67);
        preorderActual.add(59);
        // Should be [45, 32, 1, 4, 40, 48, 67, 59]
        assertEquals(preorderActual, preorderList);
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder() {
        tree.add(45);
        tree.add(32);
        tree.add(48);
        tree.add(67);
        tree.add(59);
        tree.add(40);
        tree.add(1);
        tree.add(4);

        /**
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        assertEquals(8, tree.size());
        assertEquals(3, tree.height());

        assertEquals((Integer) 45, tree.getRoot().getData());
        assertEquals((Integer) 32, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 48, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 40, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 67, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 59, tree.getRoot().getRight().getRight().getLeft().getData());

        List<Integer> postorderList = tree.postorder();
        List<Integer> postorderActual = new LinkedList<>();
        postorderActual.add(4);
        postorderActual.add(1);
        postorderActual.add(40);
        postorderActual.add(32);
        postorderActual.add(59);
        postorderActual.add(67);
        postorderActual.add(48);
        postorderActual.add(45);
        // Should be [4, 1, 40, 32, 59, 67, 48, 45]
        assertEquals(postorderActual, postorderList);
    }

    @Test(timeout = TIMEOUT)
    public void testInorder() {
        tree.add(45);
        tree.add(32);
        tree.add(48);
        tree.add(67);
        tree.add(59);
        tree.add(40);
        tree.add(1);
        tree.add(4);

        /**
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        assertEquals(8, tree.size());
        assertEquals(3, tree.height());

        assertEquals((Integer) 45, tree.getRoot().getData());
        assertEquals((Integer) 32, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 48, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 40, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 67, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 59, tree.getRoot().getRight().getRight().getLeft().getData());

        List<Integer> inorderList = tree.inorder();
        List<Integer> inorderActual = new LinkedList<>();
        inorderActual.add(1);
        inorderActual.add(4);
        inorderActual.add(32);
        inorderActual.add(40);
        inorderActual.add(45);
        inorderActual.add(48);
        inorderActual.add(59);
        inorderActual.add(67);
        // Should be [1, 4, 32, 40, 45, 48, 59, 67]
        assertEquals(inorderActual, inorderList);
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        tree.add(45);
        tree.add(32);
        tree.add(48);
        tree.add(67);
        tree.add(59);
        tree.add(40);
        tree.add(1);
        tree.add(4);

        /**
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        assertEquals(8, tree.size());
        assertEquals(3, tree.height());

        assertEquals((Integer) 45, tree.getRoot().getData());
        assertEquals((Integer) 32, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 48, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 40, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 67, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 59, tree.getRoot().getRight().getRight().getLeft().getData());

        List<Integer> levelorderList = tree.levelorder();
        List<Integer> levelorderActual = new LinkedList<>();
        levelorderActual.add(45);
        levelorderActual.add(32);
        levelorderActual.add(48);
        levelorderActual.add(1);
        levelorderActual.add(40);
        levelorderActual.add(67);
        levelorderActual.add(4);
        levelorderActual.add(59);
        // Should be [45, 32, 48, 1, 40, 67, 4, 59]
        assertEquals(levelorderActual, levelorderList);
    }

    @Test(timeout = TIMEOUT)
    public void testPathBetween() {
        tree.add(45);
        tree.add(32);
        tree.add(48);
        tree.add(67);
        tree.add(59);
        tree.add(40);
        tree.add(1);
        tree.add(4);

        /**
         *      45
         *     /  \
         *    32   48
         *   / \    \
         *  1   40  67
         *  \       /
         *   4     59
         */
        List<Integer> pathList1 = tree.findPathBetween(4, 4);
        // [4]
        List<Integer> pathActual1 = new LinkedList<>();
        pathActual1.add(4);
        assertEquals(pathActual1, pathList1);

        List<Integer> pathList2 = tree.findPathBetween(4, 40);
        // [4, 1, 32, 40]
        List<Integer> pathActual2 = new LinkedList<>();
        pathActual2.add(4);
        pathActual2.add(1);
        pathActual2.add(32);
        pathActual2.add(40);
        assertEquals(pathActual2, pathList2);

        List<Integer> pathList3 = tree.findPathBetween(40, 4);
        // [40, 32, 1, 4]
        List<Integer> pathActual3 = new LinkedList<>();
        pathActual3.add(40);
        pathActual3.add(32);
        pathActual3.add(1);
        pathActual3.add(4);
        assertEquals(pathActual3, pathList3);

        List<Integer> pathList4 = tree.findPathBetween(45, 59);
        // [45, 48, 67, 59]
        List<Integer> pathActual4 = new LinkedList<>();
        pathActual4.add(45);
        pathActual4.add(48);
        pathActual4.add(67);
        pathActual4.add(59);
        assertEquals(pathActual4, pathList4);

        List<Integer> pathList5 = tree.findPathBetween(59, 45);
        // [59, 67, 48, 45]
        List<Integer> pathActual5 = new LinkedList<>();
        pathActual5.add(59);
        pathActual5.add(67);
        pathActual5.add(48);
        pathActual5.add(45);
        assertEquals(pathActual5, pathList5);

        List<Integer> pathList6 = tree.findPathBetween(4, 48);
        //[4, 1, 32, 45, 48]
        List<Integer> pathActual6 = new LinkedList<>();
        pathActual6.add(4);
        pathActual6.add(1);
        pathActual6.add(32);
        pathActual6.add(45);
        pathActual6.add(48);
        assertEquals(pathActual6, pathList6);

        List<Integer> pathList7 = tree.findPathBetween(48, 4);
        //[48, 45, 32, 1, 4]
        List<Integer> pathActual7 = new LinkedList<>();
        pathActual7.add(48);
        pathActual7.add(45);
        pathActual7.add(32);
        pathActual7.add(1);
        pathActual7.add(4);
        assertEquals(pathActual7, pathList7);

        List<Integer> pathList8 = tree.findPathBetween(67, 59);
        //[67, 59]
        List<Integer> pathActual8 = new LinkedList<>();
        pathActual8.add(67);
        pathActual8.add(59);
        assertEquals(pathActual8, pathList8);

        List<Integer> pathList9 = tree.findPathBetween(59, 67);
        // [59, 67]
        List<Integer> pathActual9 = new LinkedList<>();
        pathActual9.add(59);
        pathActual9.add(67);
        assertEquals(pathActual9, pathList9);
    }
}