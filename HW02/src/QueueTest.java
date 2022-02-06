import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnits Test for the two implementations of the Queue ADT!
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class QueueTest {
    private static final int TIMEOUT = 350;
    private ArrayQueue<String> array;
    private LinkedQueue<String> linked;

    @Before
    public void setup() {
        array = new ArrayQueue<>();
        linked = new LinkedQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[9], array.getBackingArray());

        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayEnqueue() {
        array.enqueue("0a");    // 0a
        assertEquals(1, array.size());
        array.enqueue("1a");    // 0a, 1a
        assertEquals(2, array.size());
        array.enqueue("2a");    // 0a, 1a, 2a
        assertEquals(3, array.size());
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        assertEquals(4, array.size());
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPop() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, array.size());

        assertSame(temp, array.dequeue());  // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPeek() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, array.size());

        assertSame(temp, array.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedEnqueue() {
        linked.enqueue("0a");   // 0a
        assertEquals(1, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertSame(linked.getHead(), linked.getTail());
        linked.enqueue("1a");   // 0a, 1a
        assertEquals(2, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("1a", linked.getTail().getData());
        linked.enqueue("2a");   // 0a, 1a, 2a
        assertEquals(3, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("2a", linked.getTail().getData());
        linked.enqueue("3a");   // 0a, 1a, 2a, 3a
        assertEquals(4, linked.size());
        assertEquals("0a", linked.getHead().getData());
        assertEquals("3a", linked.getTail().getData());
        linked.enqueue("4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals("0a", linked.getHead().getData());
        assertEquals("4a", linked.getTail().getData());
        assertEquals(5, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeue() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, linked.size());

        assertSame(temp, linked.dequeue()); // 1a, 2a, 3a, 4a, 5a
        assertEquals("1a", linked.getHead().getData());
        assertEquals(5, linked.size());

        assertSame("1a", linked.dequeue()); // 2a, 3a, 4a, 5a
        assertEquals("2a", linked.getHead().getData());
        assertEquals(4, linked.size());

        assertSame("2a", linked.dequeue()); // 3a, 4a, 5a
        assertEquals("3a", linked.getHead().getData());
        assertEquals(3, linked.size());

        assertSame("3a", linked.dequeue()); // 4a, 5a
        assertEquals("4a", linked.getHead().getData());
        assertEquals(2, linked.size());

        assertSame("4a", linked.dequeue()); // 5a
        assertEquals("5a", linked.getHead().getData());
        assertEquals(1, linked.size());
        assertSame(linked.getHead(), linked.getTail());

        assertSame("5a", linked.dequeue()); //
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(0, linked.size());

        linked.enqueue("0a");
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertSame(temp, linked.dequeue()); // 1a, 2a, 3a, 4a, 5a

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("5a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPeek() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, linked.size());

        assertSame(temp, linked.peek());
    }
}