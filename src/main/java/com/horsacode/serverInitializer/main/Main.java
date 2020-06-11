package com.horsacode.serverInitializer.main;

import com.horsacode.serverInitializer.bs.GeneralBS;
import com.horsacode.serverInitializer.controller.InitPropertiesValues;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Object[] initPossibleValues = {1, 2};
        String messageInitMenu = "Options: \n 1.- Initialize server \n 2.- Add server";

        try {
            Object selectedValue = JOptionPane.showInputDialog(null,
                    messageInitMenu, "Input",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    initPossibleValues, initPossibleValues[0]);

            InitPropertiesValues initPropertiesValues = new InitPropertiesValues();
            if (selectedValue instanceof Integer) {
                // Si es uno cargamos el menu del servidor
                if((Integer) selectedValue == 1){
                    String serverName = loadMenuExec(initPropertiesValues);
                    String fileToCopyPath = initPropertiesValues.getProperty(GeneralBS.convertSpaceToDots(serverName), InitPropertiesValues.SERVER_VALUES_FILE);
                    String fileFromPath = initPropertiesValues.getProperty("application.server", InitPropertiesValues.SERVER_VALUES_FILE);
                    GeneralBS.copyEarFileToServer(fileToCopyPath, fileFromPath);
                    //Ejecutar servidor
                    //Abrir terminal con tail de los logs
                }
                // Si es dos cargamos el menu para crear un nuevo server
            }else {
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
}
