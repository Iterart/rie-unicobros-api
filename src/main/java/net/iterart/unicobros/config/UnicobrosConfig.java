package net.iterart.unicobros.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnicobrosConfig {

    @Value("${unicobros.api.key:your-api-key-here}")
    private String apiKey;

    @Value("${unicobros.api.access-token:your-access-token-here}")
    private String accessToken;

    @Value("${unicobros.api.url:https://api.unicobros.com.ar}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
