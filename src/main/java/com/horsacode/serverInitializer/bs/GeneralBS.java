package com.horsacode.serverInitializer.bs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GeneralBS {

    public static String convertSpaceToDots(String valueWithSpaces){
        return valueWithSpaces.replaceAll(" ", ".");
    }

    public static void copyEarFileToServer(String fromDirectory, String serverDirectory) throws IOException {
        File from = new File(fromDirectory);
        //TODO - Delete if existe server file
        File server = new File(serverDirectory);
        Files.copy(from.toPath(), server.toPath());
    }
}
