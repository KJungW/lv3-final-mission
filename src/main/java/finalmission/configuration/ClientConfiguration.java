package finalmission.configuration;

import finalmission.client.HolidaySearchClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("!test")
public class ClientConfiguration {

    @Bean
    public HolidaySearchClient holidaySearchClient(
            @Value("${client.holiday-search.secret-key}") String secretKey,
            @Value("${client.holiday-search.base-url}") String baseUrl
    ) {
        RestClient restClient = RestClient.builder()
                .requestFactory(simpleClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .build();
        return new HolidaySearchClient(restClient, secretKey);
    }

    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(30));
        return factory;
    }
}
