package finalmission.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.client.RandomNameClient;
import finalmission.client.RandommerRandomNameClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

    private final ObjectMapper objectMapper;

    public ClientConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RandomNameClient randomNameClient(
            @Value("${client.random-name.base-url}") String baseUrl,
            @Value("${client.random-name.secret-key}") String apiKey,
            @Value("${client.random-name.generate_url}") String generationUrl
    ) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(simpleClientHttpRequestFactory())
                .defaultHeaders(headers -> headers.set("Content-Type", "application/json"))
                .build();
        return new RandommerRandomNameClient(objectMapper, restClient, apiKey, generationUrl);
    }

    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(2));
        requestFactory.setReadTimeout(Duration.ofSeconds(20));
        return requestFactory;
    }
}
