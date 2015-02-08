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
public final class RingBufferInt extends RingBufferBase {

    // Array to store values in
    private final int[] buffer;

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
        if (size >= getCapacity()) last = prevIndex(last);
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
        if (size >= getCapacity()) first = nextIndex(first);
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


    @Override protected void clearBufferContents() {
        Arrays.fill(buffer, 0);
    }

    @Override public int getCapacity() {
        return buffer.length;
    }
}
