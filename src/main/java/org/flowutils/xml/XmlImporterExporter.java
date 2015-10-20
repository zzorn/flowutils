package org.flowutils.xml;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;

/**
 * Interface for something that imports and exports objects from/to xml.
 */
public interface XmlImporterExporter<T> {

    /**
     * Parse an object from xml.
     *
     * @param xmlEventReader used to read xml elements and content from.
     * @return the parsed object.
     */
    T readFromXml(XMLEventReader xmlEventReader);

    /**
     * Serialize an object to xml.
     *
     * @param xmlEventWriter used to write xml elements and content to.
     * @param xmlEventFactory used to create xml element instances.
     * @param object object to serialize to xml.
     */
    void writeToXml(XMLEventWriter xmlEventWriter, XMLEventFactory xmlEventFactory, T object);

}
