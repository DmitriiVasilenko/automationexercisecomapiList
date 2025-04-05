package com.automationexercise.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    public static class Response {
        private final int statusCode;
        private final Map<String, String> headers;

        public Response(int statusCode, Map<String, String> headers) {
            this.statusCode = statusCode;
            this.headers = headers;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getHeader(String name) {
            return headers.get(name);
        }
    }

    public static Response sendGetRequest(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            Map<String, String> responseHeaders = new HashMap<>();

            // Extract response headers
            connection.getHeaderFields().forEach((key, value) -> {
                if (key != null && !value.isEmpty()) {
                    responseHeaders.put(key, String.join(", ", value));
                }
            });

            connection.disconnect();

            return new Response(responseCode, responseHeaders);
        } catch (IOException e) {
            throw new RuntimeException("Error while sending GET request to: " + endpoint, e);
        }
    }
}