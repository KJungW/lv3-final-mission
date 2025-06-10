package finalmission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AddReservationRequest(
        @NotNull
        Long positionId,
        @NotBlank
        String reason,
        @NotNull
        LocalDate date
) {

}
