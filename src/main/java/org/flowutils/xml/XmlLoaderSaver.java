package org.flowutils.xml;

import java.io.File;

/**
 * Manages loading and saving object from / to xml files or strings.
 */
public interface XmlLoaderSaver<T> {

    T loadFromFile(File xmlFile);
    T loadFromFile(String xmlFileName);
    T loadFromString(String xmlText);

    void saveToFile(File xmlFile, T objectToSave);
    void saveToFile(String xmlFile, T objectToSave);
    String saveToString(T objectToSave);
}
