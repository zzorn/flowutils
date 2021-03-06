package org.flowutils.serializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

/**
 * Serializes java objects to binary data and the other way.
 *
 * By default serializes only the classes that have been registered, and throws an exception if any other classes
 * are attempted to be serialized.
 *
 * A serializer implementation is not necessarily thread safe, use a ConcurrentSerializer implementation if you need
 * to use the serializer in a multithreaded environment.
 */
public interface Serializer {

    /**
     * @return if true, only the registered classes can be serialized,
     *         if false, any class can be serialized.  Defaults to true.
     */
    boolean isRequireRegistration();

    /**
     * @param allowAllClasses if true, only the registered classes can be serialized,
     *                        if false, any class can be serialized.  Defaults to true.
     */
    void setRequireRegistration(boolean allowAllClasses);

    /**
     * Register commonly used collection classes as allowed.
     */
    void registerCommonCollectionClasses();

    /**
     * Must be called before the first call to serialize or deserialize.  Should not be called afterward.
     * @param allowedClasses add classes that are allowed to be serialized by this serializer.
     */
    void registerAllowedClasses(Collection<Class> allowedClasses);

    /**
     * Must be called before the first call to serialize or deserialize.  Should not be called afterward.
     * @param allowedClass class to add that is allowed to be serialized by this serializer.
     */
    void registerAllowedClass(Class allowedClass);

    /**
     * Must be called before the first call to serialize or deserialize.  Should not be called afterward.
     * @param allowedClasses add classes that are allowed to be serialized by this serializer.
     */
    void registerAllowedClasses(Class... allowedClasses);

    /**
     *
     * @return the classes that are allowed to be serialized, if isRequireRegistration is false.
     */
    Set<Class> getAllowedClasses();

    /**
     * Serialize the object to a byte array.
     * @param object object to serialize.
     * @return serialized form of object.
     */
    byte[] serialize(Object object);

    /**
     * Serialize the object and saves it to a file.
     * @param object object to serialize.
     * @param outputFile file to write the serialized object to.
     * @param overwrite if true, the file will be overwritten if it exists, if false, an exception will be thrown if the file already exists.
     */
    void serialize(Object object, File outputFile, boolean overwrite) throws IOException;

    /**
     * Deserialize the object from a byte array.
     *
     * @param data serialized form of object.
     * @return de-serialized object.
     */
    <T> T deserialize(byte[] data);

    /**
     * Deserialize the object from an input stream.
     *
     * @param inputStream input data as a stream.
     * @return de-serialized object.
     */
    <T> T deserialize(InputStream inputStream);

    /**
     * Deserialize the object from a file.
     *
     * @param inputFile to read the serialized object from.
     * @return de-serialized object.
     */
    <T> T deserialize(File inputFile) throws IOException;

    /**
     * Deserialize the object from a byte array.
     *
     * @param expectedType expected type of object.  If unknown, just pass in Object.class.
     * @param data serialized form of object.
     * @return de-serialized object.
     */
    <T> T deserialize(Class<T> expectedType, byte[] data);

    /**
     * Deserialize the object from an input stream.
     *
     * @param expectedType expected type of object.  If unknown, just pass in Object.class.
     * @param inputStream input data as a stream.
     * @return de-serialized object.
     */
    <T> T deserialize(Class<T> expectedType, InputStream inputStream);

    /**
     * Deserialize the object from a file.
     *
     * @param expectedType expected type of object.  If unknown, just pass in Object.class.
     * @param inputFile to read the serialized object from.
     * @return de-serialized object.
     */
    <T> T deserialize(Class<T> expectedType, File inputFile) throws IOException;

    /**
     * Register a class to allow based on its fully qualified name.
     * This allows registering classes that are private inner classes and thus not accessible in other ways.
     * @param className fully qualified name of class to register.
     */
    void registerAllowedClass(String className);
}
