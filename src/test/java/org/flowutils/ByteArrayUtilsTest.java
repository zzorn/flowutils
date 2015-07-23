package org.flowutils;

import static org.flowutils.ByteArrayUtils.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

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
        assertArrayEquals(expected, concatenate(a, b));
    }

    @Test
    public void testGetFirst() throws Exception {
        assertGetFirstWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});
        assertGetFirstWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3});
        assertGetFirstWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{1});
        assertGetFirstWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{});

    }

    private void assertGetFirstWorks(int numToGet, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, getFirst(a, numToGet));
    }

    @Test
    public void testGetLast() throws Exception {
        assertGetLastWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});
        assertGetLastWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{3, 4, 5});
        assertGetLastWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{5});
        assertGetLastWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{});

    }

    private void assertGetLastWorks(int numToGet, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, getLast(a, numToGet));
    }

    @Test
    public void testDropFirst() throws Exception {
        assertDropFirstWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{});
        assertDropFirstWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{4, 5});
        assertDropFirstWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{2, 3, 4, 5});
        assertDropFirstWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});

    }

    private void assertDropFirstWorks(int numToDrop, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, dropFirst(a, numToDrop));
    }

    @Test
    public void testDropLast() throws Exception {
        assertDropLastWorks(5, new byte[]{1, 2, 3, 4, 5}, new byte[]{});
        assertDropLastWorks(3, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2});
        assertDropLastWorks(1, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4});
        assertDropLastWorks(0, new byte[]{1, 2, 3, 4, 5}, new byte[]{1, 2, 3, 4, 5});

    }

    private void assertDropLastWorks(int numToDrop, final byte[] a, final byte[] expected) {
        assertArrayEquals(expected, dropLast(a, numToDrop));
    }

    @Test
    public void testNumberToBytesAndBack() throws Exception {
        assertNumberToBytesAndBack(new byte[]{0}, 1, true, 0);
        assertNumberToBytesAndBack(new byte[]{42}, 1, true, 42);
        assertNumberToBytesAndBack(new byte[]{42}, 1, false, 42);
        assertNumberToBytesAndBack(new byte[]{0, 42}, 2, true, 42);
        assertNumberToBytesAndBack(new byte[]{42, 0}, 2, false, 42);
        assertNumberToBytesAndBack(new byte[]{(byte) 0xFF, -3}, 2, true, -3);
        assertNumberToBytesAndBack(new byte[]{-3, (byte) 0xFF}, 2, false, -3);
        assertNumberToBytesAndBack(new byte[]{0, 0, 4, 0}, 4, true, 1024);
        assertNumberToBytesAndBack(new byte[]{0, 4, 0, 0}, 4, false, 1024);
        assertNumberToBytesAndBack(new byte[]{0, 0, 4, 100}, 4, true, 1124);
        assertNumberToBytesAndBack(new byte[]{100, 4, 0, 0}, 4, false, 1124);
    }

    private void assertNumberToBytesAndBack(final byte[] byteArrayRepresentation,
                                            final int sizeBytes,
                                            final boolean bigEndian, final int number) {
        assertArrayEquals(byteArrayRepresentation, numberToByteArray(sizeBytes, bigEndian, number));
        assertEquals(number, byteArrayToNumber(bigEndian, byteArrayRepresentation));
    }

    @Test
    public void testComposeDecomposeBlocks() throws Exception {
        final byte[] a = {1, 2, 3};
        final byte[] b = {11, 12, 13};
        final byte[] c = {111};
        final byte[] d = {};
        final byte[] e = {21, 22, 23, 24, 25};
        final byte[] composed = composeWithSizePrefixes(a, b, c, d, e);
        assertArrayEquals(new byte[]{0, 3, 1, 2, 3, 0, 3, 11, 12, 13, 0, 1, 111, 0, 0, 0, 5, 21, 22, 23, 24, 25}, composed);

        final List<byte[]> decomposed = decomposeWithSizePrefixes(composed);
        assertEquals(5, decomposed.size());
        assertArrayEquals(decomposed.get(0), a);
        assertArrayEquals(decomposed.get(1), b);
        assertArrayEquals(decomposed.get(2), c);
        assertArrayEquals(decomposed.get(3), d);
        assertArrayEquals(decomposed.get(4), e);
    }
}
