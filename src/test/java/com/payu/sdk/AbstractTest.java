package com.payu.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    protected Properties properties = new Properties();
    private InputStream inputStream = AbstractTest.class.getClassLoader().getResourceAsStream("connection.properties");

    protected void prepareEnvironmentProperties() throws IOException {
        properties.load(inputStream);
    }
}
