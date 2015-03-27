package org.flowutils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Utility functions for working with strings.
 */
public final class StringUtils {

    private static final int MILLISECONDS = 1;
    private static final int SECONDS = 1000 * MILLISECONDS;
    private static final int MINUTES = 60 * SECONDS;
    private static final int HOURS = 60 * MINUTES;
    private static final int DAYS = 24 * HOURS;

    /**
     * @return a valid java identifier, generated from the user readable name.
     */
    public static String identifierFromName(String userReadableName) {
        return identifierFromName(userReadableName, '_');
    }

    /**
     * @return a valid java identifier, generated from the user readable name.
     * If the identifier contains a non valid identifier character, it is replaced with the fillCharacter.
     */
    public static String identifierFromName(String userReadableName, char fillCharacter) {
        assert Character.isJavaIdentifierStart(fillCharacter) : "Fill character must be a valid java identifier start & part, but it was '" + fillCharacter + "'";

        // Remove extra whitespace
        userReadableName = userReadableName.trim();

        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        boolean firstCreated = false;
        for (int i = 0; i < userReadableName.length(); i++) {
            char c = userReadableName.charAt(i);

            // Skip spaces, capitalize character after space
            if (c == ' ') capitalizeNext = true;
            else {
                // Capitalize if needed
                if (capitalizeNext) {
                    c = Character.toUpperCase(c);
                    capitalizeNext = false;
                }

                if (!firstCreated) {
                    // Handle first character
                    if (Character.isJavaIdentifierStart(c)) {
                        // Valid, just add it
                        sb.append(c);
                    } else {
                        // Invalid, add fill char instead
                        sb.append(fillCharacter);

                        // Add the character if it was a valid part but not start (e.g. number)
                        if (Character.isJavaIdentifierPart(c)) sb.append(c);
                    }
                    firstCreated = true;
                }
                else {
                    if (Character.isJavaIdentifierPart(c)) sb.append(c);
                    else sb.append(fillCharacter);
                }
            }
        }

        // Make sure it is at least one char long
        if (sb.length() <= 0) sb.append(fillCharacter);

        return sb.toString();
    }

    /**
     * @return true if s is a valid Java style identifier (starts with unicode letter or underscore or $,
     * contains unicode letters, underscores, numbers, or $).
     */
    public static boolean isJavaIdentifier(String s) {
        if (s == null || s.isEmpty()) return false;
        else {
            if (!Character.isJavaIdentifierStart(s.charAt(0))) return false;
            for (int i = 1; i < s.length(); i++) {
                if (!Character.isJavaIdentifierPart(s.charAt(i))) return false;
            }
            return true;
        }
    }

    /**
     * @return true if s is a strict identifier (starts with a-z, A-Z, or _, contains a-z, A-Z, _, or 0-9).
     */
    public static boolean isStrictIdentifier(String s) {
        if (s == null || s.isEmpty()) return false;
        else {
            if (!isAsciiLetterOrUnderscore(s.charAt(0))) return false;
            for (int i = 1; i < s.length(); i++) {
                if (!isAsciiLetterOrUnderscoreOrNumber(s.charAt(i))) return false;
            }
            return true;
        }
    }

