package org.flowutils.classbuilder;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.flowutils.classbuilder.SourceLocation.*;


public class ClassBuilderTest {

    @Test
    public void testClassBuilder() throws Exception {

        ClassBuilder<TestCalculation1> builder = new JaninoClassBuilder<>(
                TestCalculation1.class,
                "calculateSomeStuff",
                "fooString", "colors", "numbers");

        builder.addSourceLine(BEFORE_CALCULATION, "int sum = 0;");
        builder.addSourceLine(AT_CALCULATION, "for (int i = 0; i < numbers.length; i++) {");
        builder.addSourceLine(AT_CALCULATION, "  sum += numbers[i];");
        builder.addSourceLine(AT_CALCULATION, "}");
        builder.addSourceLine(AFTER_CALCULATION, "return sum;");
        builder.addImport(HashMap.SimpleEntry.class);
        builder.addImport(Boolean.TYPE);

        //System.out.println("Source: \n" + builder.createSource());

        final TestCalculation1 calculator = builder.createInstance();

        Assert.assertEquals(0, calculator.calculateSomeStuff("foo", null));
        Assert.assertEquals(3, calculator.calculateSomeStuff("foo", null, 3));
        Assert.assertEquals(2+4+8, calculator.calculateSomeStuff("bar", null, 2, 4, 8));
    }


    public static interface TestCalculation1 {
        int calculateSomeStuff(String aString, Color[] colors, int... numbers);
    }
}
