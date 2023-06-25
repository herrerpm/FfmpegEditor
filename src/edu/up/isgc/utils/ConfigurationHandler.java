package edu.up.isgc.utils;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationHandler {
    public static Properties properties;
    public static void LoadProperties() {
        properties = new Properties();
        try {
            FileInputStream stream = new FileInputStream("settings.properties");
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getOpenaiKey(){
        return properties.getProperty("openai.key");
    }

    public static String getOpenWeatherKey(){
        return properties.getProperty("openweather.key");
    }
    public static String getMapQuestKey(){
        return properties.getProperty("mapquest.key");
    }
}
