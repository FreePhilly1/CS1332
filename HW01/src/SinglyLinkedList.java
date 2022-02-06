import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Phillip Kim
 * @version 1.0
 * @userid pkim90
 * @GTID 903376077
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (head == null) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
            size = size + 1;
        } else if (index == 0) {
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            SinglyLinkedListNode<T> current = head;
            int i = 0;
            while (i != index - 1) {
                current = current.getNext();
                i++;
            }
            SinglyLinkedListNode<T> added = new SinglyLinkedListNode<>(data, current.getNext());
            current.setNext(added);
            size = size + 1;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (this.isEmpty()) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
            size = size + 1;
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            newNode.setNext(head);
            head = newNode;
            size = size + 1;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (head == null) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            tail.setNext(newNode);
            tail = newNode;
        }
        size = size + 1;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (this.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else if (index == 0) {
            return this.removeFromFront();
        } else {
            SinglyLinkedListNode<T> current = head;
            int i = 0;
            while (i != index - 1) {
                current = current.getNext();
                i++;
            }
            SinglyLinkedListNode<T> removed = current.getNext();
            current.setNext(removed.getNext());
            if (removed.getNext() == null) {
                tail = current.getNext();
            }
            size = size - 1;
            return removed.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else if (head.getNext() == null) {
            SinglyLinkedListNode<T> removed = head;
            head = null;
            tail = null;
            size = size - 1;
            return removed.getData();
        } else {
            SinglyLinkedListNode<T> removed = head;
            head = head.getNext();
            size = size - 1;
            return removed.getData();
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        } else if (head.getNext() == null) {
            SinglyLinkedListNode<T> removed = head;
            head = null;
            tail = null;
            size = size - 1;
            return removed.getData();
        } else {
            SinglyLinkedListNode<T> current = head;
            while ((current.getNext()).getNext() != null) {
                current = current.getNext();
            }
            SinglyLinkedListNode<T> removed = current.getNext();
            current.setNext(null);
            tail = current;
            size = size - 1;
            return removed.getData();
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            int i = 0;
            SinglyLinkedListNode<T> current = head;
            while (i != index) {
                current = current.getNext();
                i++;
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        boolean found = false;
        SinglyLinkedListNode<T> current = head;
        SinglyLinkedListNode<T> temp = null;
        SinglyLinkedListNode<T> prev = null;
        if (this.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else if ((head.getData()).equals(data)) {
            temp = head;
            found = true;
        }
        while ((current.getNext()).getNext() != null) {
            if (((current.getNext()).getData()).equals(data)) {
                prev = current;
                temp = current.getNext();
                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new NoSuchElementException("Data not found");
        }
        if (prev == null) {
            temp = head;
            this.removeFromFront();
        } else {
            prev.setNext(temp.getNext());
            size = size - 1;
        }
        return temp.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] list = (T[]) new Object[size];
        if (!this.isEmpty()) {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < size; i++) {
                list[i] = current.getData();
                current = current.getNext();
            }
        }
        return list;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
