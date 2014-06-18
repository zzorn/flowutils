package org.flowutils.collections.ringbuffer;

import java.util.Arrays;

/**
 * Fixed size buffer that can be added to at one end, and discards values at the other.
 * Can be accessed at any point.
 *
 * Holds integer values.
 *
 * Not thread safe.
 */
public final class RingBufferInt {

    // Array to store values in
    private final int[] buffer;

    // Points to first element
    private int first = 0;

    // Points to one after last element
    private int last = 0;

    // Number of elements
    private int size = 0;

    /**
     * @param capacity capacity of the ringbuffer.
     */
    public RingBufferInt(int capacity) {

        // Allocate space
        buffer = new int[capacity];

        // Initialize to empty
        clear();
    }

    /**
     * @return the i:th element from the start of the ringbuffer, 0 = first element.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     */
    public int get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("The position " + i + " is outside the bounds of the ringbuffer (it has size " + size + ")");

        return buffer[wrappedIndex(first + i)];
    }

    /**
     * @return the i:th element from the end of the ringbuffer, 0 = last element, 1 = next to last element, etc.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     */
    public int getFromEnd(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("The position " + i + " counting from the end is outside the bounds of the ringbuffer (it has size " + size + ")");

        return buffer[wrappedIndex(last - 1 - i)];
    }

    /**
     * @return first element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public int getFirst() {
        return get(0);
    }

    /**
     * @return last element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public int getLast() {
        return getFromEnd(0);
    }

    /**
     * Adds a new element to the start of the ringbuffer.
     * If the buffer is at full capacity, the last element will be overwritten.
     */
    public void addFirst(int element) {
        // Move first to point to one before the first
        first = prevIndex(first);

        // Write new first element
        buffer[wrappedIndex(first)] = element;

        // Bump last back if the buffer is full
        if (size >= buffer.length) last = prevIndex(last);
        else size++;
    }

    /**
     * Adds a new element to the end of the ringbuffer.
     * If the buffer is at full capacity, the first element will be overwritten.
     */
    public void addLast(int element) {
        // Write to one past the last
        buffer[wrappedIndex(last)] = element;

        // Move last to point to one past the last
        last = nextIndex(last);

        // Bump first forward if the buffer is full
        if (size >= buffer.length) first = nextIndex(first);
        else size++;
    }

    /**
     * Removes the first element from the buffer, and returns it.
     *
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public int removeFirst() {
        if (size <= 0) throw new IndexOutOfBoundsException("The ringbuffer is empty, can not remove first element.");

        // Get first element
        final int firstElement = buffer[first];

        // Clear removed element reference from the buffer
        buffer[first] = 0;

        // Move first to point to the next element
        first = nextIndex(first);

        // Decrease size
        size--;

        return firstElement;
    }

    /**
     * Removes the last element from the buffer, and returns it.
     *
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public int removeLast() {
        if (size <= 0) throw new IndexOutOfBoundsException("The ringbuffer is empty, can not remove last element.");

        // Get last element
        final int lastIndex = wrappedIndex(last - 1);
        final int lastElement = buffer[lastIndex];

        // Clear removed element reference from the buffer
        buffer[lastIndex] = 0;

        // Move last to point to the previous element
        last = prevIndex(last);

        // Decrease size
        size--;

        return lastElement;
    }


    /**
     * Empties the buffer, and removes references to all stored elements.
     * The capacity remains unchanged, the new size is zero.
     */
    public void clear() {
        // Initialize pointers
        first = 0;
        last = 0;

        // Initialize size
        size = 0;

        // Clear object references
        Arrays.fill(buffer, 0);
    }

    /**
     * @return number of elements in the ringbuffer.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the maximum capacity of the ringbuffer.
     */
    public int getCapacity() {
        return buffer.length;
    }

    /**
     * @return true if the ringbuffer has reached capacity, and any new elements added will drop out an element at the other end.
     */
    public boolean isFull() {
        return size >= buffer.length;
    }

    /**
     * @return true if the ringbuffer is empty.
     */
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * @return true if the ringbuffer is not empty.
     */
    public boolean hasElements() {
        return size > 0;
    }

    private int nextIndex(final int i) {
        int index = (i + 1) % buffer.length;
        if (index < 0) index += buffer.length;
        return index;
    }

    private int prevIndex(final int i) {
        int index = (i - 1) % buffer.length;
        if (index < 0) index += buffer.length;
        return index;
    }

    private int wrappedIndex(int i) {
        int index =  i % buffer.length;
        if (index < 0) index += buffer.length;
        return index;
    }

}
