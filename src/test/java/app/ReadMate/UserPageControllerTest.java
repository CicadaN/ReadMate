package app.ReadMate;

import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testuser")
    public void testGetProfilePage() throws Exception {
        UserResponseDto user = new UserResponseDto();
        user.setUsername("testuser");

        when(userService.findByUsername("testuser")).thenReturn(user);

        mvc.perform(get("/profile").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("layout"))
                .andExpect(model().attributeExists("user", "title"));
    }

    @Test
    public void testGetProfilePage_Unauthorized() throws Exception {
        mvc.perform(get("/profile").with(csrf()))
                .andExpect(status().isUnauthorized()); // Ожидаем 401 вместо редиректа
    }

}
