package org.flowutils.editable.object;

import org.flowutils.Symbol;
import org.flowutils.editable.Variable;
import org.flowutils.editable.function.Function;
import org.flowutils.updating.Updating;

import java.util.List;

/**
 * Used to calculate a value for properties or values.
 */
public interface ValueCalculator<T> extends Updating {

    /**
     * @return calculate the value, using the current values of the parameter values.
     */
    T calculateValue();

    /**
     * @return the function used to calculate the value.
     */
    Function getValueCalculationFunction();

    /**
     * @return the values used for parameters for the value calculation function.
     */
    List<Variable> getParameters();

    /**
     * @return the specified parameter object.
     */
    Variable getParameter(Symbol parameterId);

}
