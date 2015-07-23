package org.flowutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.flowutils.Check.lessOrEqual;
import static org.flowutils.Check.notNull;

/**
 * Utilities for manipulating byte arrays.
 */
public final class ByteArrayUtils {

    /**
     * @return the specified number of bytes from start of an array, in a new array.
     *         The original array is untouched.
     */
    public static byte[] getFirst(byte[] sourceData, final int bytesToGetFromStart) {
        notNull(sourceData, "sourceData");
        lessOrEqual(bytesToGetFromStart, "bytesToGetFromStart", sourceData.length, "sourceData length");

        return Arrays.copyOf(sourceData, bytesToGetFromStart);
    }

    /**
     * @return the specified number of bytes from the end of an array, in a new array.
     *         The original array is untouched.
     */
    public static byte[] getLast(byte[] sourceData, final int bytesToGetFromEnd) {
        notNull(sourceData, "sourceData");
        lessOrEqual(bytesToGetFromEnd, "bytesToGetFromEnd", sourceData.length, "sourceData length");

        byte[] lastBytes = new byte[bytesToGetFromEnd];

        System.arraycopy(sourceData, sourceData.length - bytesToGetFromEnd, lastBytes, 0, bytesToGetFromEnd);

        return lastBytes;
    }

    /**
     * @return new byte array with the same contents as sourceData, except for the bytesToDropFromStart first bytes removed.
     *         The original array is untouched.
     */
    public static byte[] dropFirst(byte[] sourceData, final int bytesToDropFromStart) {
        notNull(sourceData, "sourceData");
        lessOrEqual(bytesToDropFromStart, "bytesToDropFromStart", sourceData.length, "sourceData length");

        return getLast(sourceData, sourceData.length - bytesToDropFromStart);
    }

    /**
     * @return new byte array with the same contents as sourceData, except for the bytesToDropFromEnd last bytes removed.
     *         The original array is untouched.
     */
    public static byte[] dropLast(byte[] sourceData, final int bytesToDropFromEnd) {
        notNull(sourceData, "sourceData");
        lessOrEqual(bytesToDropFromEnd, "bytesToDropFromEnd", sourceData.length, "sourceData length");

        return getFirst(sourceData, sourceData.length - bytesToDropFromEnd);
    }

    /**
     * @return new byte array with the content of start followed by end.
     */
    public static byte[] concatenate(final byte[] start, final byte[] end) {
        notNull(start, "start");
        notNull(end, "end");

        byte[] concatenated = new byte[end.length + start.length];

        System.arraycopy(start, 0, concatenated, 0, start.length);
        System.arraycopy(end, 0, concatenated, start.length, end.length);

        return concatenated;
    }

    public static byte[] numberToByteArray(int sizeBytes, boolean bigEndian, long number) {
        final byte[] bytes = new byte[sizeBytes];

        for (int i = 0; i < sizeBytes; i++) {
            int byteIndex = bigEndian ? sizeBytes - i - 1 : i;
            bytes[byteIndex] = (byte) (0xFF & (number >> (i*8)));
        }

        return bytes;
    }

    public static long byteArrayToNumber(boolean bigEndian, byte[] bytes) {
        notNull(bytes, "bytes");
        Check.inRange(bytes.length, "bytes.length", 1, 8);

        long number = 0;

        for (int i = 0; i < bytes.length; i++) {
            int byteIndex = bigEndian ? bytes.length - i - 1 : i;
            number |= bytes[byteIndex] << (i*8);
        }

        return number;
    }

    /**
     * Composes a byte array of blocks with size prefixes.
     * Max block size is 2^16, and big endian is used to represent the block size.
     *
     * @param blocks blocks to compose.
     * @return composed array with the blocks, where each block is prefixed by the blocksize in bytes, using two bytes in big endian order to store the size.
     */
    public static byte[] composeWithSizePrefixes(byte[] ... blocks) {
        return composeWithSizePrefixes(2, true, blocks);
    }

