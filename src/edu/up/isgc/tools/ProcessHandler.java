package edu.up.isgc.tools;

import edu.up.isgc.utils.ExceptionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public final class ProcessHandler {
    /**
     * The processBuilder variable is the single instance used by the class
     * to execute processes.
     */
    private static final ProcessBuilder processBuilder = new ProcessBuilder();

    private ProcessHandler() {

    }

    /**
     * The execute method takes a command in the form of a String array and returns the
     * BufferedReader of the result.
     * @param command The command parameter is a String array representing the command
     * @return A BufferedReader with the command output
     */
    public static BufferedReader execute(final String[] command) {
        try {
            processBuilder.command(command);
            Process process = processBuilder.start();
            InputStream stream = process.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream);
            process.waitFor();
            return new BufferedReader(streamReader);
        } catch (IOException exception) {
            ExceptionHandler.bufferedException();
        } catch (InterruptedException e) {
            ExceptionHandler.runtimeException();
        }
        return null;
    }
}
