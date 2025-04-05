package com.automationexercise;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAllProductsList {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllProductsList.class);
    private final ApiClient apiClient;

    public GetAllProductsList(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public static void main(String[] args) {
        // Создать экземпляр GetAllProductsList с ApiClient
        GetAllProductsList getAllProductsList = new GetAllProductsList(new ApiClient());

        // Выполнить процесс проверки и запросов
        getAllProductsList.execute();
    }

    public void execute() {
        // Проверка конфигурации
        validateConfiguration();

        // Получение и логирование эндпоинтов
        String testEndpoint = ConfigLoader.getProperty("test.endpoint");
        String apiEndpoint = ConfigLoader.getProperty("api.endpoint");

        logEndpoints(testEndpoint, apiEndpoint);

        // Отправка GET-запросов и логирование результата
        sendRequestToEndpoint(testEndpoint);
        sendRequestToEndpoint(apiEndpoint);
    }

    private void validateConfiguration() {
        LOGGER.info("Validating configuration...");
        ConfigLoader.validateEndpoints();
    }

    private void logEndpoints(String testEndpoint, String apiEndpoint) {
        LOGGER.info("Test endpoint: {}", testEndpoint);
        LOGGER.info("API endpoint: {}", apiEndpoint);
    }

    private void sendRequestToEndpoint(String endpoint) {
        try {
            LOGGER.info("Sending GET request to: {}", endpoint);
            ApiClient.Response response = apiClient.sendGetRequest(endpoint);
            LOGGER.info("Response code for endpoint '{}': {}", endpoint, response.getStatusCode());
        } catch (Exception e) {
            LOGGER.error("Error while sending GET request to {}: {}", endpoint, e.getMessage(), e);
        }
    }
}