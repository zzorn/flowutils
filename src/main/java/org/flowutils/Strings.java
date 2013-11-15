package org.flowutils;

/**
 *
 */
public final class Strings {

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
            if (!isLetterOrUnderscore(s.charAt(0))) return false;
            for (int i = 1; i < s.length(); i++) {
                if (!isLetterOrUnderscoreOrNumber(s.charAt(i))) return false;
            }
            return true;
        }
    }

    /**
     * @return true if the character is an ascii letter or underscore.
     */
    public static boolean isLetterOrUnderscore(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    /**
     * @return true if the character is an ascii letter, number, or underscore.
     */
    public static boolean isLetterOrUnderscoreOrNumber(char c) {
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


    private Strings() {
    }
}
