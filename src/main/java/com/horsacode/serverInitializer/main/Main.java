package com.horsacode.serverInitializer.main;

import com.horsacode.serverInitializer.bs.GeneralBS;
import com.horsacode.serverInitializer.controller.InitPropertiesValues;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Object[] initPossibleValues = {1, 2};
        String messageInitMenu = "Options: \n 1.- Initialize server \n 2.- Add server";

        try {
            Object selectedValue = JOptionPane.showInputDialog(null,
                    messageInitMenu, "Input",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    initPossibleValues, initPossibleValues[0]);

            //TODO - Evaluar el uso de H2 para guardar los datos, en lugar de un properties.
            InitPropertiesValues initPropertiesValues = new InitPropertiesValues();
            if (selectedValue instanceof Integer) {
                // Si es uno cargamos el menu del servidor
                if ((Integer) selectedValue == 1) {
                    String serverName = loadMenuExec(initPropertiesValues);
                    String fileToCopyPath = initPropertiesValues.getProperty(GeneralBS.convertSpaceToDots(serverName), InitPropertiesValues.SERVER_VALUES_FILE);
                    String fileFromPath = initPropertiesValues.getProperty("application.server", InitPropertiesValues.SERVER_VALUES_FILE);
                    GeneralBS.copyEarFileToServer(fileToCopyPath, fileFromPath);
                    //Ejecutar servidor
                    initServer(initPropertiesValues);
                    //verificar si la configuración tiene guardado un archivo de logs
                    //Abrir terminal con tail de los logs
                }
                // Si es dos cargamos el menu para crear un nuevo server
            } else {
                System.out.println("No se que pets");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadMenuExec(InitPropertiesValues initPropertiesValues) throws IOException {
        String messageExecMenu = "Select server to exec: ";
        Object[] arrayValues = initPropertiesValues.getListExecValues();
        Object selectedValue = JOptionPane.showInputDialog(null,
                messageExecMenu, "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                arrayValues, arrayValues[0]);

        return String.valueOf(selectedValue);
    }

    public static Integer initServer(InitPropertiesValues initPropertiesValues) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String pathInit = initPropertiesValues.getProperty("pathInit.server", InitPropertiesValues.SERVER_VALUES_FILE);
        String command = initPropertiesValues.getProperty("scriptInit.server", InitPropertiesValues.SERVER_VALUES_FILE);

        processBuilder.command(pathInit);

        int exitCode = -2;
        try {

            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode;
    }
}
