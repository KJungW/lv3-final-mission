package finalmission.client;

import finalmission.exception.ExternalApiConnectionException;
import finalmission.exception.HolidaySearchException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

public class HolidaySearchClient {

    private final RestClient restClient;
    private final String secretKey;

    public HolidaySearchClient(RestClient restClient, String secretKey) {
        this.restClient = restClient;
        this.secretKey = secretKey;
    }

    public ResponseEntity<String> findHolidayInMonth(int year, int month) {
        try {
            return requestSearchHolidayInMonth(year, month);
        } catch (ResourceAccessException exception) {
            throw new ExternalApiConnectionException("휴일 조회 외부 API : 연결에 실패했습니다.");
        }
    }

    private ResponseEntity<String> requestSearchHolidayInMonth(int year, int month) {
        String param = String.format("?serviceKey=%s&solYear=%s&solMonth=%s&_type=json", secretKey, year, month);
        return restClient.get()
                .uri("/B090041/openapi/service/SpcdeInfoService/getRestDeInfo" + param)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new HolidaySearchException("휴일 조회 외부 API : 클라이언트 예외");
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new HolidaySearchException("휴일 조회 외부 API : 서버측 예외");
                }))
                .toEntity(String.class);
    }
}
