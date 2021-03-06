package org.flowutils.collections.props;

import org.flowutils.Symbol;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropsTest {

    @Test
    public void testProperties() throws Exception {
        final PropsMap defaults = new PropsMap();
        defaults.set("Foo", 1);
        defaults.set("Zap", 11);

        PropsMap properties = new PropsMap(defaults);

        assertEquals(null, properties.get("Bar"));
        assertEquals(1, properties.get("Foo"));

        properties.set("Foo", 4);

        assertEquals(4, properties.get("Foo"));
        assertEquals(11, properties.get("Zap"));
        assertEquals(true, properties.has("Zap"));
        assertEquals(null, properties.get("foo"));
        assertEquals(null, properties.get("bar"));
        assertEquals(false, properties.has("bar"));

        properties.set("Foo", 5);
        properties.set("bar", 89);

        assertEquals(5, properties.get("Foo"));
        assertEquals(89, properties.get("bar"));
        assertEquals(true, properties.has("bar"));

        properties.remove(Symbol.get("bar"));
        assertEquals(null, properties.get("bar"));
        assertEquals(false, properties.has("bar"));

        properties.removeAllDefaults();

        assertEquals(null, properties.get("Zap"));
        assertEquals(false, properties.has("Zap"));


    }

}
