package com.automationexercise;

import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostToAllProductsList {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostToAllProductsList.class);

    public static void main(String[] args) {
        // Загружаем URL из конфигурации
        String endpoint = ConfigLoader.getProperty("api.endpoint");

        // Формируем тело POST-запроса (если нужно)
        String requestBody = "{\"exampleKey\":\"exampleValue\"}";

        // Отправляем POST-запрос и логируем результат
        ApiClient.Response response = ApiClient.sendPostRequest(endpoint, requestBody);
        LOGGER.info("Response: {}", response.getBody());
    }
}