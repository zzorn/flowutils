package org.flowutils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteArrayUtilsTest {

    @Test
    public void testConcatenate() throws Exception {
        assertConcatenateWorks(new byte[]{1, 2, 3}, new byte[]{4, 5, 6}, new byte[] {1, 2, 3, 4, 5, 6});
        assertConcatenateWorks(new byte[]{1, 2, 3}, new byte[]{4}, new byte[] {1, 2, 3, 4});
        assertConcatenateWorks(new byte[]{1}, new byte[]{4, 5, 6}, new byte[] {1, 4, 5, 6});
        assertConcatenateWorks(new byte[]{}, new byte[]{4, 5, 6}, new byte[] {4, 5, 6});
        assertConcatenateWorks(new byte[]{1, 2, 3}, new byte[]{}, new byte[] {1, 2, 3});
    }

    private void assertConcatenateWorks(final byte[] a, final byte[] b, final byte[] expected) {
        assertArrayEquals(expected, ByteArrayUtils.concatenate(a, b));
    }

    @Test
    public void testGetFirst() throws Exception {
        assertGetFirstWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});
        assertGetFirstWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3});
        assertGetFirstWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{1});
        assertGetFirstWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{});

    }

    private void assertGetFirstWorks(int numToGet, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, ByteArrayUtils.getFirst(a, numToGet));
    }

    @Test
    public void testGetLast() throws Exception {
        assertGetLastWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});
        assertGetLastWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{3, 4, 5});
        assertGetLastWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{5});
        assertGetLastWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{});

    }

    private void assertGetLastWorks(int numToGet, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, ByteArrayUtils.getLast(a, numToGet));
    }

    @Test
    public void testDropFirst() throws Exception {
        assertDropFirstWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{});
        assertDropFirstWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{4, 5});
        assertDropFirstWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{2, 3, 4, 5});
        assertDropFirstWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});

    }

    private void assertDropFirstWorks(int numToDrop, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, ByteArrayUtils.dropFirst(a, numToDrop));
    }

    @Test
    public void testDropLast() throws Exception {
        assertDropLastWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{});
        assertDropLastWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2});
        assertDropLastWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4});
        assertDropLastWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});

    }

    private void assertDropLastWorks(int numToDrop, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, ByteArrayUtils.dropLast(a, numToDrop));
    }
}
