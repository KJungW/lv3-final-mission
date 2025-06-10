package finalmission.test.configuration;

import finalmission.client.HolidaySearchClient;
import finalmission.test.stub.HolidaySearchClientStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ClientConfiguration {

    @Bean
    public HolidaySearchClient holidaySearchClient() {
        return new HolidaySearchClientStub();
    }
}
