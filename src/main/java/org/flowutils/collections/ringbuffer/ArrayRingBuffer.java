package org.flowutils.collections.ringbuffer;

import java.util.Arrays;

/**
 * RingBuffer implementation that uses a backing array.
 *
 * Not thread safe.
 */
public final class ArrayRingBuffer<T> extends RingBufferBase<T> {

    // Array to store values in
    private final T[] buffer;

    /**
     * @param capacity capacity of the ringbuffer.
     */
    public ArrayRingBuffer(int capacity) {

        // Allocate space
        buffer = (T[]) new Object[capacity];

        // Initialize to empty
        clear();
    }

    /**
     * @return the i:th element from the start of the ringbuffer, 0 = first element.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     */
    public T get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("The position " + i + " is outside the bounds of the ringbuffer (it has size " + size + ")");

        return buffer[wrappedIndex(first + i)];
    }

    /**
     * Sets the value of the i:th element from the start of the ringbuffer (0 = first element), discarding the previous value at that location.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     * @return value previously at the location.
     */
    public T set(int i, T value) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("The position " + i + " is outside the bounds of the ringbuffer (it has size " + size + ")");

        final int index = wrappedIndex(first + i);
        final T prevValue = buffer[index];
        buffer[index] = value;
        return prevValue;
    }

    /**
     * @return the i:th element from the end of the ringbuffer, 0 = last element, 1 = next to last element, etc.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     */
    public T getFromEnd(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("The position " + i + " counting from the end is outside the bounds of the ringbuffer (it has size " + size + ")");

        return buffer[wrappedIndex(last - 1 - i)];
    }

    /**
     * @return first element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public T getFirst() {
        return get(0);
    }

    /**
     * @return last element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public T getLast() {
        return getFromEnd(0);
    }

    /**
     * Sets the value of the first element in the ringbuffer, returning the old value at that location.
     */
    public T setFirst(T value) {
        return set(0, value);
    }

    /**
     * Sets the value of the last element in the ringbuffer, returning the old value at that location.
     */
    public T setLast(T value) {
        return set(getSize() - 1, value);
    }

    /**
     * Adds a new element to the start of the ringbuffer.
     * If the buffer is at full capacity, the last element will be overwritten.
     */
    public void addFirst(T element) {
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
    public void addLast(T element) {
        // Write to one past the last
        buffer[wrappedIndex(last)] = element;

        // Move last to point to one past the last
        last = nextIndex(last);

        // Bump first forward if the buffer is full
        if (size >= buffer.length) first = nextIndex(first);
        else size++;
    }

    @Override public boolean add(T element) {
        addLast(element);
        return true;
    }

    /**
     * Removes the first element from the buffer, and returns it.
     *
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    public T removeFirst() {
        if (size <= 0) throw new IndexOutOfBoundsException("The ringbuffer is empty, can not remove first element.");

        // Get first element
        final T firstElement = buffer[first];

        // Clear removed element reference from the buffer
        buffer[first] = null;

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
    public T removeLast() {
        if (size <= 0) throw new IndexOutOfBoundsException("The ringbuffer is empty, can not remove last element.");

        // Get last element
        final int lastIndex = wrappedIndex(last - 1);
        final T lastElement = buffer[lastIndex];

        // Clear removed element reference from the buffer
        buffer[lastIndex] = null;

        // Move last to point to the previous element
        last = prevIndex(last);

        // Decrease size
        size--;

        return lastElement;
    }

    /**
     * @return the maximum capacity of the ringbuffer.
     */
    public int getCapacity() {
        return buffer.length;
    }

    @Override protected void clearBufferContents() {
        // Clear object references
        Arrays.fill(buffer, null);
    }
}