    /**
     * Composes a byte array of blocks with size prefixes.
     *
     * @param sizeBytes bytes to use for representing the size of each block.
     * @param bigEndian byte order to use for sizeBytes.  True if big endian, false if small endian.
     * @param blocks blocks to compose.
     * @return composed array with the blocks, where each block is prefixed by the blocksize in bytes, using sizeBytes to store the size.
     */
    public static byte[] composeWithSizePrefixes(int sizeBytes, boolean bigEndian, byte[] ... blocks) {
        Check.inRange(sizeBytes, "sizeBytes", 1, 4);
        long maxBlockLength = (1L << (8 * sizeBytes)) - 1;

        // Determine total size of composite array, and check block sizes.
        int totalSize = 0;
        for (byte[] block : blocks) {
            if (block.length > maxBlockLength) throw new IllegalArgumentException("With " + sizeBytes + " bytes only  blocks of " + maxBlockLength + " bytes may be stored, but one block was " + block.length + " bytes.");

            totalSize += sizeBytes;
            totalSize += block.length;
        }

        // Create output array
        byte result[] = new byte[totalSize];

        // Compose
        int index = 0;
        for (byte[] block : blocks) {
            // Copy size
            final byte[] blockSize = numberToByteArray(sizeBytes, bigEndian, block.length);
            for (byte b : blockSize) {
                result[index++] = b;
            }

            // Copy block
            for (byte b : block) {
                result[index++] = b;
            }
        }

        return result;
    }

    /**
     * Decomposes a byte array of blocks with size prefixes.
     * Max block size is 2^16, and big endian is used to represent the block size.
     *
     * @param data data to decompose.
     * @return list with the decomposed blocks, in the order they were stored.
     * @throws InvalidBlockSize if the recorded block sizes were invalid (negative, or extending beyond the size of the data).
     */
    public static List<byte[]> decomposeWithSizePrefixes(byte[] data) throws InvalidBlockSize {
        return decomposeWithSizePrefixes(2, true, data);
    }

    /**
     * Decomposes a byte array of blocks with size prefixes.
     *
     * @param sizeBytes bytes used for representing the size of each block.
     * @param bigEndian byte order used for sizeBytes.  True if big endian, false if small endian.
     * @param data data to decompose.
     * @return list with the decomposed blocks, in the order they were stored.
     * @throws InvalidBlockSize if the recorded block sizes were invalid (negative, or extending beyond the size of the data).
     */
    public static List<byte[]> decomposeWithSizePrefixes(int sizeBytes, boolean bigEndian, byte[] data) throws InvalidBlockSize {
        Check.inRange(sizeBytes, "sizeBytes", 1, 4);

        final byte[] tempBlockSize = new byte[sizeBytes];

        // Create output list
        final ArrayList<byte[]> blocks = new ArrayList<>();

        // Decompose
        int index = 0;
        while (index < data.length) {
            // Read block size
            for (int i = 0; i < sizeBytes; i++) {
                tempBlockSize[i] = data[index++];
            }
            final long blockSize = byteArrayToNumber(bigEndian, tempBlockSize);

            // Sanity check size
            if (blockSize < 0) throw new InvalidBlockSize("Block size of block "+blocks.size()+" was negative: " + blockSize);
            if (index + blockSize > data.length) throw new InvalidBlockSize("Block size of block "+blocks.size()+" extended beyond the limit of the input array: " + blockSize);
            if (blockSize > Integer.MAX_VALUE) throw new IllegalStateException("block size should not exceed integer max value, but it was " + blockSize);

            // Read the block
            byte[] block = new byte[(int) blockSize];
            for (int i = 0; i < blockSize; i++) {
                block[i] = data[index++];
            }
            blocks.add(block);
        }

        return blocks;
    }



    private ByteArrayUtils() {
    }

    /**
     * Indicates that the block size of a decoded block was illegal (negative, or extending beyond the input array).
     */
    public static final class InvalidBlockSize extends Exception {
        public InvalidBlockSize() {
        }

        public InvalidBlockSize(String message) {
            super(message);
        }

        public InvalidBlockSize(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidBlockSize(Throwable cause) {
            super(cause);
        }

        public InvalidBlockSize(String message,
                                Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
