package org.flowutils;

/**
 * Something that can be testrun in multiple threads.
 */
public interface TestRun {

    void run() throws Exception;

}
