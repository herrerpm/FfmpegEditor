package edu.up.isgc.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexHandler {
    private RegexHandler() {
    }
    /**
     *  The MathExpression method matches a String with a given regex pattern
     *  and returns either null or the matched string.
     * @param string A string with the pattern expected
     * @param pattern The regex pattern we expect to match
     * @return A string containing only the expected matched pattern
     */
    public static String matchExpression(final String string, final String pattern) {
        Pattern keyPattern = Pattern.compile(pattern);
        Matcher keyMatcher = keyPattern.matcher(string);
        if (keyMatcher.find()) {
            return keyMatcher.group();
        }
        return null;
    }
}
