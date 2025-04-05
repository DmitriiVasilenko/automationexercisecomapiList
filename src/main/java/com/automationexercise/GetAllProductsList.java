package com.automationexercise;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAllProductsList {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllProductsList.class);

    public static void main(String[] args) {
        // Validate required endpoints
        ConfigLoader.validateEndpoints();

        // Fetch and log endpoints
        String testEndpoint = ConfigLoader.getProperty("test.endpoint");
        String apiEndpoint = ConfigLoader.getProperty("api.endpoint");

        LOGGER.info("Test endpoint: {}", testEndpoint);
        LOGGER.info("API endpoint: {}", apiEndpoint);


        // Send GET requests to both endpoints and log the responses
        sendGetRequest(testEndpoint);
        sendGetRequest(apiEndpoint);
    }

    private static void sendGetRequest(String endpoint) {
        try {
            LOGGER.info("Sending GET request to: {}", endpoint);

            // Call the ApiClient and get the Response object
            ApiClient.Response apiResponse = ApiClient.sendGetRequest(endpoint);

            // Extract the HTTP status code from the Response object
            int responseCode = apiResponse.getStatusCode();

            LOGGER.info("Response code for endpoint '{}': {}", endpoint, responseCode);
        } catch (Exception e) {
            LOGGER.error("Error while sending GET request to {}: {}", endpoint, e);
        }
    }
}