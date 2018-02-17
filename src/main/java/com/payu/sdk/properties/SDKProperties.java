package com.payu.sdk.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SDKProperties {

    private static Properties properties = new Properties();

    private static final String SDK_PROPERTY_FILE = "sdk.properties";

    static {
        try {
            InputStream inputStream = SDKProperties.class.getClassLoader().getResourceAsStream(SDK_PROPERTY_FILE);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {

        }
    }

    public static String getProperty(PropertyNames propertyName) {
        return properties.getProperty(propertyName.getPropertyName());
    }

    public static String getPropertyByName(String propertyName) {
        return properties.getProperty(propertyName);
    }

}
