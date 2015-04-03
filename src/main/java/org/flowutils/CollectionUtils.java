package org.flowutils;

import java.util.List;
import java.util.Random;

/**
 * Collection related utilities.
 */
public class CollectionUtils {

    private final static Random RANDOM = new Random();


    /**
     * @return a random element from a list, or null if the list is empty.
     */
    // TODO: Move to RandomSequence
    public static <T> T getRandomElement(List<T> list) {
        return getRandomElement(list, RANDOM);
    }

    /**
     * @param random random instance to use.
     * @return a random element from a list, or null if the list is empty.
     */
    // TODO: Move to RandomSequence
    public static <T> T getRandomElement(List<T> list, Random random) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        else {
            final int randomIndex = random.nextInt(list.size());
            return list.get(randomIndex);
        }
    }

    private CollectionUtils() {
    }
}