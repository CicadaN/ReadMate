package app.ReadMate.dto;

import lombok.Data;

// для ответа клиенту
@Data
public class UserResponseDto {
    private long id;
    private String username;
    private String email;
}

