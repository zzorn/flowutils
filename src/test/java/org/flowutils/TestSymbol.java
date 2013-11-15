package org.flowutils;

import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestSymbol {
    @Test
    public void testSymbol() throws Exception {
        final Symbol foo1 = Symbol.get("foo");
        final Symbol foo2 = Symbol.get(new String(new char[]{'f', 'o', 'o'}));
        final Symbol bar = Symbol.get("bar");

        assertEquals(foo1, foo2);
        assertNotEquals(foo1, bar);
        assertTrue(foo1 == foo2);
        assertTrue(foo1 != bar);


        assertEquals("foo", foo1.getName());
        assertEquals("foo", foo2.getName());


        Symbol.get("_ahab");
        Symbol.get("abc123");
        Symbol.get("abc__123");
        Symbol.get("x");
        Symbol.get("_");

        try {
            Symbol.get(null);
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("  \n ");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get(" asdf");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("a sdf");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("asdf ");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("1asd");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("Ångström");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("asd$asd");
            fail();
        }
        catch (IllegalArgumentException e){}

        try {
            Symbol.get("-+asd23");
            fail();
        }
        catch (IllegalArgumentException e){}
    }
}
