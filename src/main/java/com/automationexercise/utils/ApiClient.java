package com.automationexercise.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiClient {

    public static class Response {
        private final int statusCode;
        private final Map<String, String> headers;
        private final String body;

        public Response(int statusCode, Map<String, String> headers, String body) {
            this.statusCode = statusCode;
            this.headers = headers;
            this.body = body;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getHeader(String name) {
            return headers.get(name);
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getBody() {
            return body;
        }
    }

    public static Response sendGetRequest(String endpoint) {
        try {
            URL url = URI.create(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Reading the response body
            String responseBody;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                responseBody = reader.lines().collect(Collectors.joining("\n"));
            }

            int responseCode = connection.getResponseCode();
            Map<String, String> responseHeaders = new HashMap<>();

            // Extracting response headers
            connection.getHeaderFields().forEach((key, value) -> {
                if (key != null && !value.isEmpty()) {
                    responseHeaders.put(key, String.join(", ", value));
                }
            });

            connection.disconnect();

            // Pass responseBody as the third parameter
            return new Response(responseCode, responseHeaders, responseBody);

        } catch (IOException e) {
            throw new RuntimeException("Error while sending GET request to " + endpoint, e);
        }
    }
}