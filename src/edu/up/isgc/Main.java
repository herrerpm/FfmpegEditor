package edu.up.isgc;
import edu.up.isgc.utils.ConfigurationHandler;
import edu.up.isgc.utils.Media;
import edu.up.isgc.utils.VideoGenerator;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Please change the variable to match where your files are
        final String IMAGE_FOLDER = "./images";
        ConfigurationHandler.LoadProperties();
        ArrayList<Media> mediaList = new ArrayList<>();
        File folder = new File(IMAGE_FOLDER);
        //The folder where temporary images and final video will be stored
        File temporary = new File("./temporary");
        VideoGenerator video = new VideoGenerator(folder, temporary);
        video.generateVideo();
    }
}
