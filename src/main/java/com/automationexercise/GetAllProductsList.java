package com.automationexercise;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAllProductsList {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllProductsList.class);

    public static void main(String[] args) {
        // Create an instance of GetAllProductsList without ApiClient
        GetAllProductsList getAllProductsList = new GetAllProductsList();

        // Execute the process of validation and requests
        getAllProductsList.execute();
    }

    public void execute() {
        // Logger initialization
        final Logger LOGGER = LoggerFactory.getLogger(GetAllProductsList.class);

        // Validate configuration directly from ConfigLoader
        ConfigLoader.validateEndpoints();

        // Call methods directly using the static methods of ApiClient
        ApiClient.Response response = ApiClient.sendGetRequest("your_endpoint");

        // Log the received response
        LOGGER.info("Response: {}", response.getBody());
    }
}