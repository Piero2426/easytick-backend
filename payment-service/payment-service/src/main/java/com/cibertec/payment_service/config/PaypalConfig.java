package com.cibertec.payment_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "paypal")
@Data
public class PaypalConfig {
    private String clientId;
    private String clientSecret;
    private String apiUrl;
    private String returnUrl;
    private String cancelUrl;
}