package edu.up.isgc.utils;
import edu.up.isgc.tools.RegexHandler;
import edu.up.isgc.utils.ExtensionHandler;

import java.io.File;

public class Media {
    /**
     * A string that represents the latitude of the image.
     */
    private String latitude;
    /**
     * A string that represents the longitude of the image.
     */
    private String longitude;
    /**
     * A string that represents the date of creation of the
     * image.
     */
    private String date;
    /**
     * A file representing the path of the image.
     */
    private File file;
    /**
     * A string representing the extension of the image.
     */
    private String extension;
    /**
     * A string representing the climate code for the image.
     */
    private String climate;
    /**
     * A filre representing the temporary output of the image.
     */
    private File output;

    /**
     * A getter for the latitude atribute.
     * @return A string representing the latitude in decimal
     */

    public String getLatitude() {
        return latitude;
    }

    /**
     * A setter for the latitude argument.
     * @param latitudeValue A float representing the latitude in decimal
     */

    public void setLatitude(final Float latitudeValue) {
        if (latitudeValue != null) {
            this.latitude = String.valueOf(latitudeValue);
        } else {
            this.latitude = null;
        }
    }

    /**
     * A getter for the longitude atribute.
     * @return A string representing the longitude in decimal
     */

    public String getLongitude() {
        return longitude;
    }
    /**
     * A setter for the longitude argument.
     * @param longitudeValue A float representing the longitude in decimal
     */


    public void setLongitude(final Float longitudeValue) {
        if (longitudeValue != null) {
            this.longitude = String.valueOf(longitudeValue);
        } else {
            this.longitude = null;
        }
    }

    /**
     * A getter for the creation date of the image.
     * @return A string representing the creation date of the image.
     */
    public String getDate() {
        return date;
    }

    /**
     * A setter for the creation dateValue of the image.
     * @param dateValue A string representing the creation dateValue of the image.
     */
    public void setDate(final String dateValue) {
        this.date = dateValue;
    }

    /**
     * The get file is a getter for the File param.
     * @return The respective file
     */
    public File getFile() {
        return file;
    }

    /**
     * The setfile method is a setter for the fileValue attribute.
     * @param fileValue A fileValue to be added
     */
    public void setFile(final File fileValue) {
        this.file = fileValue;
    }

    /**
     * The getExtension is a getter for the Media extension.
     * @return The respective extension for the media
     */
    public String getExtension() {
        return extension;
    }

    /**
     * The setExtension setter sets the extensionValue of a Media.
     * @param extensionValue The extensionValue to be added
     */
    public void setExtension(final String extensionValue) {
        this.extension = extensionValue;
    }

    /**
     * The getClimate method is a getter for the climate.
     * @return The climate icon of the media
     */
    public String getClimate() {
        return climate;
    }

    /**
     * A setter for the climateValue.
     * @param climateValue A string with the icon name for openweather
     */
    public void setClimate(final String climateValue) {
        this.climate = climateValue;
    }

    /**
     * A getter for the output file.
     * @return A file representing the output
     */
    public File getOutput() {
        return output;
    }

    /**
     * A setter for the outputFile file.
     * @param outputFile A File representing the outputFile
     */
    public void setOutput(final File outputFile) {
        this.output = outputFile;
    }

    /**
     * A constructor for the Media object.
     * @param fileDirectoryValue The directory where the image is located
     * @param outputDirectoryValue The expected output location
     */
    public Media(final File fileDirectoryValue, final File outputDirectoryValue) {
        setFile(fileDirectoryValue);
        setExtension(ExtensionHandler.GetExtension(file));
        setOutput(new File(
                outputDirectoryValue, RegexHandler.matchExpression(
                        file.getName(), ".+(?=\\.)") + ".mp4"));
    }

    /**
     * A way to validate if the media has the required data for the video.
     * @return A boolean with the representation fo the image
     */
    public boolean validateMedia() {
        return (getDate() != null & getLongitude() != null & getLatitude() != null);
    }

}
