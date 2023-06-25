package edu.up.isgc.tools;
import edu.up.isgc.utils.ExceptionHandler;
import java.io.BufferedReader;
import java.io.IOException;

public final class ExifToolHandler {
    private ExifToolHandler() {
    }

    /**
     * The GetExifTag method allows retrieving a specific exif tag.
     *
     * @param filepath The file path parameter is a string of the file desired
     * @param tag      The tag parameter is a String of the -s tag desired in ExifTool
     * @return A string with the tag value, or null if the value does not exist
     */
    private static String getExifTag(final String filepath, final String tag) {
        try (BufferedReader reader = ProcessHandler.execute(new String[]{"./ExifTool/exiftool", "-" + tag, filepath})) {
            String line = reader.readLine();
            if (line != null) {
                return RegexHandler.matchExpression(line, "(?<=:\\s).*");
            }
        } catch (IOException e) {
            ExceptionHandler.bufferedException();
        }
        return null;
    }

    /**
     * The GetDate method takes the path of a file and returns the GetDate tag.
     *
     * @param path A string representing the file path
     * @return the creation date of the file
     */
    public static String getDate(final String path) {
        return getExifTag(path, "createDate");
    }

    /**
     * The GetLatitude method takes the path of a file and returns the GPSLatitude tag value.
     *
     * @param path A string representing the file path
     * @return the Latitude of the file
     */
    public static String getLatitude(final String path) {
        return getExifTag(path, "GPSLatitude");
    }

    /**
     * The GetLongitude method takes the path of a file and returns the Longitude tag value.
     *
     * @param path A string representing the file path
     * @return the creation Latitude of the file
     */
    public static String getLongitude(final String path) {
        return getExifTag(path, "GPSLongitude");
    }


}
