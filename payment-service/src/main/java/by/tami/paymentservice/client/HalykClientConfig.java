package by.tami.paymentservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HalykClientConfig {

    @Bean
    public RestClient halykOauthClient(@Value("${halyk.oauth-base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient halykApiClient(@Value("${halyk.api-base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}