package org.flowutils;

import static org.flowutils.MathUtils.*;

/**
 * Fuzzy logic operations.  Operates on floating point numbers between zero and one.
 * From "n-ary Fuzzy Logic and Neutrosophic Logic Operators", table 1: http://arxiv.org/pdf/0808.3109.pdf
 *
 * Visualize a and b as partially overlapping circles in a Ven-diagram,
 * where multiplication is used to intersect areas and addition is used to include areas.
 */
public enum FuzzyLogic {

    /**
     * 0
     */
    FALSE,

    /**
     * 1
     */
    TRUE,

    /**
     * a
     */
    LEFT,

    /**
     * b
     */
    RIGHT,

    /**
     * 1 - a
     */
    NOT_LEFT,

    /**
     * 1 - b
     */
    NOT_RIGHT,

    /**
     * a * b
     */
    AND,

    /**
     * a or b or both.
     */
    OR,

    /**
     * a or b but not both.
     */
    XOR,

    /**
     * 1 - and
     */
    NAND,

    /**
     * 1 - or.
     */
    NOR,

    /**
     * a and b or not a and not b.
     */
    EQUIVALENCE,

    /**
     * only if.
     * a => b
     */
    IMPLICATION,

    /**
     * 1 - implication
     */
    NON_IMPLICATION,

    /**
     * if.
     * a <= b
     */
    CONVERSE_IMPLICATION,

    /**
     * 1 - converse implication
     */
    CONVERSE_NON_IMPLICATION,
    ;

    /**
     * Applies this fuzzy logic operation to two input values.
     * Clamps the inputs to the 0..1 range before calculating, to ensure that the result is also in that range.
     * Also ensures that a and b are not infinite or NaN.
     * @return result of the operation on a and b.  Range 0..1.
     */
    public float calculateAndClamp(float a, float b) {
        Check.normalNumber(a, "a");
        Check.normalNumber(b, "b");
        return calculate(clamp0To1(a), clamp0To1(b));
    }

    /**
     * Applies this fuzzy logic operation to two input values.
     * Clamps the inputs to the 0..1 range before calculating, to ensure that the result is also in that range.
     * Also ensures that a and b are not infinite or NaN.
     * @return result of the operation on a and b.  Range 0..1.
     */
    public double calculateAndClamp(double a, double b) {
        Check.normalNumber(a, "a");
        Check.normalNumber(b, "b");
        return calculate(clamp0To1(a), clamp0To1(b));
    }

    /**
     * Applies this fuzzy logic operation to two input values.
     * Does not check the value range of the parameters.
     * @return result of the operation on a and b.
     */
    public float calculate(float a, float b) {
        switch (this) {
            case FALSE:
                return 0f;
            case AND:
                return a*b;
            case NON_IMPLICATION:
                return a - a*b;
            case LEFT:
                return a;
            case CONVERSE_NON_IMPLICATION:
                return b - a*b;
            case RIGHT:
                return b;
            case XOR:
                return a + b - 2f*a*b;
            case OR:
                return a + b - a*b;
            case NOR:
                return 1f - a - b + a*b;
            case EQUIVALENCE:
                return 1f - a - b + 2f*a*b;
            case NOT_RIGHT:
                return 1f - b;
            case CONVERSE_IMPLICATION:
                return 1f - b + a*b;
            case NOT_LEFT:
                return 1f - a;
            case IMPLICATION:
                return 1f - a + a*b;
            case NAND:
                return 1f - a*b;
            case TRUE:
                return 1f;
            default:
                throw new IllegalStateException("Unhandled operation " + this);
        }
    }

    /**
     * Applies this fuzzy logic operation to two input values.
     * Does not check the value range of the parameters.
     * @return result of the operation on a and b.
     */
    public double calculate(double a, double b) {
        switch (this) {
            case FALSE:
                return 0.0;
            case AND:
                return a*b;
            case NON_IMPLICATION:
                return a - a*b;
            case LEFT:
                return a;
            case CONVERSE_NON_IMPLICATION:
                return b - a*b;
            case RIGHT:
                return b;
            case XOR:
                return a + b - 2.0*a*b;
            case OR:
                return a + b - a*b;
            case NOR:
                return 1.0 - a - b + a*b;
            case EQUIVALENCE:
                return 1.0 - a - b + 2.0*a*b;
            case NOT_RIGHT:
                return 1.0 - b;
            case CONVERSE_IMPLICATION:
                return 1.0 - b + a*b;
            case NOT_LEFT:
                return 1.0 - a;
            case IMPLICATION:
                return 1.0 - a + a*b;
            case NAND:
                return 1.0 - a*b;
            case TRUE:
                return 1.0;
            default:
                throw new IllegalStateException("Unhandled operation " + this);
        }
    }

    /**
     * Unary not operation
     */
    public static float not(float a) {
        return 1f - a;
    }

    /**
     * Unary not operation
     */
    public static double not(double a) {
        return 1.0 - a;
    }
}
