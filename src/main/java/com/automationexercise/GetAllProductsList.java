package com.automationexercise;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAllProductsList {

    public static void main(String[] args) {
        // Create an instance of GetAllProductsList
        GetAllProductsList getAllProductsList = new GetAllProductsList();

        // Execute the process of validation and requests
        getAllProductsList.execute();
    }

    public void execute() {
        // Logger initialization
        final Logger LOGGER = LoggerFactory.getLogger(GetAllProductsList.class);

        // Validate configuration directly from ConfigLoader
        ConfigLoader.validateEndpoints();

        // Retrieve endpoint from configuration file
        String endpoint = ConfigLoader.getProperty("api.endpoint");

        // Call methods directly using the static methods of ApiClient
        ApiClient.Response response = ApiClient.sendGetRequest(endpoint);

        // Log the received response
        LOGGER.info("Response: {}", response.getBody());
    }
}