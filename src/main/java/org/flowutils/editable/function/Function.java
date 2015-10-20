package org.flowutils.editable.function;

import org.flowutils.Symbol;
import org.flowutils.editable.Type;
import org.flowutils.editable.TypedValue;
import org.flowutils.editable.object.Member;
import org.flowutils.editable.object.Property;

import java.util.List;
import java.util.Map;

/**
 * Interface for a callable function.
 */
public interface Function<T> extends Member {

    /**
     *
     * @return the parameters required by this function.
     */
    List<Parameter> getParameters();

    Type<T> getReturnType();

    T calculate(Map<Symbol, Object> parameterValues);

    void calculate(Map<Symbol, TypedValue> parameterValues, TypedValue output);

    void calculate(Map<Symbol, TypedValue> parameterValues, Object objectInstance, Property outputProperty);

}
