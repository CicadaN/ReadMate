package app.ReadMate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

// DTO для регистрации
@Getter
@Setter
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
    //todo добавить обновление пароля

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Min(10)
    @Max(120)
    @JsonProperty("age")
    private Integer age;
}
