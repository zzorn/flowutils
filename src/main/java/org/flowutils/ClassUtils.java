package org.flowutils;


import static org.flowutils.Check.notNull;

/**
 *
 */
public class ClassUtils {

    public static <T extends Number> T convertNumber(Number numberToConvert, T typeOfNumberToConvertTo) {
        notNull(typeOfNumberToConvertTo, "typeOfNumberToConvertTo");
        notNull(numberToConvert, "numberToConvert");

        return convertNumber(numberToConvert, (Class<T>)typeOfNumberToConvertTo.getClass());
    }

    public static <T extends Number> T convertNumber(Number numberToConvert, Class<T> numberType) {
        notNull(numberType, "numberType");
        notNull(numberToConvert, "numberToConvert");

        if (numberType == Integer.class     || numberType == Integer.TYPE) return (T) Integer.valueOf(numberToConvert.intValue());
        else if (numberType == Float.class  || numberType == Float.TYPE)   return (T) Float.valueOf(numberToConvert.floatValue());
        else if (numberType == Double.class || numberType == Double.TYPE)  return (T) Double.valueOf(numberToConvert.doubleValue());
        else if (numberType == Long.class   || numberType == Long.TYPE)    return (T) Long.valueOf(numberToConvert.longValue());
        else if (numberType == Byte.class   || numberType == Byte.TYPE)    return (T) Byte.valueOf(numberToConvert.byteValue());
        else if (numberType == Short.class  || numberType == Short.TYPE)   return (T) Short.valueOf(numberToConvert.shortValue());
        else if (numberType == Ranged.class)                               return (T) new Ranged(numberToConvert.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + numberType);
    }

