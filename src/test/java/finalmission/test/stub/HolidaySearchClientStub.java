package finalmission.test.stub;

import finalmission.client.HolidaySearchClient;
import org.springframework.http.ResponseEntity;

public class HolidaySearchClientStub extends HolidaySearchClient {

    private ResponseEntity<String> successResponse;
    private RuntimeException failException;

    public HolidaySearchClientStub() {
        super(null, null);
    }

    @Override
    public ResponseEntity<String> findHolidayInMonth(int year, int month) {
        if (successResponse != null) {
            return successResponse;
        }
        throw failException;
    }

    public void setSuccessResponse(ResponseEntity<String> successResponse) {
        this.successResponse = successResponse;
    }

    public void setFailException(RuntimeException failException) {
        this.failException = failException;
    }
}
