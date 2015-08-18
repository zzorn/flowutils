package org.flowutils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Utilities for working with files.
 */
public class FileUtils {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    /**
     * @param file file to read.
     * @return contents of the file as text, assumes content is in UTF-8 format.
     * Throws an illegal argument exception if the file was not found or could not be read.
     */
    public static String readFile(File file) {
        try {
            return readStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file '" + file + "': " + e.getMessage(), e);
        }
    }

    /**
     * @param resourcePath path to resource to read
     * @return contents of the resource as text, assumes content is in UTF-8 format.
     * Throws an illegal argument exception if the resource was not found or could not be read.
     */
    public static String readResource(String resourcePath) {
        try {
            return readStream(FileUtils.class.getResourceAsStream("/" + resourcePath.replace('\\', '/')));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read resource '" + resourcePath + "': " + e.getMessage(), e);
        }
    }

    /**
     * @return contents of the stream as text, assumes content is in UTF-8 format.
     * @throws java.io.IOException if there was some problem.
     */
    public static String readStream(InputStream reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader, UTF8_CHARSET));
        try {
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                builder.append(line);
                builder.append('\n');
                line = bufferedReader.readLine();
            }

            return builder.toString();
        } finally {
            bufferedReader.close();
        }
    }


    /**
     * Saves the text to the specified file in UTF-8 format, using a temp file as a temporary initial storage.
     * Checks that the data written to the file matches with the data in memory before deleting the original file and replacing it with the temporary file.
     * Automatically creates a tempFile with the same name as the file except ".temp" appended.
     *
     * @param text text to save
     * @param file file to save data to
     * @throws IOException if there was some problem at any step when saving the data or verifying the saved data.
     */
    public static void saveAndCheck(String text, final File file) throws IOException {
        saveAndCheck(text.getBytes(UTF8_CHARSET), file, createTempFileName(file));
    }

    /**
     * Saves the text to the specified file in UTF-8 format, using the specified temp file as a temporary initial storage.
     * Checks that the data written to the file matches with the data in memory before deleting the original file and replacing it with the temporary file.
     *
     * @param text text to save
     * @param file file to save data to
     * @param tempFile temporary file to first save data to.
     * @throws IOException if there was some problem at any step when saving the data or verifying the saved data.
     */
    public static void saveAndCheck(String text, final File file, final File tempFile) throws IOException {
        saveAndCheck(text.getBytes(UTF8_CHARSET), file, tempFile);
    }


    /**
     * Saves the data to the specified file, using a temp file as a temporary initial storage.
     * Checks that the data written to the file matches with the data in memory before deleting the original file and replacing it with the temporary file.
     * Automatically creates a tempFile with the same name as the file except ".temp" appended.
     *
     * @param data data to save
     * @param file file to save data to
     * @throws IOException if there was some problem at any step when saving the data or verifying the saved data.
     */
    public static void saveAndCheck(byte[] data, final File file) throws IOException {
        saveAndCheck(data, file, createTempFileName(file));
    }

    /**
     * Saves the data to the specified file, using the specified temp file as a temporary initial storage.
     * Checks that the data written to the file matches with the data in memory before deleting the original file and replacing it with the temporary file.
     *
     * @param data data to save
     * @param file file to save data to
     * @param tempFile temporary file to first save data to.
     * @throws IOException if there was some problem at any step when saving the data or verifying the saved data.
     */
    public static void saveAndCheck(byte[] data, final File file, final File tempFile) throws IOException {
        // Save to temp file
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile, false));
        try {
            outputStream.write(data);
            outputStream.flush();
        } finally {
            outputStream.close();
        }

        // Sanity check (e.g. if file system just pretends to write things)
        if (!tempFile.exists()) throw new IllegalStateException("The temp file ("+
                                                                tempFile +") we just saved the stored object to does not exist anymore!");

        // Check that the written file matches the data
        InputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile));
        final byte[] writtenFileData;
        try {
            writtenFileData = StreamUtils.readBytesFromInputStream(inputStream);
        }
        finally {
            inputStream.close();
        }

        if (writtenFileData.length != data.length) throw new IOException("The contents of the temp file ("+
                                                                         tempFile +") do not match the data written there! (different length)");
        for (int i = 0; i < data.length; i++) {
            if (data[i] != writtenFileData[i]) throw new IOException("The contents of the temp file ("+
                                                                     tempFile +") do not match the data written there! (different data at byte "+i+")");
        }

        // Delete the real file
        if (file.exists() && !file.delete()) {
            throw new IOException("Could not delete the storage file ("+
                                  file +") so that we could replace it with the temporary file ("+
                                  tempFile +") with new data.");
        }

        // Replace real file with temp file
        if (!tempFile.renameTo(file)) {
            throw new IOException("Could not rename the temporary storage file ("+
                                  tempFile +") to the real storage file ("+
                                  file +").  "+
                                  tempFile +" now contains the only copy of the data, move it to another name manually to avoid it getting overwritten!");
        }
    }

    /**
     * Loads the binary contents of the specified file.
     * @param file file to load
     * @return bytes in the file
     * @throws IOException thrown if there was some problem when reading the file.
     */
    public static byte[] loadData(final File file) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] data;
        try {
            data = StreamUtils.readBytesFromInputStream(inputStream);
        }
        finally {
            inputStream.close();
        }
        return data;
    }


    /**
     * @return a temporary file name derived from the supplied input file.
     */
    public static File createTempFileName(File file) {
        return new File(file.getPath() + ".temp");
    }



}
