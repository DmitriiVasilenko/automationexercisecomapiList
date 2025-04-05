package com.automationexercise.steps;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


public class StepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    private String actualEndpoint;
    private int actualResponseCode;
    private String responseContentType;

    @Given("the API endpoint is {string}")
    public void theApiEndpointIs(String endpointKey) {
        LOGGER.debug("Loading the API endpoint for key: {}", endpointKey);
        this.actualEndpoint = ConfigLoader.getProperty(endpointKey);
        if (this.actualEndpoint == null || this.actualEndpoint.isEmpty()) {
            throw new IllegalStateException("Endpoint not found for key: " + endpointKey);
        }
        LOGGER.info("Loaded endpoint: {}", this.actualEndpoint);
    }

    @When("I send a GET request to the API")
    public void iSendAGetRequestToTheApi() {
        LOGGER.info("Sending GET request to: {}", this.actualEndpoint);

        // Send GET request using ApiClient and retrieve detailed Response
        ApiClient.Response apiResponse = ApiClient.sendGetRequest(this.actualEndpoint);

        // Extract and store relevant details from the Response object
        this.actualResponseCode = apiResponse.getStatusCode();
        this.responseContentType = apiResponse.getHeader("Content-Type");

        LOGGER.info("Received Response - StatusCode: {}, Content-Type: {}",
                this.actualResponseCode, this.responseContentType);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expectedResponseCode) {
        LOGGER.info("Validating response code - Expected: {}, Actual: {}",
                expectedResponseCode, this.actualResponseCode);
        Assert.assertEquals(this.actualResponseCode, expectedResponseCode,
                "Expected and actual response codes do not match!");
    }

    @Then("content-type should be {string}")
    public void contentTypeShouldBe(String expectedContentType) {
        LOGGER.info("Validating response content-type - Expected: {}, Actual: {}",
                expectedContentType, this.responseContentType);
        Assert.assertEquals(this.responseContentType, expectedContentType,
                "The response content-type does not match!");
    }
}