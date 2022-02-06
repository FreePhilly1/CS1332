import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test for HW4-Hashmap. Does not guarantee neither accuracy nor efficiency.
 * Focuses on checking if external chaining properly works when using put() or remove().
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 */
public class JunHashMapTest {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void put() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0, 0 % 27 = 0
        assertNull(map.put(1, "B")); // 1 % 13 = 1, 1 % 27 = 1
        assertNull(map.put(2, "C")); // 2 % 13 = 2, 2 % 27 = 2
        assertNull(map.put(33, "D")); // 33 % 13 = 7, 33 % 27 = 6
        assertNull(map.put(4, "E")); // 4 % 13 = 4, 4 % 27 = 4
        assertNull(map.put(5, "F")); // 5 % 13 = 5, 5 % 27 = 5
        assertNull(map.put(13, "A2")); // External Chain - > 13 % 27 = 13

        assertNotNull(map.put(1, "B2")); // Add duplicate
        assertEquals("B2", map.getTable()[1].getValue());

        assertEquals(7, map.size());

        assertNull(map.put(26, "A3")); // 26 % 13 = 0, 26 % 27 = 26

        assertEquals(new ExternalChainingMapEntry<>(26, "A3"), map.getTable()[0]);
        assertEquals(new ExternalChainingMapEntry<>(0, "A"), map.getTable()[0].getNext().getNext());
        // External Chaining test

        assertNull(map.put(82, "G")); // 82 % 13 = 4, 82 % 27 = 1. It should go to index 1.

        assertEquals(9, map.size()); // Resizing the map doesn't resize the size var.
        assertEquals(27, map.getTable().length); // 2 * length + 1

        ExternalChainingMapEntry<Integer, String>[] expected = new
                ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(82, "G");
        expected[1].setNext(new ExternalChainingMapEntry<>(1, "B2"));
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        expected[5] = new ExternalChainingMapEntry<>(5, "F");
        expected[6] = new ExternalChainingMapEntry<>(33, "D");
        expected[13] = new ExternalChainingMapEntry<>(13, "A2");
        expected[26] = new ExternalChainingMapEntry<>(26, "A3");

        assertEquals(expected[1].getNext().getValue(), map.getTable()[1].getNext().getValue());
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        map.put(4, "A"); // 4 -> 4
        map.put(31, "B"); // 5 -> 4
        map.put(1, "C"); // 1 -> 1
        map.put(28, "D"); // 2 -> 1
        map.put(82, "E"); // 4 -> 1
        map.put(6, "F"); // 6 -> 6
        map.put(7, "G"); // 7 -> 7
        map.put(8, "H"); // 8 -> 8
        map.put(35, "Resize"); // 9 -> 8

        assertEquals(9, map.size());
        assertEquals(27, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[(ExternalChainingHashMap
                                .INITIAL_CAPACITY * 2) + 1];

        expected[1] = new ExternalChainingMapEntry<>(82, "E");
        expected[1].setNext(new ExternalChainingMapEntry<>(28, "D"));
        expected[1].getNext().setNext(new ExternalChainingMapEntry<>(1, "C"));
        expected[4] = new ExternalChainingMapEntry<>(31, "B");
        expected[4].setNext(new ExternalChainingMapEntry<>(4, "A"));
        expected[6] = new ExternalChainingMapEntry<>(6, "F");
        expected[7] = new ExternalChainingMapEntry<>(7, "G");
        expected[8] = new ExternalChainingMapEntry<>(35, "Resize");
        expected[8].setNext(new ExternalChainingMapEntry<>(8, "H"));


        assertEquals(expected[1].getNext().getNext().getValue(), map.getTable()[1].getNext().getNext().getValue());
        assertArrayEquals(expected, map.getTable());

    }



    @Test(timeout = TIMEOUT)
    public void remove() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0
        assertNull(map.put(13, "B")); // 13 % 13 = 0
        assertNull(map.put(26, "C")); // 26 % 13 = 0
        assertNull(map.put(1, "D")); // 1 % 13 = 1
        assertNull(map.put(2, "E")); // 2 % 13 = 2
        assertNull(map.put(3, "F")); // 3 % 13 = 3
        assertNull(map.put(4, "G")); // 4 % 13 = 4
        assertNull(map.put(17, "H")); // 17 % 13 = 4

