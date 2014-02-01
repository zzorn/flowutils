package org.flowutils;


import org.junit.Test;
import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class CollectionUtilsTest {

    @Test
    public void testRandomElement() throws Exception {
        final List<String> testElements = Arrays.asList("foo", "bar", "zoo");

        for (int i = 0; i < 1000; i++) {
            final String element = CollectionUtils.getRandomElement(testElements);
            assertTrue("Returned random element should be one of the test elements",
                       testElements.contains(element));
        }

        final Random customRandom = new Random(42);
        for (int i = 0; i < 1000; i++) {
            final String element = CollectionUtils.getRandomElement(testElements, customRandom);
            assertTrue("Returned random element should be one of the test elements",
                       testElements.contains(element));
        }
    }

    @Test
    public void testRandomElementOfEmptyListReturnsNull() throws Exception {
        final List<String> emptyTestList = Arrays.asList();

        assertEquals(null, CollectionUtils.getRandomElement(emptyTestList));
        assertEquals(null, CollectionUtils.getRandomElement(emptyTestList, new Random()));
    }
}
