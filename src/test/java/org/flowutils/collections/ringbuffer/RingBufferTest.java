package org.flowutils.collections.ringbuffer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for RingBuffer
 */
public class RingBufferTest {
    @Test
    public void testModulus() throws Exception {
        assertEquals(-3, -13 % 10);

    }

    @Test
    public void testBuffer() throws Exception {
        ArrayRingBuffer<String> ringBuffer = new ArrayRingBuffer<String>(4);
        assertGetThrowsException(ringBuffer, 0);
        assertGetThrowsException(ringBuffer, 1);
        assertGetThrowsException(ringBuffer, -1);
        assertGetFromEndThrowsException(ringBuffer, 0);
        assertGetFromEndThrowsException(ringBuffer, 1);
        assertGetFromEndThrowsException(ringBuffer, -1);
        assertGetFirstThrowsException(ringBuffer);
        assertGetLastThrowsException(ringBuffer);
        assertEquals(0, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());
        assertEquals(true, ringBuffer.isEmpty());
        assertEquals(false, ringBuffer.isFull());

        ringBuffer.addLast("1");

        assertEquals("1", ringBuffer.get(0));
        assertGetThrowsException(ringBuffer, 1);
        assertGetThrowsException(ringBuffer, 4);
        assertGetFromEndThrowsException(ringBuffer, 1);
        assertEquals("1", ringBuffer.getFromEnd(0));
        assertEquals("1", ringBuffer.getFirst());
        assertEquals("1", ringBuffer.getLast());
        assertEquals(1, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());
        assertEquals(false, ringBuffer.isEmpty());
        assertEquals(false, ringBuffer.isFull());

        ringBuffer.addLast("2");

        assertEquals("1", ringBuffer.get(0));
        assertEquals("2", ringBuffer.get(1));
        assertEquals("1", ringBuffer.getFromEnd(1));
        assertEquals("2", ringBuffer.getFromEnd(0));
        assertEquals("1", ringBuffer.getFirst());
        assertEquals("2", ringBuffer.getLast());
        assertEquals(2, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        ringBuffer.addFirst("0");

        assertEquals("0", ringBuffer.get(0));
        assertEquals("1", ringBuffer.get(1));
        assertEquals("2", ringBuffer.get(2));
        assertEquals("2", ringBuffer.getFromEnd(0));
        assertEquals("1", ringBuffer.getFromEnd(1));
        assertEquals("0", ringBuffer.getFromEnd(2));
        assertEquals("0", ringBuffer.getFirst());
        assertEquals("2", ringBuffer.getLast());
        assertEquals(3, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        ringBuffer.addFirst("-1");

        assertEquals("-1", ringBuffer.get(0));
        assertEquals("0", ringBuffer.get(1));
        assertEquals("1", ringBuffer.get(2));
        assertEquals("2", ringBuffer.get(3));
        assertEquals("2", ringBuffer.getFromEnd(0));
        assertEquals("1", ringBuffer.getFromEnd(1));
        assertEquals("0", ringBuffer.getFromEnd(2));
        assertEquals("-1", ringBuffer.getFromEnd(3));
        assertEquals("-1", ringBuffer.getFirst());
        assertEquals("2", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        ringBuffer.addLast("3");

        assertEquals("0", ringBuffer.get(0));
        assertEquals("1", ringBuffer.get(1));
        assertEquals("2", ringBuffer.get(2));
        assertEquals("3", ringBuffer.get(3));
        assertEquals("3", ringBuffer.getFromEnd(0));
        assertEquals("2", ringBuffer.getFromEnd(1));
        assertEquals("1", ringBuffer.getFromEnd(2));
        assertEquals("0", ringBuffer.getFromEnd(3));
        assertEquals("0", ringBuffer.getFirst());
        assertEquals("3", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        ringBuffer.addFirst("-2");

        assertEquals("-2", ringBuffer.get(0));
        assertEquals("0", ringBuffer.get(1));
        assertEquals("1", ringBuffer.get(2));
        assertEquals("2", ringBuffer.get(3));
        assertEquals("2", ringBuffer.getFromEnd(0));
        assertEquals("1", ringBuffer.getFromEnd(1));
        assertEquals("0", ringBuffer.getFromEnd(2));
        assertEquals("-2", ringBuffer.getFromEnd(3));
        assertEquals("-2", ringBuffer.getFirst());
        assertEquals("2", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        for (int i = 11; i <= 100; i++) {
            ringBuffer.addLast("#" + i);
        }

        assertGetThrowsException(ringBuffer, -1);
        assertGetThrowsException(ringBuffer, 4);
        assertGetFromEndThrowsException(ringBuffer, -1);
        assertGetFromEndThrowsException(ringBuffer, -4);
        assertGetFromEndThrowsException(ringBuffer, -3);
        assertGetFromEndThrowsException(ringBuffer, 4);
        assertEquals("#97", ringBuffer.get(0));
        assertEquals("#98", ringBuffer.get(1));
        assertEquals("#99", ringBuffer.get(2));
        assertEquals("#100", ringBuffer.get(3));
        assertEquals("#100", ringBuffer.getFromEnd(0));
        assertEquals("#99", ringBuffer.getFromEnd(1));
        assertEquals("#98", ringBuffer.getFromEnd(2));
        assertEquals("#97", ringBuffer.getFromEnd(3));
        assertEquals("#97", ringBuffer.getFirst());
        assertEquals("#100", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());
        assertEquals(false, ringBuffer.isEmpty());
        assertEquals(true, ringBuffer.isFull());

        for (int i = 13; i <= 100; i++) {
            ringBuffer.addFirst("$" + i);
        }

        assertEquals("$100", ringBuffer.get(0));
        assertEquals("$99", ringBuffer.get(1));
        assertEquals("$98", ringBuffer.get(2));
        assertEquals("$97", ringBuffer.get(3));
        assertEquals("$97", ringBuffer.getFromEnd(0));
        assertEquals("$98", ringBuffer.getFromEnd(1));
        assertEquals("$99", ringBuffer.getFromEnd(2));
        assertEquals("$100", ringBuffer.getFromEnd(3));
        assertEquals("$100", ringBuffer.getFirst());
        assertEquals("$97", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());

        // Test setters

        ringBuffer.set(2, "EditedValue");

        assertEquals("$100", ringBuffer.get(0));
        assertEquals("$99", ringBuffer.get(1));
        assertEquals("EditedValue", ringBuffer.get(2));
        assertEquals("$97", ringBuffer.get(3));

        ringBuffer.setFirst("EditedValue2");

        assertEquals("EditedValue2", ringBuffer.get(0));
        assertEquals("$99", ringBuffer.get(1));
        assertEquals("EditedValue", ringBuffer.get(2));
        assertEquals("$97", ringBuffer.get(3));

        ringBuffer.setLast("EditedValue3");

        assertEquals("EditedValue2", ringBuffer.get(0));
        assertEquals("$99", ringBuffer.get(1));
        assertEquals("EditedValue", ringBuffer.get(2));
        assertEquals("EditedValue3", ringBuffer.get(3));

        // Test clear

        ringBuffer.clear();

        assertGetThrowsException(ringBuffer, 0);
        assertGetThrowsException(ringBuffer, 1);
        assertGetThrowsException(ringBuffer, -1);
        assertGetFromEndThrowsException(ringBuffer, 0);
        assertGetFromEndThrowsException(ringBuffer, 1);
        assertGetFromEndThrowsException(ringBuffer, -1);
        assertGetFirstThrowsException(ringBuffer);
        assertGetLastThrowsException(ringBuffer);
        assertEquals(0, ringBuffer.size());
        assertEquals(4, ringBuffer.getCapacity());
        assertEquals(true, ringBuffer.isEmpty());
        assertEquals(false, ringBuffer.isFull());
    }

    @Test
    public void testRemove() throws Exception {
        ArrayRingBuffer<String> ringBuffer = new ArrayRingBuffer<String>(5);
        assertRemoveFirstThrowsException(ringBuffer);
        assertRemoveLastThrowsException(ringBuffer);

        ringBuffer.addLast("1");
        ringBuffer.addLast("2");
        ringBuffer.addLast("3");
        ringBuffer.addLast("4");
        assertEquals("1", ringBuffer.getFirst());
        assertEquals("2", ringBuffer.get(1));
        assertEquals("4", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        assertEquals("1", ringBuffer.removeFirst());
        assertEquals("2", ringBuffer.getFirst());
        assertEquals("3", ringBuffer.get(1));
        assertEquals("4", ringBuffer.getLast());
        assertEquals(3, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        assertEquals("4", ringBuffer.removeLast());
        assertEquals("2", ringBuffer.getFirst());
        assertEquals("3", ringBuffer.get(1));
        assertEquals("3", ringBuffer.getLast());
        assertEquals(2, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        ringBuffer.addLast("5");
        ringBuffer.addLast("6");
        ringBuffer.addLast("7");
        ringBuffer.addLast("8");
        assertEquals("3", ringBuffer.getFirst());
        assertEquals("5", ringBuffer.get(1));
        assertEquals("8", ringBuffer.getLast());
        assertEquals(5, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        assertEquals("8", ringBuffer.removeLast());
        assertEquals("3", ringBuffer.getFirst());
        assertEquals("5", ringBuffer.get(1));
        assertEquals("7", ringBuffer.getLast());
        assertEquals(4, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        assertEquals("3", ringBuffer.removeFirst());
        assertEquals("5", ringBuffer.removeFirst());
        assertEquals("6", ringBuffer.removeFirst());

        assertEquals("7", ringBuffer.getFirst());
        assertEquals("7", ringBuffer.getLast());
        assertEquals(1, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());

        assertEquals("7", ringBuffer.removeLast());
        assertEquals(0, ringBuffer.size());
        assertEquals(5, ringBuffer.getCapacity());
        assertRemoveFirstThrowsException(ringBuffer);
        assertRemoveLastThrowsException(ringBuffer);
    }

    @Test
    public void testIterator() throws Exception {
        ArrayRingBuffer<String> ringBuffer = new ArrayRingBuffer<String>(5);
        ringBuffer.addLast("foo");
        ringBuffer.addLast("bar");
        ringBuffer.addLast("zap");

        String sum = "";
        for (String s : ringBuffer) {
            sum += s;
        }
        assertEquals("foobarzap", sum);
    }

    private void assertGetThrowsException(ArrayRingBuffer<String> ringBuffer, final int index) {
        try {
            ringBuffer.get(index);

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private void assertGetFromEndThrowsException(ArrayRingBuffer<String> ringBuffer, final int index) {
        try {
            ringBuffer.getFromEnd(index);

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private void assertGetFirstThrowsException(ArrayRingBuffer<String> ringBuffer) {
        try {
            ringBuffer.getFirst();

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private void assertGetLastThrowsException(ArrayRingBuffer<String> ringBuffer) {
        try {
            ringBuffer.getLast();

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private void assertRemoveFirstThrowsException(ArrayRingBuffer<String> ringBuffer) {
        try {
            ringBuffer.removeFirst();

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }
    private void assertRemoveLastThrowsException(ArrayRingBuffer<String> ringBuffer) {
        try {
            ringBuffer.removeLast();

            fail("Should have thrown an IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
