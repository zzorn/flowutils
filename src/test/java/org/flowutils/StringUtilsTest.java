package org.flowutils;

import org.junit.Assert;

import static org.flowutils.StringUtils.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StringUtilsTest {

    @Test
    public void testCollectionToString() throws Exception {
        final List<Object> emptyList = Arrays.asList();
        final List<String> testList = Arrays.asList("foo", "bar", "zap");
        final List<String> nullsLists = Arrays.asList("foo", null, "zap");

        assertEquals("", collectionToString(emptyList));
        assertEquals("foo, bar, zap", collectionToString(testList));
        assertEquals("foo, null, zap", collectionToString(nullsLists));
        assertEquals("", collectionToString(null));

        assertEquals("foo:bar:zap", collectionToString(testList, ":"));
        assertEquals("[foo, bar, zap]", collectionToString(testList, ", ", "[", "]"));
        assertEquals("[]", collectionToString(emptyList, ", ", "[", "]"));
        assertEquals("[(foo), (bar), (zap)]", collectionToString(testList, ", ", "[", "]", "(", ")"));
        assertEquals("[(foo), (bar), (zap)]", arrayToString(new String[]{"foo", "bar", "zap"}, ", ", "[", "]", "(", ")"));
        assertEquals("[(foo), (null), (zap)]", collectionToString(nullsLists, ", ", "[", "]", "(", ")"));
        assertEquals("[]", collectionToString(emptyList, ", ", "[", "]", "(", ")"));
        assertEquals("[]", collectionToString(null, ", ", "[", "]", "(", ")"));
        assertEquals("[foo, bar, zap]", collectionToJsonString(testList));
        assertEquals("[\"foo\", \"bar\", \"zap\"]", collectionToQuotedJsonString(testList));
    }

    @Test
    public void testCapitalize() throws Exception {
        assertEquals(null, capitalize(null));
        assertEquals("", capitalize(""));
        assertEquals("Foo", capitalize("foo"));
        assertEquals("1foo", capitalize("1foo"));
        assertEquals("Ångström", capitalize("ångström"));
    }

    @Test
    public void testCountCharacters() throws Exception {
        assertEquals(4, countCharacters("asdfaadsa", 'a'));
        assertEquals(0, countCharacters("", 'a'));
        assertEquals(0, countCharacters("b", 'a'));
    }

    @Test
    public void testIdentifierFromName() throws Exception {
        assertEquals("fooBarBaz", identifierFromName("foo bar baz"));
        assertEquals("ångström", identifierFromName("ångström"));
        assertEquals("foo", identifierFromName("\nfoo\t\r"));
        assertEquals("foo", identifierFromName("  foo  "));
        assertEquals("foo__bar", identifierFromName("foo\n\nbar"));
        assertEquals("fooaabar", identifierFromName("foo\n\nbar", 'a'));
    }

    @Test
    public void testIsStrictIdentifier() throws Exception {
        assertTrue(isStrictIdentifier("a"));
        assertTrue(isStrictIdentifier("foo"));
        assertTrue(isStrictIdentifier("fooBar"));
        assertTrue(isStrictIdentifier("foo_bar"));
        assertTrue(isStrictIdentifier("foo123"));
        assertTrue(isStrictIdentifier("__foo__"));

        assertFalse(isStrictIdentifier("$foo"));
        assertFalse(isStrictIdentifier("ångström"));
        assertFalse(isStrictIdentifier("123foo"));
        assertFalse(isStrictIdentifier(" bar"));
        assertFalse(isStrictIdentifier(" bar "));
        assertFalse(isStrictIdentifier("bar "));
        assertFalse(isStrictIdentifier("\n"));
        assertFalse(isStrictIdentifier(" "));
        assertFalse(isStrictIdentifier(""));
        assertFalse(isStrictIdentifier(null));
    }

    @Test
    public void testIsJavaIdentifier() throws Exception {
        assertTrue(isJavaIdentifier("a"));
        assertTrue(isJavaIdentifier("foo"));
        assertTrue(isJavaIdentifier("fooBar"));
        assertTrue(isJavaIdentifier("foo_bar"));
        assertTrue(isJavaIdentifier("foo123"));
        assertTrue(isJavaIdentifier("__foo__"));
        assertTrue(isJavaIdentifier("$foo"));
        assertTrue(isJavaIdentifier("ångström"));

        assertFalse(isJavaIdentifier("123foo"));
        assertFalse(isJavaIdentifier(" bar"));
        assertFalse(isJavaIdentifier(" bar "));
        assertFalse(isJavaIdentifier("bar "));
        assertFalse(isJavaIdentifier("\n"));
        assertFalse(isJavaIdentifier(" "));
        assertFalse(isJavaIdentifier(""));
        assertFalse(isJavaIdentifier(null));

    }
}
