package org.flowutils;

import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.*;

public class LogUtilTest {

    @Test
    public void testCreateLogger() throws Exception {
        final Logger logger = LogUtils.getLogger();

        logger.info("Testing logging");

        assertEquals("org.flowutils.LogUtilTest", logger.getName());
    }
}
