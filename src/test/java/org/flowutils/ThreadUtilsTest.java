package org.flowutils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadUtilsTest {
    @Test
    public void testGetCallingMethodName() throws Exception {
        assertEquals("testGetCallingMethodName", ThreadUtils.getCallingMethodName(0));

        foo();
    }

    @Test
    public void testGetCallingMethodClass() throws Exception {
        assertNotEquals("org.flowutils.ThreadUtilsTest", ThreadUtils.getCallingClassName());

        assertEquals("org.flowutils.ThreadUtilsTest", ThreadUtils.getCallingClassName(0));
    }

    private void foo() {
        assertEquals("testGetCallingMethodName", ThreadUtils.getCallingMethodName());

        assertEquals("foo", ThreadUtils.getCallingMethodName(0));
        assertEquals("testGetCallingMethodName", ThreadUtils.getCallingMethodName(1));

        assertEquals("org.flowutils.ThreadUtilsTest", ThreadUtils.getCallingClassName());

        bar();
    }

    private void bar() {
        assertEquals("foo", ThreadUtils.getCallingMethodName());

        assertEquals("bar", ThreadUtils.getCallingMethodName(0));
        assertEquals("foo", ThreadUtils.getCallingMethodName(1));
        assertEquals("testGetCallingMethodName", ThreadUtils.getCallingMethodName(2));
    }
}
