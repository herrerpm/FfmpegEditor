package edu.up.isgc.utils;

import edu.up.isgc.tools.ExifToolHandler;
import edu.up.isgc.tools.FfmpegHandler;
import edu.up.isgc.tools.MicroServiceCaller;
import edu.up.isgc.tools.ProcessHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class VideoGenerator {
    /**
     * A file representing the folder for the input images.
     */
    private File inputDirectory;
    /**
     *  A file representing where the temporary files will be stored.
     */
    private File temporaryDirectory;
    /**
     * An array with media objects representing the order of the video.
     */
    private final ArrayList<Media> mediaList = new ArrayList<>();

    /**
     * A getter for the input directory.
     * @return A file representing the input directory
     */
    public File getInputDirectory() {
        return inputDirectory;
    }

    /**
     * A setter for the input directory.
     * @param inputDirectoryFile A file representing the input directory.
     */
    public void setInputDirectory(final File inputDirectoryFile) {
        this.inputDirectory = inputDirectoryFile;
    }

    /**
     * A getter for the temporary directory.
     * @return A file representing the temporary directory
     */

    public File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * A setter for the temporary directory.
     * @param temporaryDirectoryFile A file representing the temporary directory
     */
    public void setTemporaryDirectory(final File temporaryDirectoryFile) {
        this.temporaryDirectory = temporaryDirectoryFile;
    }

    /**
     * A constructor for the final video.
     * @param inputDirectoryFile A file representing the input directory
     * @param temporaryDirectoryFile A file representing the output directory
     */
    public VideoGenerator(final File inputDirectoryFile, final File temporaryDirectoryFile) {
        setInputDirectory(inputDirectoryFile);
        setTemporaryDirectory(temporaryDirectoryFile);
        setMediaList();
    }

    /**
     * The concatenate video method joins all the videos in the expected order.
     * @param directory A directory representing where are the temporary files located
     */
    public static void concatenateVideo(final File directory) {
        ProcessHandler.execute(new String[] {
                        "./ffmpeg/ffmpeg",
                        "-f",
                        "concat",
                        "-safe",
                        "0",
                        "-i",
                        new File(directory, "input.txt").getAbsolutePath(),
                        "-c", "copy", new File(directory, "output.mp4").getAbsolutePath()
                }
        );
    }

    /**
     * A method for listing all the files in a .txt for the concatenation of the video.
     * @param directory The directory where the .txt will be stored
     * @param mediaList A list of the videos to concatenate
     */

    public static void listFiles(final File directory, final ArrayList<Media> mediaList) {
        File videoList = new File(directory, "input.txt");
        try {
            if (videoList.createNewFile()) {
                FileWriter writer = new FileWriter(videoList);
                for (Media media: mediaList) {
                    writer.write( "file '" + media.getOutput().getAbsolutePath() + "'\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The global method to generate the video with the requirements provided.
     */
    public void generateVideo() {
        System.out.println("Welcome, please wait while media is added");
        editMedia();
        System.out.println("Media added");
        if (mediaList.size() > 2) {
            System.out.println("Creating cover");
            generateCover();
            System.out.println("Cover created");
            System.out.println("Creating Map");
            generateMap();
            System.out.println("Map generated");
            System.out.println("Listing files");
            listFiles(this.temporaryDirectory, mediaList);
            System.out.println("Files listed");
            System.out.println("Joining video");
            concatenateVideo(this.temporaryDirectory);
            System.out.println("Video generated");
        } else {
            System.out.println("\nThere are not enough multimedia files to make the video\n" +
                    "please add more files, or correct the files that do not have the\n" +
                    "required metadata or are not supported formats");
        }

    }

    /**
     * A setter for the temporary folder.
     */
    public void setMediaList() {
        if (temporaryDirectory.exists()) {
            ProcessHandler.execute(new String[]{
                    "find",
                    "./temporary",
                    "-mindepth",
                    "1",
                    "-delete"
            });
        }  else {
            temporaryDirectory.mkdir();
        }
        for (File file : getInputDirectory().listFiles()) {
            Media media = new Media(file, this.temporaryDirectory);
            media.setDate(ExifToolHandler.getDate(media.getFile().getAbsolutePath()));
            media.setLatitude(CoordinatesHandler.degreesToDecimal(ExifToolHandler.getLatitude(file.getAbsolutePath())));
            media.setLongitude(CoordinatesHandler.degreesToDecimal(ExifToolHandler.getLongitude(file.getAbsolutePath())));
            if (media.validateMedia()) {
                media.setClimate(
                    MicroServiceCaller.getWeather(
                    media.getLatitude(), media.getLongitude(), edu.up.isgc.utils.ConfigurationHandler.getOpenWeatherKey()
                ));
                this.mediaList.add(media);
            }  else {
                System.out.printf(
                    "\nMedia: %s is invalid, please check that it has a valid createDate, GPSLatitude and GPSLongitude\n" +
                    "atributes, make sure to pass the image through means that do not strip the image of this tags\n" +
                    "the resulting video will not be included ",
                    media.getFile().getName()
                );
            }
            this.mediaList.sort(new MediaSorter());
        }
    }

    /**
     * A method for generating the video cover.
     */
    public void generateCover() {
        File cover = new File(this.getTemporaryDirectory(), "cover.mp4");
        FfmpegHandler.editCover(MicroServiceCaller.generateOpenAiImage(), cover);
        this.mediaList.add(0, new Media(
                new File(this.getInputDirectory(), "cover.mp4"), this.getTemporaryDirectory()));
    }

    /**
     *  A method for generating the final map.
     */
    public void generateMap() {
        Media firstLocation = this.mediaList.get(0);
        Media lastLocation = this.mediaList.get(1);
        FfmpegHandler.getMap(
                MicroServiceCaller.getMap(firstLocation, lastLocation), new File(this.getTemporaryDirectory(), "map.mp4"));
        Media media = new Media(
                new File(this.getInputDirectory(), "map.mp4"), this.getTemporaryDirectory());
        this.mediaList.add(this.mediaList.size(), media);
    }

    /**
     * A method for editing the files, adding the climate and phrases required.
     */
    public void editMedia() {
        for (int i = 0; i < mediaList.size(); i++) {
            Media media = mediaList.get(i);
            boolean isImage = (edu.up.isgc.utils.ExtensionHandler.AllowedExtensions(media.getExtension(), new String[] {".jpg", ".png"}));
            boolean isVideo = (edu.up.isgc.utils.ExtensionHandler.AllowedExtensions(media.getExtension(), new String[] {".mov", ".mp4"}));
            if (isImage && i != mediaList.size() - 1) {
                FfmpegHandler.modifyWeatherImage(media);
            } else if (isVideo && i != mediaList.size() - 1) {
                FfmpegHandler.modifyWeatherVideo(media);
            } else if (isImage || isVideo && i == mediaList.size()-1) {
                if (isVideo) {
                    FfmpegHandler.phrasedVideo(mediaList.get(mediaList.size() - 1),
                            MicroServiceCaller.generateAiPhrase(
                                    MicroServiceCaller.GetCity(media.getLatitude(), media.getLongitude())
                            ));
                }  else {
                    FfmpegHandler.phrasedImage(mediaList.get(mediaList.size() - 1),
                            MicroServiceCaller.generateAiPhrase(
                                    MicroServiceCaller.GetCity(media.getLatitude(), media.getLongitude())
                            )
                            );
                }
            } else {
                System.out.println("File not supported: " + media.getFile().getName());
            }
        }
    }


}
