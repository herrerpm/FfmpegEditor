package edu.up.isgc.utils;

public final class ExceptionHandler {
    private ExceptionHandler() {
    }

    /**
     *  The bufferedException is a way of handling the BufferedException by returning the
     *  user data about the error.
     */
    public static void bufferedException() {
        System.out.println(
                "An error occurred while trying to read the file, please make sure that the \n" +
                "file exists and that it is accesible. Review if you have the necessary \n" +
                "permissions and try to restart the program."
        );
        System.exit(1);
    }

    /**
     * A method for handling runtimeExceptions.
     */
    public static void runtimeException() {
        System.out.println(
                "An unexpected error occurred during the execution of the program, \n" +
                "please verify that the correct metadata was added, and that enough \n" +
                "memory was assigned and try to restart the program."
        );
        System.exit(1);
    }

}