    /**
     * @return true if the character is an ascii letter or underscore.
     */
    public static boolean isAsciiLetterOrUnderscore(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    /**
     * @return true if the character is an ascii letter, number, or underscore.
     */
    public static boolean isAsciiLetterOrUnderscoreOrNumber(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               (c >= '1' && c <= '9') ||
               c == '0' ||
               c == '_';
    }


    /**
     * @return number of times the character c is found in the string s.
     */
    public static int countCharacters(String s, char c) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) sum++;
        }
        return sum;
    }


    /**
     * @return a version of the parameter string where the first character is converted to uppercase.
     */
    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        else {
            char capitalizedFirstChar = Character.toUpperCase(s.charAt(0));
            String restOfString = s.substring(1);
            return capitalizedFirstChar + restOfString;
        }
    }


    /**
     * @return a single string containing the elements of the array separated by the specified character.
     *         Note that the separator is the first argument.
     */
    public static String arrayToString(String separator, String ... elements) {
        return arrayToString(elements, separator, "", "", "", "");
    }

    /**
     * @return a single string containing the elements of the array separated by the specified separator,
     *         surrounded by the start and end strings, and with each element surrounded by elementStart and elementEnd strings.
     */
    public static String arrayToString(String[] stringArray, String separator, String start, String end, String elementStart, String elementEnd) {
        return collectionToString(Arrays.asList(stringArray), separator, start, end, elementStart, elementEnd);
    }

    /**
     * @return a single string containing the elements of the collection separated by commas.
     */
    public static String collectionToString(Collection<?> elements) {
        return collectionToString(elements, ", ", "", "", "", "");
    }

    /**
     * @return a single string containing the elements of the collection separated by commas, and surrounded by brackets [].
     */
    public static String collectionToJsonString(Collection<?> elements) {
        return collectionToString(elements, ", ", "[", "]", "", "");
    }

    /**
     * @return a single string containing the elements of the collection separated by commas, and surrounded by brackets [].
     *         Each element is surrounded by quotes "
     */
    public static String collectionToQuotedJsonString(Collection<?> elements) {
        return collectionToString(elements, ", ", "[", "]", "\"", "\"");
    }


    /**
     * @return a single string containing the elements of the collection separated by the specified separator.
     */
    public static String collectionToString(Collection<?> elements, String separator) {
        return collectionToString(elements, separator, "", "", "", "");
    }

    /**
     * @return a single string containing the elements of the collection separated by the specified separator
     *         and surrounded by the start and end strings.
     */
    public static String collectionToString(Collection<?> elements, String separator, String start, String end) {
        return collectionToString(elements, separator, start, end, "", "");
    }

    /**
     * @return a single string containing the elements of the collection separated by the specified separator,
     *         surrounded by the start and end strings, and with each element surrounded by elementStart and elementEnd strings.
     */
    public static String collectionToString(Collection<?> elements, String separator, String start, String end, String elementStart, String elementEnd) {
        StringBuilder builder = new StringBuilder();

        builder.append(start);

        if (elements != null) {
            boolean first = true;
            for (Object element : elements) {
                if (first) first = false;
                else builder.append(separator);

                builder.append(elementStart);
                builder.append(element);
                builder.append(elementEnd);
            }
        }

        builder.append(end);

        return builder.toString();
    }

    /**
     * @return the text that comes after the last occurrence of the specified character in the provided text (not including the character),
     * or an empty string if there is no such character, or if the character is last.
     * E.g. textAfter('.', "filename.txt.zip") would return "zip".
     */
    public static String textAfter(Character c, String text) {
        final int lastIndex = text.lastIndexOf(c);
        if (lastIndex < 0 || lastIndex >= text.length() - 1) {
            return "";
        }
        else {
            return text.substring(lastIndex + 1);
        }
    }

    /**
     * @return the text that comes before the first occurrence of the specified character in the provided text (not including the character),
     * or an empty string if there is no such character, or if the character is first.
     * E.g. textBefore('.', "filename.txt.zip") would return "filename".
     */
    public static String textBefore(Character c, String text) {
        final int firstIndex = text.indexOf(c);
        if (firstIndex <= 0) {
            return "";
        }
        else {
            return text.substring(0, firstIndex);
        }
    }

    /**
     * @return a human readable string representation in english for the specified number of milliseconds.
     * E.g. "1 day 5 hours 20 min 4s 100ms"
     */
    public static String timeIntervalToString(long milliseconds) {
        final StringBuilder s = new StringBuilder();

        // Append minus sign if interval is negative
        if (milliseconds < 0) {
            s.append("-");
            milliseconds = -milliseconds;
        }

        milliseconds = appendTimePeriod(milliseconds, s, DAYS, "day", "days", true);
        milliseconds = appendTimePeriod(milliseconds, s, HOURS, "hour", "hours", true);
        milliseconds = appendTimePeriod(milliseconds, s, MINUTES, "min", "min", true);
        milliseconds = appendTimePeriod(milliseconds, s, SECONDS, "s", "s", true);
        milliseconds = appendTimePeriod(milliseconds, s, MILLISECONDS, "ms", "ms", false);

        // Should be none left
        assert milliseconds == 0;

        return s.toString();
    }

    /**
     * Converts a timestamp of the type used in java Date class to a standard string representation.
     * @return timestamp in the format "yyyy-mm-dd hh:mm:ss.sss".
     */
    public static String timestampToString(long timestamp) {
        return timestampToString(timestamp, true);
    }

    /**
     * Converts a timestamp of the type used in java Date class to a standard string representation.
     * @return timestamp in the format "yyyy-mm-dd hh:mm:ss.sss" if includeTime is true, otherwise "yyyy-mm-dd".
     */
    public static String timestampToString(long timestamp, boolean includeTime) {
        return timestampToString(timestamp, includeTime, " ");
    }

    /**
     * Converts a timestamp of the type used in java Date class to a standard string representation.
     * @return timestamp in the format "yyyy-mm-dd"[timeSeparator]"hh:mm:ss.sss" if includeTime is true, otherwise "yyyy-mm-dd".
     */
    public static String timestampToString(long timestamp, boolean includeTime, String timeSeparator) {
        Date date = new Date(timestamp);
        StringBuilder s = new StringBuilder();

        s.append(date.getYear() + 1900);
        s.append("-");
        s.append(date.getMonth() + 1);
        s.append("-");
        s.append(date.getDate());

        if (includeTime) {
            s.append(timeSeparator);
            s.append(date.getHours());
            s.append(":");
            s.append(date.getMinutes());
            s.append(":");
            s.append(date.getSeconds());
            s.append(".");
            s.append(MathUtils.modPositive(timestamp, 1000));
        }

        return s.toString();
    }

    private static long appendTimePeriod(long milliseconds,
                                         StringBuilder s,
                                         final long periodLength,
                                         final String periodNameSingular,
                                         final String periodNamePlural,
                                         final boolean skipIfZero) {

        if (milliseconds >= periodLength || (!skipIfZero && s.length() == 0)) {

            if (s.length() > 1) s.append(" ");

            // Append number of periods and period name
            final long periods = milliseconds / periodLength;
            s.append(periods).append(" " + (periods == 1 ? periodNameSingular : periodNamePlural));

            // Remove periods from time remaining
            milliseconds %= periodLength;
        }

        return milliseconds;
    }

    private StringUtils() {
    }
}
