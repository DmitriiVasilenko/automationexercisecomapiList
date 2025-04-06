package com.automationexercise.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Properties properties = new Properties();

    // Constants for configuration file and keys
    private static final String CONFIG_FILE = "application.properties";
    private static final String API_ENDPOINT_KEY = "api.endpoint";

    // Static block to load the configuration file
    static {
        loadProperties();
    }

    // Loading properties from the configuration file
    private static void loadProperties() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file '" + CONFIG_FILE + "' not found!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    // Retrieve the value of a property by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Method to validate the presence of the 'api.endpoint' key
    public static void validateEndpoints() {
        String value = getProperty(API_ENDPOINT_KEY);
        if (value == null || value.isEmpty()) {
            LOGGER.error("Required endpoint '{}' is not defined in the configuration file!", API_ENDPOINT_KEY);
            throw new IllegalStateException("Endpoint not found or empty for key: " + API_ENDPOINT_KEY);
        }
    }
}