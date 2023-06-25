package edu.up.isgc.tools;

import edu.up.isgc.utils.ExceptionHandler;
import edu.up.isgc.utils.Media;
import edu.up.isgc.utils.ConfigurationHandler;
import java.io.BufferedReader;
import java.io.IOException;

public final class MicroServiceCaller {
    private MicroServiceCaller() {
    }
    /**
     * The GetWeather method returns the weather icon of a given location given its latitude and longitude, it returns
     * the code for an openweather icon.
     * @param lat A String representing the latitude
     * @param lon A String representing the longitude
     * @param apiKey A String representing the apiKey
     * @return A String with the code for the openweather icon that describes the weather at the current time
     */
    public static String getWeather(final String lat, final String lon, final String apiKey) {
        try {
            BufferedReader reader = ProcessHandler.execute(new String[]{"curl", String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s", lat, lon, apiKey)
            });
            String line;
            if ((line = reader.readLine()) != null) {
                return RegexHandler.matchExpression(line, "(?<=\"icon\":\")(?:(?!\").)*");
            }
        } catch (IOException e) {
            ExceptionHandler.bufferedException();
        }
        return null;
    }

    /**
     * The getCity method takes a latitude and longitude and returns the expected city.
     * @param lat a String representing the latitude
     * @param lon a String representing the longitude
     * @return The name of the city
     */
    public static String GetCity(final String lat, final String lon) {
        try {
            BufferedReader reader = ProcessHandler.execute(new String[]{"curl", String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
                    lat, lon, ConfigurationHandler.getOpenWeatherKey())
            });
            String line, city;
            if ((line = reader.readLine()) != null) {
                city = RegexHandler.matchExpression(line, "(?<=\"name\":\")(?:(?!\").)*");
                if (!city.equals("")) {
                    return city;
                } else {
                    return "A city with " + RegexHandler.matchExpression(
                            line, "(?<=\"description\":\")(?:(?!\").)*");
                }
            }
        } catch (IOException e) {
            ExceptionHandler.bufferedException();
        }
        return null;
    }

    /**
     *  The getMap method takes two locations as inputs and returns a map representing the route.
     * @param firstLocation A Media object representing the first location
     * @param lastLocation A media object representing the last location
     * @return A
     */
    public static String getMap(final Media firstLocation, final Media lastLocation) {
        String mapUrl = String.format("https://www.mapquestapi.com/staticmap/v5/map?start=%s, %s&end=%s, %s&routeArc=true&size=1080,1920&key=%s",
                firstLocation.getLatitude(),
                firstLocation.getLongitude(),
                lastLocation.getLatitude(),
                lastLocation.getLongitude(),
                ConfigurationHandler.getMapQuestKey()
        );
        return mapUrl;
    }

    /**
     * The generateAiPhrase method generates a phrase with a given input.
     * @param place A media representing the place visited
     * @return A string representing the place for the phrase prompt
     */

    public static String generateAiPhrase(final String place) {
        String[] command = {
                "curl",
                "https://api.openai.com/v1/completions",
                "-H",
                "Content-Type: application/json",
                "-H",
                "Authorization: Bearer " + ConfigurationHandler.getOpenaiKey(),
                "-d",
                "{ \"model\": \"text-davinci-003\", \"prompt\":" +
                        " \"Return an inspirational phrase related to " + place +
                         " try to fit it exactly in 30 characters\", \"max_tokens\": 30, \"temperature\": 0.5 }"
        };
        try {
            BufferedReader reader = ProcessHandler.execute(command);
            String line;
            if ((line = reader.readLine()) != null) {
                return RegexHandler.matchExpression(line, "(?<=\"text\":\"\\\\n\\\\n\\\\\")(?:(?!\\\\\").)*");
            }
        } catch (IOException e) {
            ExceptionHandler.bufferedException();
        }
        return null;
    }

    /**
     * The method generateOpenAiImage returns an url representing the dalle-2 image.
     * @return A url for retrieving the image
     */
    public static String generateOpenAiImage() {
        String[] command = new String[]{
                "curl",
                "https://api.openai.com/v1/images/generations",
                "-H",
                "Content-Type: application/json",
                "-H",
                "Authorization: Bearer " + ConfigurationHandler.getOpenaiKey(),
                "-d",
                "{ \"prompt\": \"An image representing traveling\", \"n\": 1, \"size\": \"1024x1024\" }"
        };
        try {
            BufferedReader reader = ProcessHandler.execute(command);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(":")[0].equals("      \"url\"")) {
                   return RegexHandler.matchExpression(line, "(?<=\"url\": \")(?:(?!\").)*");
                }
            }
        } catch (IOException e) {
            ExceptionHandler.bufferedException();
        }
        return null;
    }
}
