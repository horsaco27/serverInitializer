package com.horsacode.serverInitializer.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class InitPropertiesValues {

    public static final String EXEC_VALUES_FILE = "config/exec_values.properties";
    public static final String SERVER_VALUES_FILE = "config/server_values.properties";

    private InputStream loadFile(String fileName) throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null)
            return inputStream;
        else
            throw new FileNotFoundException("property file " + fileName + " not found in the classpath");
    }

    public Object[] getListExecValues() throws IOException {

        Properties properties = new Properties();
        InputStream inputStream = loadFile(InitPropertiesValues.EXEC_VALUES_FILE);
        properties.load(inputStream);
        List<String> listValues = new ArrayList<>();
        properties.stringPropertyNames().forEach(property -> listValues.add(getProperty(property, InitPropertiesValues.EXEC_VALUES_FILE)));
        inputStream.close();
        return listValues.toArray();
    }

    public String getProperty(String propertyName, String fileName) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = loadFile(fileName);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyName);
    }
}
