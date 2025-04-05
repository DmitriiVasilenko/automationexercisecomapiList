package com.automationexercise.steps;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;

public class StepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);
    private String endpoint; // Переменная для хранения API-ендпоинта
    private ApiClient.Response lastResponse; // Переменная для хранения ответа API

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
        lastResponse = ApiClient.sendGetRequest(this.actualEndpoint); // FIX: Store response in `lastResponse`

        // Extract and store relevant details from the Response object
        this.actualResponseCode = lastResponse.getStatusCode();
        this.responseContentType = lastResponse.getHeader("Content-Type");

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

    @Then("the scheme should contain {string}")
    public void the_scheme_should_contain(String fieldName) {
        LOGGER.info("Validating that the response scheme contains the field: {}", fieldName);

        // Проверяем, есть ли ответ
        if (lastResponse == null) {
            throw new IllegalStateException("No response available. Did you send the request?");
        }

        // Получаем тело ответа
        String responseBody = lastResponse.getBody();

        try {
            // Парсим JSON-ответ
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Разделяем fieldName на уровни (products.category.usertype.usertype)
            String[] fieldPath = fieldName.split("\\.");
            boolean isFieldFound = isFieldPresentInJson(jsonResponse, fieldPath, 0);

            // Если ключ не найден, бросаем ошибку
            if (!isFieldFound) {
                throw new AssertionError("The field '" + fieldName + "' is missing in the response scheme!");
            }

            LOGGER.info("The field '{}' is present in the response scheme.", fieldName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the response body. Error: " + e.getMessage(), e);
        }
    }

    // Рекурсивная проверка наличия поля в JSON
    private boolean isFieldPresentInJson(Object json, String[] fieldPath, int index) {
        if (index >= fieldPath.length) {
            return true; // Мы достигли последнего уровня пути
        }

        String currentField = fieldPath[index];

        if (json instanceof JSONObject jsonObject) {
            // Если текущий объект JSON содержит нужный ключ, продолжаем проверку
            return jsonObject.has(currentField)
                    && isFieldPresentInJson(jsonObject.get(currentField), fieldPath, index + 1);
        } else if (json instanceof JSONArray jsonArray) {
            // Если это массив, проверяем все элементы массива
            for (int i = 0; i < jsonArray.length(); i++) {
                if (isFieldPresentInJson(jsonArray.get(i), fieldPath, index)) {
                    return true; // Ключ найден в одном из элементов
                }
            }
        }

        return false; // Ключ не найден
    }
}