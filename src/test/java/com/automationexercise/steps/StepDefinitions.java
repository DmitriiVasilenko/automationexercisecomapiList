package com.automationexercise.steps;

import com.automationexercise.utils.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.automationexercise.utils.JsonStructureValidator;

public class StepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);
    private String endpoint; // Stores the endpoint URL
    private ApiClient.Response response; // Stores the API response
    private JSONObject jsonResponse; // Stores the parsed JSON response

    @Given("User navigates to {string}")
    public void userNavigatesTo(String endpointKey) {
        // Set the endpoint from the scenario
        this.endpoint = endpointKey;

        // Log the endpoint for debugging purposes
        LOGGER.info("Navigating to endpoint: {}", endpoint);
    }

    @When("User checks the response code")
    public void userChecksTheResponseCode() {
        // Send a GET request using ApiClient
        response = ApiClient.sendGetRequest(endpoint);

        // Log the response code for debugging purposes
        LOGGER.info("Response code: {}", response.getStatusCode());
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(Integer expectedResponseCode) {
        // Verify the actual response code matches the expected code using TestNG's Assert
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode.intValue(),
                "The response code does not match the expected value!");

        // Log the success message
        LOGGER.info("Response code verified successfully: {}", response.getStatusCode());
    }

    @When("User checks the contentType")
    public void userChecksTheContentType() {
        // Send a GET request to fetch the response (if not already done)
        if (response == null) {
            response = ApiClient.sendGetRequest(endpoint);
        }

        // Retrieve Content-Type header in case-insensitive manner
        String contentType = response.getHeaders().get("content-type");
        LOGGER.info("Content-Type: {}", contentType);
    }

    @Then("content-type should be {string}")
    public void contentTypeShouldBe(String expectedContentType) {
        // Retrieve Content-Type header in case-insensitive manner
        String actualContentType = response.getHeaders().get("content-type");

        // Verify the content-type matches the expected value
        Assert.assertEquals(actualContentType, expectedContentType,
                "The content-type does not match the expected value!");

        // Log the success message
        LOGGER.info("Content-Type verified successfully: {}", actualContentType);
    }

    @When("User checks markup")
    public void userChecksMarkup() {
        // Send a GET request to fetch the response if not already done
        if (response == null) {
            response = ApiClient.sendGetRequest(endpoint);
        }

        // Parse the response body as JSON
        try {
            jsonResponse = new JSONObject(response.getBody());
            LOGGER.info("Markup parsed successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response body as JSON", e);
        }
    }

    @Then("markup should contain {string}")
    public void markupShouldContain(String fieldName) {
        try {
            if (JsonStructureValidator.fieldExists(jsonResponse, fieldName)) {
                LOGGER.info("Field '{}' found in the markup", fieldName);
            } else {
                Assert.fail("The markup does not contain the field: " + fieldName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while checking for field: " + fieldName, e);
        }
    }



}