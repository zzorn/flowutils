package org.flowutils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.flowutils.Check.*;

public class CheckTest {

    @Test
    public void testInvariant() throws Exception {
        invariant(true, "should work");

        try {
            invariant(false, "should fail");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testNullChecks() throws Exception {
        notNull(0, "foo");

        try {
            notNull(null, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testEqual() throws Exception {
        Check.equals("abc", "foo", "abc"+"", "bar");
        Check.equals(2, "foo", 1+1, "bar");
        Check.equalRef(2, "foo", 1 + 1, "bar");

        String s = "asdf";
        Check.equalRef(s, "foo", s, "bar");

        try {
            Check.equals("abc", "foo", "abc"+"def", "bar");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            Check.equals("abc", "foo", "ABC", "bar");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            Check.equalRef("asdf", "foo", "ghjk", "bar");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testStrings() throws Exception {
        nonEmptyString("1", "foo");
        nonEmptyString("abc", "foo");
        nonEmptyString(" as \n d  ", "foo");

        try {
            nonEmptyString("", "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            nonEmptyString("\n", "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            nonEmptyString(" ", "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            nonEmptyString(" \t \r\n  ", "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testIdentifier() throws Exception {
        identifier("JavaIdentifier123", "id");
        identifier("x", "id");
        identifier("_3", "id");
        identifier("x_3", "id");
        identifier("$foo$bar$$$$", "id");
        identifier("Ångström", "id");

        try {
            identifier("?sdf", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            identifier("x^", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            identifier("Asdf+5", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}


        try {
            identifier("+-*/", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            identifier(" ", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            identifier("", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            identifier("3x", "id");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testCollections() throws Exception {
        List<String> l = Arrays.asList("a", "b", "c", "d");
        List<String> l2 = Arrays.asList(null, "asdf");
        List<String> l3 = Arrays.asList();
        Map<String, String> m = new HashMap<String, String>();
        Map<String, String> m2 = new HashMap<String, String>();
        m.put("abc", "123");
        m.put("def", "456");
        m2.put(null, "asdf");

        contained("a", l, "l");
        contained("c", l, "l");
        contained(null, l2, "l2");

        contained("abc", m, "m");
        contained(null, m2, "m2");

        notEmpty(l, "l");
        empty(l3, "l3");

        notContained(null, l, "l");
        notContained("g", l, "l");

        notContained("geh", m, "m");
        notContained(null, m, "m");

        try {
            contained("g", l, "l");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            contained(null, l, "l");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            contained("geh", m, "m");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            contained(null, m, "m");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notContained("a", l, "l");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notContained(null, l2, "l2");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notContained("abc", m, "m");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notContained(null, m2, "m2");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            empty(l, "l");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notEmpty(l3, "l3");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testInstanceOf() throws Exception {
        instanceOf("abc", "foo", String.class);
        instanceOf("abc", "foo", Object.class);
        notInstanceOf("abc", "foo", Integer.class);

        try {
            instanceOf("abc", "foo", Integer.class);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notInstanceOf("abc", "foo", Object.class);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}


        try {
            notInstanceOf("abc", "foo", String.class);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testFail() throws Exception {
        try {
            Check.fail("Some reason to fail");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            Check.fail("abc", "foo", "should be imaginary string");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testNormalNumber() throws Exception {
        normalNumber(0, "foo");
        normalNumber(-1, "foo");
        normalNumber(1, "foo");
        normalNumber(Double.MIN_NORMAL, "foo");
        normalNumber(-Double.MIN_NORMAL, "foo");

        try {
            normalNumber(Double.POSITIVE_INFINITY, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            normalNumber(Double.NEGATIVE_INFINITY, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            normalNumber(Float.NEGATIVE_INFINITY, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            normalNumber(Double.NaN, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testSignChecks() throws Exception {
        positive(1, "foo");
        positive(2309.23, "foo");
        positive(0.00001, "foo");
        positive(Integer.MAX_VALUE, "foo");

        positiveOrZero(0, "foo");
        positiveOrZero(0.0, "foo");
        positiveOrZero(0.00001, "foo");
        positiveOrZero(1, "foo");
        positiveOrZero(4234, "foo");
        positiveOrZero(4234.231f, "foo");

        negative(-0.001f, "foo");
        negative(-123123.123, "foo");
        negative(-1, "foo");
        negative(Integer.MIN_VALUE, "foo");

        negativeOrZero(0, "foo");
        negativeOrZero(0.0, "foo");
        negativeOrZero(-0.00001f, "foo");
        negativeOrZero(-1, "foo");
        negativeOrZero(-1123.123, "foo");
        negativeOrZero(Integer.MIN_VALUE, "foo");

        notZero(1, "foo");
        notZero(-1, "foo");
        notZero(Integer.MIN_VALUE, "foo");
        notZero(Integer.MAX_VALUE, "foo");
        notZero(0.001, "foo", 0.0001);
        notZero(-0.001, "foo", 0.0001);
        notZero(1.0, "foo", 0.0001);
        notZero(-1.0f, "foo", 0.0001);

        try {
            positive(0, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            positive(-1, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            positive(-0.0001, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            positiveOrZero(-0.0001, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            positiveOrZero(-1, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negative(0, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negative(0f, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negative(0.00001, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negative(1, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negativeOrZero(0.0012, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            negativeOrZero(1, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notZero(0, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            notZero(0.000f, "foo", 0.00001);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testComparisons() throws Exception {

        // Ints
        greater(3, "foo", 0, null);

        greater(3, "foo", 0, "limit");
        greater(-4, "foo", -5, "limit");

        greaterOrEqual(3, "foo", 0, "limit");
        greaterOrEqual(3, "foo", 3, "limit");
        greaterOrEqual(-4, "foo", -5, "limit");
        greaterOrEqual(-4, "foo", -4, "limit");

        less(-4, "foo", -3, "limit");
        less(1000, "foo", 1001, "limit");

        lessOrEqual(1000, "foo", 1001, "limit");
        lessOrEqual(1000, "foo", 1000, "limit");
        lessOrEqual(-1, "foo", 0, "limit");

        equal(0, "foo", 0, "target");
        equal(-42, "foo", -42, "target");
        equal(Integer.MAX_VALUE, "foo", Integer.MAX_VALUE, "target");

        // Floats
        greater(3.0, "foo", 0, null);
        greater(3.0f, "foo", 2.99, null);
        greater(3, "foo", 2.99, null);

        greaterOrEqual(3, "foo", 2.99, "limit");
        greaterOrEqual(3, "foo", 3.0, "limit");
        greaterOrEqual(3.0, "foo", 3, "limit");
        greaterOrEqual(3.01, "foo", 3, "limit");

        lessOrEqual(3, "foo", 3.1, "limit");
        lessOrEqual(3.0, "foo", 3, "limit");

        less(3, "foo", 3.000000001, "limit");
        less(3f, "foo", 3.000000001, "limit");
        less(3.0, "foo", 3.000000001, "limit");
        less(-0.1, "foo", 0, "limit");

        equal(3.14, "foo", 3.14f, "limit", 0.00001);
        equal(3, "foo", 4f, "limit", 1);

        // Test failures
        try {
            greaterOrEqual(3.14f, "param", Math.PI, "Pi");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            equal(3.14f, "param", Math.PI, "Pi", 0.0001);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            less(3, "param", 2, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            less(3, "param", 3, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            less(3f, "param", 3.0, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            greater(3, "param", 3.1, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            greater(-1, "param", 0, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            equal(1, "param", 0, "limit");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            equal(0f, "param", 0.000001, "limit", 0.00000001);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }


    @Test
    public void testRanges() throws Exception {
        inRange(0, "foo", -1, 1);
        inRange(0.1, "foo", -1, 1);
        inRange(0, "foo", -1.0, 1);

        inRange(43, "foo", 42.5, 43.01);
        inRange(43, "foo", 42, 44);
        inRange(43, "foo", 43, 44);

        inRangeInclusive(43, "foo", 42, 43);
        inRangeInclusive(43, "foo", 42.5, 43);

        inRangeZeroToOne(0, "foo");
        inRangeZeroToOne(0f, "foo");
        inRangeZeroToOne(0.34, "foo");
        inRangeZeroToOne(1.0, "foo");
        inRangeZeroToOne(1, "foo");

        try {
            inRange(43, "foo", 42.5, 43.0);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRange(43, "foo", 42, 43);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRange(41, "foo", 42, 43);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRangeInclusive(43, "foo", 41, 42);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRangeInclusive(40, "foo", 41, 42);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRangeZeroToOne(1.001, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRangeZeroToOne(-0.001, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            inRangeZeroToOne(2, "foo");
            Assert.fail();
        }
        catch (IllegalArgumentException e) {}
    }
}
