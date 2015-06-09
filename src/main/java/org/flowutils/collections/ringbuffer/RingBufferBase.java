package org.flowutils.collections.ringbuffer;

import java.util.AbstractList;

/**
 * Contains common functionality for RingBuffers.
 */
public abstract class RingBufferBase<T> extends AbstractList<T> implements RingBuffer<T> {

    // Points to first element
    protected int first = 0;

    // Points to one after last element
    protected int last = 0;

    // Number of elements
    protected int size = 0;

    /**
     * @param index index of the element to get, 0 == first element.
     * @return the element with the specific index from the buffer.
     * @throws java.lang.ArrayIndexOutOfBoundsException if the index if out of the buffer bounds.
     */
    @Override public abstract T get(int index);

    /**
     * Empties the buffer, and removes references to all stored elements.
     * The capacity remains unchanged, the new size is zero.
     */
    public final void clear() {
        // Initialize pointers
        first = 0;
        last = 0;

        // Initialize size
        size = 0;

        clearBufferContents();
    }

    /**
     * @return number of elements in the ringbuffer.
     */
    public final int size() {
        return size;
    }

    /**
     * @return number of elements in the ringbuffer.
     * @deprecated replaced with size()
     */
    public final int getSize() {
        return size();
    }

    @Override public final boolean isFull() {
        return size >= getCapacity();
    }

    /**
     * @return true if the ringbuffer is empty.
     */
    public final boolean isEmpty() {
        return size <= 0;
    }

    @Override public final boolean hasElements() {
        return size > 0;
    }

    /**
     * Fill the buffer with nulls or zeroes.
     */
    protected abstract void clearBufferContents();

    protected final int nextIndex(final int i) {
        final int capacity = getCapacity();

        int index = (i + 1) % capacity;
        if (index < 0) index += capacity;
        return index;
    }

    protected final int prevIndex(final int i) {
        final int capacity = getCapacity();

        int index = (i - 1) % capacity;
        if (index < 0) index += capacity;
        return index;
    }

    protected final int wrappedIndex(int i) {
        final int capacity = getCapacity();

        int index =  i % capacity;
        if (index < 0) index += capacity;
        return index;
    }
}
