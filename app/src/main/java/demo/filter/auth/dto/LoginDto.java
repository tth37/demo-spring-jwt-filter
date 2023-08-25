package demo.filter.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class LoginDto {
    @Schema(example = "admin@localhost")
    @NotNull(message = "Email is required")
    public String email;

    @Schema(example = "admin")
    @NotNull(message = "Password is required")
    public String password;
}
