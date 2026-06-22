package com.cibertec.payment_service.service;

import com.cibertec.payment_service.config.PaypalConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Map;

@Service
public class PaypalService {

    private final PaypalConfig config;
    private final RestClient restClient;

    public PaypalService(PaypalConfig config) {
        this.config = config;
        this.restClient = RestClient.create();
    }

    private String getAccessToken() {
        String auth = config.getClientId() + ":" + config.getClientSecret();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        Map<String, Object> response = restClient.post()
                .uri(config.getApiUrl() + "/v1/oauth2/token")
                .header("Authorization", "Basic " + encodedAuth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        return response.get("access_token").toString();
    }

    public Map<String, Object> createOrder(Double totalSoles) {
        String accessToken = getAccessToken();
        double tipoDeCambio = 3.75;
        double totalDolares = totalSoles / tipoDeCambio;

        String orderRequest = """
                {
                    "intent": "CAPTURE",
                    "purchase_units": [
                        {
                            "amount": {
                                "currency_code": "USD",
                                "value": "%.2f"
                            }
                        }
                    ],
                    "application_context": {
                        "brand_name": "TicketSystem Pro",
                        "landing_page": "NO_PREFERENCE",
                        "user_action": "PAY_NOW",
                        "return_url": "%s",
                        "cancel_url": "%s"
                    }
                }
                """.formatted(totalDolares, config.getReturnUrl(), config.getCancelUrl());

        return restClient.post()
                .uri(config.getApiUrl() + "/v2/checkout/orders")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderRequest)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> captureOrder(String orderId) {
        String accessToken = getAccessToken();

        return restClient.post()
                .uri(config.getApiUrl() + "/v2/checkout/orders/" + orderId + "/capture")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}