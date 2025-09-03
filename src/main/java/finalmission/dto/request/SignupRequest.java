package finalmission.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name
) {

}
