package edu.up.isgc.utils;

import edu.up.isgc.tools.RegexHandler;

public final class CoordinatesHandler {
    private CoordinatesHandler() {

    }

    /**
     * The deegresToDecimal method converts a given latitude or longitude in degrees, minutes,
     * and seconds to its respective representation in decimal.
     *
     * @param coordinate It is a String that represents the latitude or longitude
     * @return A float representing the latitude or longitude in decimal format
     */
    public static Float degreesToDecimal(final String coordinate) {
        final int MINUTES_IN_HOUR = 60;
        final int SECONDS_IN_HOUR = 3600;
        if (coordinate != null) {
            float degrees = Float.parseFloat(RegexHandler.matchExpression(coordinate, ".*\\S(?=\\s*deg)"));
            float minutes = Float.parseFloat(RegexHandler.matchExpression(coordinate, "\\d+\\.*\\d*(?=')"));
            float seconds = Float.parseFloat(RegexHandler.matchExpression(coordinate, "\\d+\\.*\\d*(?=\")"));
            float conversion = degrees + minutes / MINUTES_IN_HOUR + seconds / SECONDS_IN_HOUR;
            char direction = RegexHandler.matchExpression(coordinate, "(.)$").charAt(0);
            return (direction == 'S' || direction == 'W') ? -conversion : conversion;
        }
        return null;
    }
}
