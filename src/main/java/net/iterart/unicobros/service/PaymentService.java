package net.iterart.unicobros.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.iterart.unicobros.config.UnicobrosConfig;
import net.iterart.unicobros.dto.PaymentIntentRequest;
import net.iterart.unicobros.dto.PaymentIntentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final UnicobrosConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PaymentService(UnicobrosConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest paymentRequest) {
        try {
            // Convert the payment request object to JSON
            String jsonData = objectMapper.writeValueAsString(paymentRequest);
            
            logger.debug("Creating payment intent with data: {}", jsonData);

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .uri(URI.create(config.getApiUrl() + "/p/intent"))
                    .setHeader("x-api-key", config.getApiKey())
                    .setHeader("x-lang", "es")
                    .setHeader("x-access-token", config.getAccessToken())
                    .setHeader("Content-Type", "application/json")
                    .build();

            // Send the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Payment intent response - Status: {}, Body: {}", response.statusCode(), response.body());

            // Create and return the response
            boolean success = response.statusCode() >= 200 && response.statusCode() < 300;
            String message = success ? "Payment intent created successfully" : "Failed to create payment intent";

            return new PaymentIntentResponse(
                    response.statusCode(),
                    response.body(),
                    success,
                    message
            );

        } catch (Exception e) {
            logger.error("Error creating payment intent", e);
            return new PaymentIntentResponse(
                    500,
                    null,
                    false,
                    "Error: " + e.getMessage()
            );
        }
    }
}
