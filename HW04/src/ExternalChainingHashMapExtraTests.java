import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * JUnits Tests: Extra tests for the HashMap using external chaining! Will try to test as many cases as possible!
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class ExternalChainingHashMapExtraTests {
    private static final int TIMEOUT = 300;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testPut() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old

        expected[4] = new ExternalChainingMapEntry<>(82, "Jun");
        expected[4].setNext(new ExternalChainingMapEntry<>(4, "Mike"));
        expected[2] = new ExternalChainingMapEntry<>(15, "James");
        expected[11] = new ExternalChainingMapEntry<>(76, "Katie");
        expected[5] = new ExternalChainingMapEntry<>(31, "Erik");
        expected[8] = new ExternalChainingMapEntry<>(99, "Michael");
        expected[11].setNext(new ExternalChainingMapEntry<>(11, "Derrick"));

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testRemove() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old
        assertSame("Erik", map.remove(31));
        assertSame("Jun", map.remove(82));

        expected[4] = (new ExternalChainingMapEntry<>(4, "Mike"));
        expected[2] = new ExternalChainingMapEntry<>(15, "James");
        expected[11] = new ExternalChainingMapEntry<>(76, "Katie");
        expected[5] = null;
        expected[8] = new ExternalChainingMapEntry<>(99, "Michael");
        expected[11].setNext(new ExternalChainingMapEntry<>(11, "Derrick"));

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());

        assertSame("James", map.get(15));
        assertSame("Erik", map.get(31));
        assertSame("Mike", map.get(4));
        assertSame("Derrick", map.get(11));
        assertSame("Menelik", map.get(99));
        assertSame("Katie", map.get(76));
        assertSame("Jun", map.get(82));

        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old

        assertSame("Michael", map.get(99));


    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());

        assertTrue(map.containsKey(99));
        assertTrue(map.containsKey(31));
        assertTrue(map.containsKey(76));
        assertSame("Katie", map.remove(76));
        assertFalse(map.containsKey(76));
        assertFalse(map.containsKey(62));
        assertFalse(map.containsKey(5));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());
        Set<Integer> expected = new HashSet<>();

        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old

        expected.add(76);
        expected.add(4);
        expected.add(99);
        expected.add(82);
        expected.add(11);
        expected.add(31);
        expected.add(15);

        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4

        assertEquals(7, map.size());

        List<String> expected = new LinkedList<>();
        expected.add("James");
        expected.add("Jun");
        expected.add("Mike");
        expected.add("Erik");
        expected.add("Menelik");
        expected.add("Katie");
        expected.add("Derrick");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testResize1() {
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2 -> 15 % 27 = 15
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5 -> 31 % 27 = 4
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4 -> 4 % 27 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11 -> 11 % 27 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8 -> 99 % 27 = 18
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11 -> 76 % 27 = 22
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4 -> 82 % 27 = 1
        assertNull(map.put(98, "Kyle")); //index = 98 % 13 = 7 -> 98 % 27 = 17
        assertNull(map.put(57, "Ryan")); //RESIZE: index 57 % 27 = 3

        assertEquals(9, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[(ExternalChainingHashMap
                                .INITIAL_CAPACITY * 2) + 1];

        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old

        expected[15] = new ExternalChainingMapEntry<>(15, "James");
        expected[4] = new ExternalChainingMapEntry<>(31, "Erik");
        expected[4].setNext(new ExternalChainingMapEntry<>(4, "Mike"));
        expected[11] = new ExternalChainingMapEntry<>(11, "Derrick");
        expected[18] = new ExternalChainingMapEntry<>(99, "Michael");
        expected[22] = new ExternalChainingMapEntry<>(76, "Katie");
        expected[1] = new ExternalChainingMapEntry<>(82, "Jun");
        expected[17] = new ExternalChainingMapEntry<>(98, "Kyle");
        expected[3] = new ExternalChainingMapEntry<>(57, "Ryan");

        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testResize2() { //This is the resize case when you call the resize method itself
        assertNull(map.put(15, "James")); //index = 15 % 13 = 2
        assertNull(map.put(31, "Erik")); //index = 31 % 13 = 5
        assertNull(map.put(4, "Mike")); //index = 4 % 13 = 4
        assertNull(map.put(11, "Derrick")); //index = 11 % 13 = 11
        assertNull(map.put(99, "Menelik")); //index = 99 % 13 = 8
        assertNull(map.put(76, "Katie")); //index = 76 % 13 = 11
        assertNull(map.put(82, "Jun")); //index = 82 % 13 = 4


        assertEquals("Menelik", map.put(99, "Michael")); //Should replace Menelik with Michael, return the old
        assertEquals(7, map.size());

        map.resizeBackingTable(map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[7];

        expected[1] = new ExternalChainingMapEntry<>(99, "Michael"); //index = 99 % 7 = 1
        expected[1].setNext(new ExternalChainingMapEntry<>(15, "James")); //Index = 15 % 7 = 1
        expected[3] = new ExternalChainingMapEntry<>(31, "Erik"); //index = 31 % 7 = 3
        expected[4] = new ExternalChainingMapEntry<>(11, "Derrick"); //index = 11 % 7 = 4
        expected[4].setNext(new ExternalChainingMapEntry<>(4, "Mike")); //index = 4 % 7 = 4
        expected[6] = new ExternalChainingMapEntry<>(76, "Katie"); //index = 76 % 7 = 6
        expected[5] = new ExternalChainingMapEntry<>(82, "Jun"); //index 82 % 7 = 5

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }
}