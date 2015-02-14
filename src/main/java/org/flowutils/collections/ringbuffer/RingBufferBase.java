package org.flowutils.collections.ringbuffer;

import java.util.Iterator;

/**
 * Contains common functionality for RingBuffers.
 */
public abstract class RingBufferBase<T> implements Iterable<T> {

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
    public abstract T getElement(int index);

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
    public final int getSize() {
        return size;
    }

    /**
     * @return the maximum capacity of the ringbuffer.
     */
    public abstract int getCapacity();

    /**
     * @return true if the ringbuffer has reached capacity, and any new elements added will drop out an element at the other end.
     */
    public final boolean isFull() {
        return size >= getCapacity();
    }

    /**
     * @return true if the ringbuffer is empty.
     */
    public final boolean isEmpty() {
        return size <= 0;
    }

    /**
     * @return true if the ringbuffer is not empty.
     */
    public final boolean hasElements() {
        return size > 0;
    }

    @Override public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override public boolean hasNext() {
                return i < getSize();
            }

            @Override public T next() {
                return getElement(i++);
            }

            @Override public void remove() {
                throw new UnsupportedOperationException("Remove is not supported for ringbuffers");
            }
        };
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
