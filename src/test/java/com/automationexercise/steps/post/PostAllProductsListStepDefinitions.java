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
    private String endpoint; // Stores the endpoint URL
    private ApiClient.Response response; // Stores the API response

    @Given("User sends POST request to {string}")
    public void userSendsPostRequestTo(String endpointKey) {
        // Логируем полученное значение endpointKey
        LOGGER.info("Received endpointKey: {}", endpointKey);

        // Проверяем корректность переданного URL
        if (endpointKey == null || endpointKey.isBlank()) {
            LOGGER.error("Invalid endpoint key: null or blank.");
            throw new IllegalArgumentException("Endpoint key cannot be null or blank.");
        }

        // Устанавливаем значение endpoint
        this.endpoint = endpointKey;

        // Отправляем POST-запрос
        try {
            response = ApiClient.sendPostRequest(endpoint, null); // Отправка без тела запроса
            LOGGER.info("POST request to {} returned status code: {}", endpoint, response.getStatusCode());
        } catch (Exception e) {
            LOGGER.error("Failed to send POST request to {}: {}", endpoint, e.getMessage());
            throw new RuntimeException("POST request failed.", e);
        }
    }

    @When("User checks the response code of post")
    public void userChecksTheResponseCodeOfPost() {
        // Проверяем наличие ответа
        if (response == null) {
            LOGGER.error("Response is null. Ensure a POST request was sent.");
            throw new IllegalStateException("No response available to check.");
        }

        // Логируем код ответа
        LOGGER.info("Response code of POST request: {}", response.getStatusCode());
    }

    @Then("the response code of post should be {int}")
    public void theResponseCodeOfPostShouldBe(Integer expectedResponseCode) {
        // Проверяем код ответа на соответствие ожидаемому значению
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode.intValue(),
                "The response code does not match the expected value!");

        // Логируем успешное совпадение кода
        LOGGER.info("POST response code verified successfully: {}", response.getStatusCode());
    }
}