        assertEquals(8, map.size());

        assertSame("A", map.remove(0)); // Remove end of chain
        assertSame("D", map.remove(1));
        assertSame("E", map.remove(2));

        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected = new
                ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>(26, "C");
        expected[0].setNext(new ExternalChainingMapEntry<>(13, "B"));
        expected[3] = new ExternalChainingMapEntry<>(3, "F");
        expected[4] = new ExternalChainingMapEntry<>(17, "H");
        expected[4].setNext(new ExternalChainingMapEntry<>(4, "G"));

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void get() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0
        assertNull(map.put(13, "B")); // 13 % 13 = 0
        assertNull(map.put(26, "C")); // 26 % 13 = 0
        assertNull(map.put(1, "D")); // 1 % 13 = 1
        assertNull(map.put(2, "E")); // 2 % 13 = 2
        assertNull(map.put(3, "F")); // 3 % 13 = 3
        assertNull(map.put(4, "G")); // 4 % 13 = 4
        assertNull(map.put(17, "H")); // 17 % 13 = 4

        assertSame("A", map.get(0));
        assertSame("B", map.get(13));
        assertSame("C", map.get(26));
        assertSame("D", map.get(1));
        assertSame("E", map.get(2));
        assertSame("F", map.get(3));
        assertSame("G", map.get(4));
        assertSame("H", map.get(17)); // Check index with / without chain

        assertEquals("C", map.put(26, "New"));
        assertSame("New", map.get(26)); // Check if returns the new one


    }

    @Test(timeout = TIMEOUT)
    public void containsKey() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0
        assertNull(map.put(13, "B")); // 13 % 13 = 0
        assertNull(map.put(26, "C")); // 26 % 13 = 0
        assertNull(map.put(1, "D")); // 1 % 13 = 1
        assertNull(map.put(2, "E")); // 2 % 13 = 2
        assertNull(map.put(3, "F")); // 3 % 13 = 3
        assertNull(map.put(4, "G")); // 4 % 13 = 4
        assertNull(map.put(17, "H")); // 17 % 13 = 4

        assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(13));
        assertTrue(map.containsKey(26));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsKey(17));

        map.remove(0);
        map.remove(17);
        map.remove(3);

        assertFalse(map.containsKey(0));
        assertFalse(map.containsKey(17));
        assertFalse(map.containsKey(3)); // Checks if the deleted ones return false
    }

    @Test(timeout = TIMEOUT)
    public void keySet() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0
        assertNull(map.put(13, "B")); // 13 % 13 = 0
        assertNull(map.put(26, "C")); // 26 % 13 = 0
        assertNull(map.put(1, "D")); // 1 % 13 = 1
        assertNull(map.put(2, "E")); // 2 % 13 = 2
        assertNull(map.put(3, "F")); // 3 % 13 = 3
        assertNull(map.put(4, "G")); // 4 % 13 = 4
        assertNull(map.put(17, "H")); // 17 % 13 = 4

        Set<Integer> set = new HashSet<>();
        set.add(26);
        set.add(13);
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(17);

        assertEquals(set, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void values() {
        assertNull(map.put(0, "A")); // 0 % 13 = 0
        assertNull(map.put(13, "B")); // 13 % 13 = 0
        assertNull(map.put(26, "C")); // 26 % 13 = 0
        assertNull(map.put(1, "D")); // 1 % 13 = 1
        assertNull(map.put(2, "E")); // 2 % 13 = 2
        assertNull(map.put(3, "F")); // 3 % 13 = 3
        assertNull(map.put(4, "G")); // 4 % 13 = 4
        assertNull(map.put(17, "H")); // 17 % 13 = 4

        List<String> list = new LinkedList<>();
        list.add("C");
        list.add("B");
        list.add("A");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("H");
        list.add("G"); // Considers the order of external chaining


        assertEquals(list, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void clear() {
        map.put(0, "A");
        assertEquals(1, map.size());

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNSE() {
        map.put(1, "B");
        map.get(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNSE2() {
        map.put(1, "B");
        map.remove(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIAE() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");

        map.resizeBackingTable(2);
    }

}