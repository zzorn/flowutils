package org.flowutils.classbuilder;

/**
 * Used to build up source for a class, and then compiling the source and getting the compiled class or an instance of it.
 *
 * @param <T> a type that the generated class implements or extends.
 */
public interface ClassBuilder<T> {

    /**
     * Adds the specified line to the source at the specified location.
     * Indentation and terminating newline will be added to the line.
     */
    void addSourceLine(SourceLocation location, String line);

    /**
     * Adds a source line that imports the specified class.
     * Ignores imports of primitives, and imports the component type of array types.
     */
    void addImport(Class type);

    /**
     * Constructs the source from the currently added source lines if they have changed since the last call, and returns it.
     */
    String createSource();

    /**
     * Compiles the current code if it has changed since the last call, and returns the compiled class.
     *
     * @return class representing the compiled code.
     * @throws ClassBuilderException if there was any problem compiling the code.
     */
    Class<? extends T> createClass() throws ClassBuilderException;

    /**
     * Compiles the current code if it has changed since the last call,
     * and returns an instance of the compiled class, using a no-arguments constructor.
     *
     * @return new instance of the generated class.
     * @throws ClassBuilderException if there was any problem compiling the code or creating the instance.
     */
    T createInstance() throws ClassBuilderException;
}
