package org.flowutils.collections.ringbuffer;

import java.util.List;

/**
 * Fixed size buffer that can be added to at one end, and discards values at the other.
 * Can be accessed at any point.
 *
 * Note that the add(index, element) and remove(index) methods of the list are not supported at the moment.
 *
 * Not thread safe.
 */
public interface RingBuffer<T> extends List<T> {

    /**
     * @return the maximum capacity of the ringbuffer.
     */
    int getCapacity();

    /**
     * @return true if the ringbuffer has reached capacity, and any new elements added will drop out an element at the other end.
     */
    boolean isFull();

    /**
     * @return true if the ringbuffer is not empty.
     */
    boolean hasElements();

    /**
     * @return the i:th element from the end of the ringbuffer, 0 = last element, 1 = next to last element, etc.
     * @throws IndexOutOfBoundsException if i is larger than the current size of the buffer.
     */
    T getFromEnd(int i);

    /**
     * @return first element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    T getFirst();

    /**
     * @return last element of the ringbuffer.
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    T getLast();

    /**
     * Sets the value of the first element in the ringbuffer, returning the old value at that location.
     */
    T setFirst(T value);

    /**
     * Sets the value of the last element in the ringbuffer, returning the old value at that location.
     */
    T setLast(T value);

    /**
     * Adds a new element to the start of the ringbuffer.
     * If the buffer is at full capacity, the last element will be overwritten.
     */
    void addFirst(T element);

    /**
     * Adds a new element to the end of the ringbuffer.
     * If the buffer is at full capacity, the first element will be overwritten.
     */
    void addLast(T element);

    /**
     * Removes the first element from the buffer, and returns it.
     *
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    T removeFirst();

    /**
     * Removes the last element from the buffer, and returns it.
     *
     * @throws IndexOutOfBoundsException if there are no elements in the buffer.
     */
    T removeLast();
}
