package edu.up.isgc.tools;
import edu.up.isgc.utils.Media;
import java.io.File;

public final class FfmpegHandler {
    private FfmpegHandler() {
    }

    /**
     * The modifyWeatherImage method takes an image and adds its climate while ensuring
     * its aspect ratio and resolution remain in portrait mode, the method takes a Media
     * object and applies the ffmpeg command with a ProcessHandler, and the file is then
     * sent to the temporary directory.
     *
     * @param image A Media Object representing the image to edit
     */
    public static void modifyWeatherImage(final Media image) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-loop", "1",
                "-i", image.getFile().getAbsolutePath(),
                "-i", "https://openweathermap.org/img/wn/" + image.getClimate() + ".png",
                "-filter_complex", "[1:v]scale=200:200[ow];[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps];[v_fps][ow]overlay=0:0",
                "-t", "5",
                image.getOutput().getAbsolutePath()
        };
        ProcessHandler.execute(command);
    }

    /**
     * The modifyWeatherVideo method edits a Video file to add the climate and ensure
     * its resolution and frames are correct. It applies the respective ffmpeg command
     * and saves the output on the temporary directory assigned.
     *
     * @param video a Media object that represents the video
     */
    public static void modifyWeatherVideo(final Media video) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-i", video.getFile().getAbsolutePath(),
                "-i", "https://openweathermap.org/img/wn/" + video.getClimate() + ".png",
                "-filter_complex",
                "[1:v]scale=200:200[ow];[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps];[v_fps][ow]overlay=0:0",
                video.getOutput().getAbsolutePath()
        };
        ProcessHandler.execute(command);
    }

    /**
     * The editCover method creates a cover for the resulting video using the link
     * of the image and an output file for where it will be stored.
     *
     * @param url  A string representing the url for the image
     * @param file A file representing the location of the image
     */
    public static void editCover(final String url, final File file) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-loop", "1",
                "-i", url,
                "-filter_complex", "[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps]",
                "-map", "[v_fps]",
                "-t", "5",
                file.getAbsolutePath()
        };
        ProcessHandler.execute(command);
    }

    /**
     * The phrasedImage method returns executes a ffmpeg command that generates an image with
     * an openAi phrase.
     * @param image The media image that the phrase will be on
     * @param phrase The phrase to be added in the image
     */
    public static void phrasedImage(final Media image, final String phrase) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-loop", "1",
                "-i", image.getFile().getAbsolutePath(),
                "-i", "https://openweathermap.org/img/wn/" + image.getClimate() + ".png",
                "-filter_complex", "[1:v]scale=200:200[ow];[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps];[v_fps][ow]overlay=0:0,drawtext=text='" + phrase + "':fontfile=./Roboto-Regular.ttf:x=(w-tw)/2:y=h-th-50:fontsize=50:fontcolor=white",
                "-t", "5",
                image.getOutput().getAbsolutePath()
        };
        ProcessHandler.execute(command);
    }

    /**
     * The method phrasedVideo method takes a video as an input and adds a phrase to it.
     * @param video The Media object representing the video
     * @param phrase The string to be added in the video
     */
    public static void phrasedVideo(final Media video, final String phrase) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-i", video.getFile().getAbsolutePath(),
                "-i", "https://openweathermap.org/img/wn/" + video.getClimate() + ".png",
                "-filter_complex", "[1:v]scale=200:200[ow];[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps];[v_fps][ow]overlay=0:0,drawtext=text='" + phrase + "':fontfile=./Roboto-Regular.ttf:x=(w-tw)/2:y=h-th-50:fontsize=45:fontcolor=white",
                video.getOutput().getAbsolutePath()
        };
        System.out.println(String.join(" ", command));
        ProcessHandler.execute(command);
    }


    /**
     *  The getMap method generates a map from a link connected to the mapquest api.
     * @param url The url representing the map
     * @param file The Media object representing the file
     */
    public static void getMap(final String url, final File file) {
        String[] command = {
                "./ffmpeg/ffmpeg",
                "-loop", "1",
                "-i", url,
                "-filter_complex", "[0:v]scale=1024:1920:force_original_aspect_ratio=increase,crop=1024:1920[v];[v]fps=30[v_fps]",
                "-map", "[v_fps]",
                "-t", "5",
                file.getAbsolutePath()
        };
        ProcessHandler.execute(command);
    }
}
