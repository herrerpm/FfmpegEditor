package edu.up.isgc.utils;

import edu.up.isgc.tools.RegexHandler;

import java.io.File;
import java.util.Objects;

public class ExtensionHandler {
    public static String GetExtension(File file){
        return RegexHandler.matchExpression(file.getName(), "\\..+");
    }

    public static Boolean AllowedExtensions(String extension, String[] allowed_extensions){
        for (String allowed_extension: allowed_extensions) {
            if (Objects.equals(allowed_extension, extension)) {
                return true;
            }
        }
        return false;
    }
}
