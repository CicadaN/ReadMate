package app.ReadMate;

import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.repository.UserRepository;
import app.ReadMate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class) // аналог @RunWith(SpringRunner.class) в JUnit 5
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Удаляем всех пользователей перед тестом
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testSaveUser() throws Exception {
        String jsonBody = "{" +
                "\"username\":\"testuser\"," +
                "\"password\":\"testpass\"," +
                "\"email\":\"test@email.com\"," +
                "\"age\":25" +
                "}";

        mvc.perform(post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testGetUsers() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testDeleteUser() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("admin");
        dto.setPassword("testpass");
        dto.setEmail("test@email.com");
        dto.setAge(25);
        UserResponseDto delUser = userService.save(dto);

        mvc.perform(delete("/api/users")
                        .with(csrf())
                        .param("id", String.valueOf(delUser.getId())))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testUpdateUser() throws Exception {
        // Создаём пользователя в базе
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("admin");
        dto.setPassword("testpass");
        dto.setEmail("test@email.com");
        dto.setAge(25);
        userService.save(dto);  // Сохраняем пользователя

        // Отправляем PUT-запрос с обновлёнными данными
        mvc.perform(put("/profile")
                        .with(csrf())  // Для безопасности
                        .param("username", "admin")  // Не меняем имя, оно фиксированное
                        .param("email", "newemail@email.com")  // Обновляем email
                        .param("age", "30"))  // Обновляем возраст
                .andExpect(status().is3xxRedirection())  // Ожидаем редирект
                .andExpect(redirectedUrl("/profile"));

        // Загружаем пользователя после обновления
        UserResponseDto updatedUser = userService.findByUsername("admin");

        // Проверяем, что данные действительно изменились
        assertNotNull(updatedUser);
        assertEquals("newemail@email.com", updatedUser.getEmail());
        assertEquals(30, updatedUser.getAge());
    }


}
