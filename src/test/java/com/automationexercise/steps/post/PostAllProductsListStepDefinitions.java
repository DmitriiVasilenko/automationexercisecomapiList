package com.automationexercise.steps.post;

import com.automationexercise.utils.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class PostAllProductsListStepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostAllProductsListStepDefinitions.class);
    private ApiClient.Response response; // Field retained to share data between steps

    @Given("User sends POST request to {string}")
    public void userSendsPostRequestTo(String endpointKey) {
        // Log the received endpointKey
        LOGGER.info("Received endpointKey: {}", endpointKey);

        // Validate the provided URL
        if (endpointKey == null || endpointKey.isBlank()) {
            LOGGER.error("Invalid endpoint key: null or blank.");
            throw new IllegalArgumentException("Endpoint key cannot be null or blank.");
        }

        // Use a local variable for the endpoint
        String endpoint = endpointKey;

        // Send the POST request
        try {
            response = ApiClient.sendPostRequest(endpoint, null); // Save response for subsequent steps
            LOGGER.info("POST request to {} returned status code: {}", endpoint, response.getStatusCode());
        } catch (Exception e) {
            LOGGER.error("Failed to send POST request to {}: {}", endpoint, e.getMessage());
            throw new RuntimeException("POST request failed.", e);
        }
    }

    @When("User checks the response code of post")
    public void userChecksTheResponseCodeOfPost() {
        // Verify that a response is available
        if (response == null) {
            LOGGER.error("Response is null. Ensure a POST request was sent.");
            throw new IllegalStateException("No response available to check.");
        }

        // Log the response code
        LOGGER.info("Response code of POST request: {}", response.getStatusCode());
    }

    @Then("the response code of post should be {int}")
    public void theResponseCodeOfPostShouldBe(Integer expectedResponseCode) {
        // Compare the actual response code with the expected value
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode.intValue(),
                "The response code does not match the expected value!");

        // Log successful code match
        LOGGER.info("POST response code verified successfully: {}", response.getStatusCode());
    }
}