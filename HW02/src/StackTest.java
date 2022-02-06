import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Tests for the Stack implementation classes!
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class StackTest {
    private static final int TIMEOUT = 400;
    private ArrayStack<String> arrayStack;
    private LinkedStack<String> linkedStack;

    @Before
    public void setup() {
        arrayStack = new ArrayStack<>();
        linkedStack = new LinkedStack<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, arrayStack.size());
        assertArrayEquals(new Object[ArrayStack.INITIAL_CAPACITY],
                arrayStack.getBackingArray());
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPush() {
        linkedStack.push("1A"); // 1A->
        assertEquals("1A", linkedStack.getHead().getData());
        assertEquals(1, linkedStack.size());
        linkedStack.push("2B"); // 2B-> 1A->
        assertEquals("2B", linkedStack.getHead().getData());
        assertEquals(2, linkedStack.size());
        linkedStack.push("3C"); // 3C-> 2B-> 1A->
        assertEquals("3C", linkedStack.getHead().getData());
        assertEquals(3, linkedStack.size());
        linkedStack.push("4D"); // 4D-> 3C-> 2B-> 1A->
        assertEquals("4D", linkedStack.getHead().getData());
        assertEquals(4, linkedStack.size());
        linkedStack.push("5E"); // 5E-> 4D-> 3C-> 2B-> 1A->
        assertEquals("5E", linkedStack.getHead().getData());
        assertEquals(5, linkedStack.size());

        LinkedNode<String> curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("5E", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("4D", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("3C", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPop() {
        linkedStack.push("1A"); // 1A->
        assertEquals("1A", linkedStack.getHead().getData());
        assertEquals(1, linkedStack.size());
        linkedStack.push("2B"); // 2B-> 1A->
        assertEquals("2B", linkedStack.getHead().getData());
        assertEquals(2, linkedStack.size());
        linkedStack.push("3C"); // 3C-> 2B-> 1A->
        assertEquals("3C", linkedStack.getHead().getData());
        assertEquals(3, linkedStack.size());
        linkedStack.push("4D"); // 4D-> 3C-> 2B-> 1A->
        assertEquals("4D", linkedStack.getHead().getData());
        assertEquals(4, linkedStack.size());
        linkedStack.push("5E"); // 5E-> 4D-> 3C-> 2B-> 1A->
        assertEquals("5E", linkedStack.getHead().getData());
        assertEquals(5, linkedStack.size());
        LinkedNode<String> curr = linkedStack.getHead();

        assertNotNull(curr);
        assertEquals("5E", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("4D", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("3C", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("5E", linkedStack.pop());
        assertEquals(4, linkedStack.size());

        curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("4D", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("3C", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("4D", linkedStack.pop());
        assertEquals(3, linkedStack.size());

        curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("3C", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("3C", linkedStack.pop());
        assertEquals(2, linkedStack.size());

        curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("2B", linkedStack.pop());
        assertEquals(1, linkedStack.size());

        curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("1A", linkedStack.pop());
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPeek() {
        linkedStack.push("1A"); // 1A->
        assertEquals("1A", linkedStack.getHead().getData());
        assertEquals(1, linkedStack.size());
        linkedStack.push("2B"); // 2B-> 1A->
        assertEquals("2B", linkedStack.getHead().getData());
        assertEquals(2, linkedStack.size());
        linkedStack.push("3C"); // 3C-> 2B-> 1A->
        assertEquals("3C", linkedStack.getHead().getData());
        assertEquals(3, linkedStack.size());
        linkedStack.push("4D"); // 4D-> 3C-> 2B-> 1A->
        assertEquals("4D", linkedStack.getHead().getData());
        assertEquals(4, linkedStack.size());
        linkedStack.push("5E"); // 5E-> 4D-> 3C-> 2B-> 1A->
        assertEquals("5E", linkedStack.getHead().getData());
        assertEquals(5, linkedStack.size());

        LinkedNode<String> curr = linkedStack.getHead();
        assertNotNull(curr);
        assertEquals("5E", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("4D", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("3C", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("2B", curr.getData());

        curr = curr.getNext();
        assertNotNull(curr);
        assertEquals("1A", curr.getData());

        curr = curr.getNext();
        assertNull(curr);

        assertEquals("5E", linkedStack.peek()); //

        assertEquals("5E", linkedStack.pop());
        assertEquals(4, linkedStack.size());

        assertEquals("4D", linkedStack.peek()); //

        assertEquals("4D", linkedStack.pop()); //
        assertEquals(3, linkedStack.size());

        assertEquals("3C", linkedStack.peek()); //

        assertEquals("3C", linkedStack.pop());
        assertEquals(2, linkedStack.size());

        assertEquals("2B", linkedStack.peek()); //

        assertEquals("2B", linkedStack.pop());
        assertEquals(1, linkedStack.size());

        assertEquals("1A", linkedStack.peek()); //

        assertEquals("1A", linkedStack.pop());
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());

    }

    @Test(timeout = TIMEOUT)
    public void testArrayPush() {
        arrayStack.push("1A");   // 1A
        assertEquals(1, arrayStack.size());
        arrayStack.push("2B");   // 2B, 1A
        assertEquals(2, arrayStack.size());
        arrayStack.push("3C");   // 3C, 2B, 1A
        assertEquals(3, arrayStack.size());
        arrayStack.push("4D");   // 4D, 3C, 2B, 1A
        assertEquals(4, arrayStack.size());
        arrayStack.push("5E");   // 5E, 4D, 3C, 2B, 1A
        assertEquals(5, arrayStack.size());


        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        expected[0] = "1A";
        expected[1] = "2B";
        expected[2] = "3C";
        expected[3] = "4D";
        expected[4] = "5E";

        assertArrayEquals(expected, arrayStack.getBackingArray());
        //Add so that the size expands twice!
        arrayStack.push("6F");
        arrayStack.push("6F");
        arrayStack.push("6F");
        arrayStack.push("6F");
        arrayStack.push("6F");
        assertEquals(10, arrayStack.size());

        arrayStack = new ArrayStack<>();
        for (int i = 0; i < 36; i++) {
            arrayStack.push("7H");
        }
        assertEquals(36, arrayStack.size());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPop() {
        arrayStack.push("1A");   // 1A
        assertEquals(1, arrayStack.size());
        arrayStack.push("2B");   // 1A, 2B
        assertEquals(2, arrayStack.size());
        arrayStack.push("3C");   // 1A, 2B, 3C
        assertEquals(3, arrayStack.size());
        arrayStack.push("4D");   // 1A, 2B, 3C, 4D
        assertEquals(4, arrayStack.size());
        arrayStack.push("5E");   // 1A, 2B, 3C, 4D, 5E
        assertEquals(5, arrayStack.size());

        assertEquals("5E", arrayStack.pop());
        assertEquals(4, arrayStack.size());

        assertEquals("4D", arrayStack.pop());
        assertEquals(3, arrayStack.size());

        assertEquals("3C", arrayStack.pop());
        assertEquals(2, arrayStack.size());

        assertEquals("2B", arrayStack.pop());
        assertEquals(1, arrayStack.size());

        assertEquals("1A", arrayStack.pop());
        assertEquals(0, arrayStack.size());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPeek() {
        arrayStack.push("1A");   // 1A
        assertEquals(1, arrayStack.size());
        arrayStack.push("2B");   // 1A, 2B
        assertEquals(2, arrayStack.size());
        arrayStack.push("3C");   // 1A, 2B, 3C
        assertEquals(3, arrayStack.size());
        arrayStack.push("4D");   // 1A, 2B, 3C, 4D
        assertEquals(4, arrayStack.size());
        arrayStack.push("5E");   // 1A, 2B, 3C, 4D, 5E
        assertEquals(5, arrayStack.size());

        assertEquals("5E", arrayStack.peek()); //
        assertEquals("5E", arrayStack.pop());
        assertEquals(4, arrayStack.size());

        assertEquals("4D", arrayStack.peek()); //
        assertEquals("4D", arrayStack.pop());
        assertEquals(3, arrayStack.size());

        assertEquals("3C", arrayStack.peek()); //
        assertEquals("3C", arrayStack.pop());
        assertEquals(2, arrayStack.size());

        assertEquals("2B", arrayStack.peek()); //
        assertEquals("2B", arrayStack.pop());
        assertEquals(1, arrayStack.size());

        assertEquals("1A", arrayStack.peek()); //
        assertEquals("1A", arrayStack.pop());
        assertEquals(0, arrayStack.size());
    }
}