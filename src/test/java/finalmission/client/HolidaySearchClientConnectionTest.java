package finalmission.client;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class HolidaySearchClientConnectionTest {

    @Value("${client.holiday-search.secret-key}")
    private String secretKey;
    @Value("${client.holiday-search.base-url}")
    private String baseUrl;

    private HolidaySearchClient holidaySearchClient;

    @BeforeEach
    void setup() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5);
        factory.setConnectTimeout(30);

        RestClient restClient = RestClient.builder()
                .requestFactory(factory)
                .baseUrl(baseUrl)
                .build();

        holidaySearchClient = new HolidaySearchClient(restClient, secretKey);
    }

    @DisplayName("외부 API에 연결할 수 있다.")
    @Test
    public void canConnect() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(30));

        RestClient restClient = RestClient.builder()
                .requestFactory(factory)
                .baseUrl(baseUrl)
                .build();

        HolidaySearchClient holidaySearchClient = new HolidaySearchClient(restClient, secretKey);
        holidaySearchClient.findHolidayInMonth(2025, 10);
    }
}
