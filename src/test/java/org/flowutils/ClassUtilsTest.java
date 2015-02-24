package org.flowutils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.flowutils.ClassUtils.isWrappedPrimitiveType;
import static org.flowutils.ClassUtils.wrappedPrimitiveTypeAsConstantString;

/**
 *
 */
public class ClassUtilsTest {

    @Test
    public void testNumberConversion() throws Exception {
        assertEquals(Double.valueOf(23.0), ClassUtils.convertNumber(23, 1.1));
        assertEquals(Double.valueOf(23.0), ClassUtils.convertNumber(23, Double.valueOf(1)));
        assertEquals(Double.valueOf(23.0), ClassUtils.convertNumber(23, Double.TYPE));
        assertEquals(Double.valueOf(23.0), ClassUtils.convertNumber(23, Double.class));
        assertEquals(Double.valueOf(23.0), ClassUtils.convertNumber(23.0, 1.1));
        assertEquals(Float.valueOf(23), ClassUtils.convertNumber(23.0, 1.1f));
        assertEquals(Integer.valueOf(23), ClassUtils.convertNumber(23.0, 1));
        assertEquals(Short.valueOf((short) 23), ClassUtils.convertNumber(23.2, (short)1));
        assertEquals(Long.valueOf(23), ClassUtils.convertNumber(23.2, 1L));
        assertEquals(Byte.valueOf((byte) 23), ClassUtils.convertNumber(23.0, Byte.TYPE));
        assertEquals(Byte.valueOf((byte) 23), ClassUtils.convertNumber(23.0, Byte.class));
        assertEquals(Byte.valueOf((byte) 23), ClassUtils.convertNumber(23.1, Byte.class));

    }

    @Test
    public void testIsWrappedPrimitive() throws Exception {

        assertTrue(isWrappedPrimitiveType(Boolean.class));
        assertTrue(isWrappedPrimitiveType(Byte.class));
        assertTrue(isWrappedPrimitiveType(Short.class));
        assertTrue(isWrappedPrimitiveType(Integer.class));
        assertTrue(isWrappedPrimitiveType(Long.class));
        assertTrue(isWrappedPrimitiveType(Float.class));
        assertTrue(isWrappedPrimitiveType(Double.class));
        assertTrue(isWrappedPrimitiveType(Character.class));

        assertFalse(isWrappedPrimitiveType(String.class));
        assertFalse(isWrappedPrimitiveType(Object.class));
        assertFalse(isWrappedPrimitiveType(List.class));
    }

    @Test
    public void testToConstantString() throws Exception {
        assertEquals("true", wrappedPrimitiveTypeAsConstantString(Boolean.TRUE));
        assertEquals("3", wrappedPrimitiveTypeAsConstantString(3));
        assertEquals("5.0f", wrappedPrimitiveTypeAsConstantString((float) 5));
        assertEquals("-1.0d", wrappedPrimitiveTypeAsConstantString((double) -1));
        assertEquals("'f'", wrappedPrimitiveTypeAsConstantString('f'));
        assertEquals("'_'", wrappedPrimitiveTypeAsConstantString('_'));
    }
}
