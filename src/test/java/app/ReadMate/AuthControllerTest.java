package app.ReadMate;

import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.repository.UserRepository;
import app.ReadMate.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private UserRequestDto testUserDto;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Удаляем всех
        userRepository.deleteAll();
        // Создаём пользователя в базе
        testUserDto = new UserRequestDto();
        testUserDto.setUsername("admin");
        testUserDto.setPassword("testpass");
        testUserDto.setEmail("test@email.com");
        testUserDto.setAge(25);
        userService.save(testUserDto);
    }

    @Test
    public void testRegister() throws Exception {
        String username = "test";
        String password = "test12test";


        mvc.perform(multipart("/register")
                        .with(csrf())
                        .param("username", username)
                        .param("password", password)
                        .param("age", "25") // Добавляем возраст
                )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("User registered and authenticated successfully")));
    }

    @Test
    public void testLogin_Success() throws Exception {
        mvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUserDto.getUsername())
                        .param("password", testUserDto.getPassword())
                )
                .andExpect(status().is3xxRedirection()) // Успешный логин должен редиректить на /profile
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        mvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "wrongUser")
                        .param("password", "wrongPassword")
                )
                .andExpect(status().is3xxRedirection()) // Должен редиректить на login?error=true
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testLogout() throws Exception {
        mvc.perform(post("/logout")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout successful"));
    }





}
