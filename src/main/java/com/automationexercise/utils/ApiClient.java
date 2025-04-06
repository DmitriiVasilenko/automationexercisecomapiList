package com.automationexercise.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient(); // Single instance of the HTTP client

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

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getBody() {
            return body;
        }
    }

    public static Response sendRequest(String endpoint, HttpRequest.Builder requestBuilder) {
        if (endpoint == null || endpoint.isBlank()) {
            throw new IllegalArgumentException("Endpoint URL cannot be null or blank.");
        }
        try {
            HttpRequest request = requestBuilder.uri(URI.create(endpoint)).build();
            HttpResponse<String> httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // Transform headers to a simplified Map<String, String>
            Map<String, String> simplifiedHeaders = httpResponse.headers().map().entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().toLowerCase(), // Case-insensitive keys
                            entry -> String.join(", ", entry.getValue()) // Join all values
                    ));

            return new Response(
                    httpResponse.statusCode(),
                    simplifiedHeaders,
                    httpResponse.body()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error while sending request to " + endpoint, e);
        }
    }

    public static Response sendGetRequest(String endpoint) {
        return sendRequest(endpoint, HttpRequest.newBuilder().GET());
    }

    public static Response sendPostRequest(String endpoint, String requestBody) {
        return sendRequest(endpoint, HttpRequest.newBuilder()
                .POST(requestBody == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json"));
    }

}