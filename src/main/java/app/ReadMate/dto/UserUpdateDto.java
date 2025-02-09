package app.ReadMate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Min(10)
    @Max(120)
    private Integer age;
}