    /**
     * @return a + b as a Number instance of the same type as a.
     */
    public static <T extends Number> T addNumbers(T a, T b) {
        notNull(a, "a");
        notNull(b, "b");

        if (a instanceof Integer) return (T) Integer.valueOf(a.intValue() + b.intValue());
        else if (a instanceof Float) return (T) Float.valueOf(a.floatValue() + b.floatValue());
        else if (a instanceof Double) return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
        else if (a instanceof Long) return (T) Long.valueOf(a.longValue() + b.longValue());
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (a.byteValue() + b.byteValue()));
        else if (a instanceof Short) return (T) Short.valueOf((short) (a.shortValue() + b.shortValue()));
        else if (a instanceof Ranged) return (T) new Ranged(a.doubleValue() + b.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    /**
     * @return a - b as a Number instance of the same type as a.
     */
    public static <T extends Number> T subNumbers(T a, T b) {
        notNull(a, "a");
        notNull(b, "b");

        if (a instanceof Integer) return (T) Integer.valueOf(a.intValue() - b.intValue());
        else if (a instanceof Float) return (T) Float.valueOf(a.floatValue() - b.floatValue());
        else if (a instanceof Double) return (T) Double.valueOf(a.doubleValue() - b.doubleValue());
        else if (a instanceof Long) return (T) Long.valueOf(a.longValue() - b.longValue());
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (a.byteValue() - b.byteValue()));
        else if (a instanceof Short) return (T) Short.valueOf((short) (a.shortValue() - b.shortValue()));
        else if (a instanceof Ranged) return (T) new Ranged(a.doubleValue() - b.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    /**
     * @return a * b as a Number instance of the same type as a.
     */
    public static <T extends Number> T mulNumbers(T a, T b) {
        notNull(a, "a");
        notNull(b, "b");

        if (a instanceof Integer) return (T) Integer.valueOf(a.intValue() * b.intValue());
        else if (a instanceof Float) return (T) Float.valueOf(a.floatValue() * b.floatValue());
        else if (a instanceof Double) return (T) Double.valueOf(a.doubleValue() * b.doubleValue());
        else if (a instanceof Long) return (T) Long.valueOf(a.longValue() * b.longValue());
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (a.byteValue() * b.byteValue()));
        else if (a instanceof Short) return (T) Short.valueOf((short) (a.shortValue() * b.shortValue()));
        else if (a instanceof Ranged) return (T) new Ranged(a.doubleValue() * b.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    /**
     * @return a / b as a Number instance of the same type as a.
     */
    public static <T extends Number> T divNumbers(T a, T b) {
        notNull(a, "a");
        notNull(b, "b");

        if (a instanceof Integer) return (T) Integer.valueOf(a.intValue() / b.intValue());
        else if (a instanceof Float) return (T) Float.valueOf(a.floatValue() / b.floatValue());
        else if (a instanceof Double) return (T) Double.valueOf(a.doubleValue() / b.doubleValue());
        else if (a instanceof Long) return (T) Long.valueOf(a.longValue() / b.longValue());
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (a.byteValue() / b.byteValue()));
        else if (a instanceof Short) return (T) Short.valueOf((short) (a.shortValue() / b.shortValue()));
        else if (a instanceof Ranged) return (T) new Ranged(a.doubleValue() / b.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    /**
     * @return the modulus of a and b, a %b as a Number instance of the same type as a.
     */
    public static <T extends Number> T modNumbers(T a, T b) {
        notNull(a, "a");
        notNull(b, "b");

        if (a instanceof Integer) return (T) Integer.valueOf(a.intValue() % b.intValue());
        else if (a instanceof Float) return (T) Float.valueOf(a.floatValue() % b.floatValue());
        else if (a instanceof Double) return (T) Double.valueOf(a.doubleValue() % b.doubleValue());
        else if (a instanceof Long) return (T) Long.valueOf(a.longValue() % b.longValue());
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (a.byteValue() % b.byteValue()));
        else if (a instanceof Short) return (T) Short.valueOf((short) (a.shortValue() % b.shortValue()));
        else if (a instanceof Ranged) return (T) new Ranged(a.doubleValue() % b.doubleValue());
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    /**
     * @return the absolute value of a, as a Number instance of the same type as a.
     */
    public static <T extends Number> T absNumber(T a) {
        notNull(a, "a");

        if (a instanceof Integer) return (T) Integer.valueOf(Math.abs(a.intValue()));
        else if (a instanceof Float) return (T) Float.valueOf(Math.abs(a.floatValue()));
        else if (a instanceof Double) return (T) Double.valueOf(Math.abs(a.doubleValue()));
        else if (a instanceof Long) return (T) Long.valueOf(Math.abs(a.longValue()));
        else if (a instanceof Byte) return (T) Byte.valueOf((byte) (Math.abs(a.intValue())));
        else if (a instanceof Short) return (T) Short.valueOf((short) (Math.abs(a.intValue())));
        else if (a instanceof Ranged) return (T) new Ranged(Math.abs(a.doubleValue()));
        else throw new UnsupportedOperationException("Unexpected number type " + a.getClass());
    }

    public static boolean isWrappedPrimitiveType(Class<?> type) {
        return Boolean.class.equals(type) ||
               Byte.class.equals(type) ||
               Short.class.equals(type) ||
               Integer.class.equals(type) ||
               Long.class.equals(type) ||
               Float.class.equals(type) ||
               Double.class.equals(type) ||
               Character.class.equals(type);
    }

    public static String wrappedPrimitiveTypeAsConstantString(Object value) {
        notNull(value, "value");
        if (!isWrappedPrimitiveType(value.getClass())) throw new IllegalArgumentException("Not a wrapped primitive type: " + value.getClass());

        if (Boolean.class.isInstance(value)) return value.toString();
        else if (Byte.class.isInstance(value)) return value.toString();
        else if (Short.class.isInstance(value)) return value.toString();
        else if (Integer.class.isInstance(value)) return value.toString();
        else if (Long.class.isInstance(value)) return value.toString();
        else if (Float.class.isInstance(value)) return value.toString() + "f";
        else if (Double.class.isInstance(value)) return value.toString() + "d";
        else if (Character.class.isInstance(value)) return "'" + value + "'";
        else throw new IllegalStateException("Unhandled type: " + value.getClass());
    }

    public static String getPrimitiveTypeNameOrNull(Class<?> type) {
        notNull(type, "type");

        if (Boolean.class.equals(type)) return "boolean";
        else if (Byte.class.equals(type)) return "byte";
        else if (Short.class.equals(type)) return "short";
        else if (Integer.class.equals(type)) return "int";
        else if (Long.class.equals(type)) return "long";
        else if (Float.class.equals(type)) return "float";
        else if (Double.class.equals(type)) return "double";
        else if (Character.class.equals(type)) return "char";

        else if (Boolean.TYPE.equals(type)) return "boolean";
        else if (Byte.TYPE.equals(type)) return "byte";
        else if (Short.TYPE.equals(type)) return "short";
        else if (Integer.TYPE.equals(type)) return "int";
        else if (Long.TYPE.equals(type)) return "long";
        else if (Float.TYPE.equals(type)) return "float";
        else if (Double.TYPE.equals(type)) return "double";
        else if (Character.TYPE.equals(type)) return "char";
        else return null;
    }

    public static String getTypeDeclaration(Class<?> type) {
        StringBuilder s = new StringBuilder();

        if (!type.isArray()) {
            // Normal class
            s.append(type.getName());
        }
        else {
            Class<?> componentType = type.getComponentType();
            while  (componentType.isArray()) {
                componentType = componentType.getComponentType();
            }

            s.append(componentType.getName());

            // Handle multidimensional arrays
            int dimensions = StringUtils.countCharacters(type.getName(), '[');
            for (int i = 0; i < dimensions; i++) {
                s.append("[]");
            }
        }

        return s.toString();
    }
}
