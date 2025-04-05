package com.automationexercise.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // New method to validate required keys
    public static void validateEndpoints() {
        validateKey("test.endpoint");
        validateKey("api.endpoint");
    }

    private static void validateKey(String key) {
        String value = getProperty(key);
        if (value == null || value.isEmpty()) {
            LOGGER.error("Required endpoint '{}' is not defined in the configuration file!", key);
            throw new IllegalStateException("Endpoint not found or empty for key: " + key);
        }
    }
}