package app.ReadMate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
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
}
