package app.ReadMate;

import app.ReadMate.dto.BookRequestDTO;
import app.ReadMate.model.Book;
import app.ReadMate.repository.BookRepository;
import app.ReadMate.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanUp() {
        // Если у вас есть метод удаления всех книг:
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testCreateBook() throws Exception {
        BookRequestDTO requestDto = new BookRequestDTO();
        requestDto.setTitle("Test Book");
        requestDto.setAuthor("John Smith");
        requestDto.setDescription("Some description");
        requestDto.setPublicationYear(2023);

        mvc.perform(post("/books")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("John Smith"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testGetBookById() throws Exception {
        // Сначала создадим книгу через сервис (или репозиторий)
        BookRequestDTO requestDto = new BookRequestDTO();
        requestDto.setTitle("Another Book");
        requestDto.setAuthor("Alice");
        requestDto.setDescription("Another description");
        requestDto.setPublicationYear(2020);

        Book saved = bookService.createBook(requestDto);

        mvc.perform(get("/books/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.title").value("Another Book"))
                .andExpect(jsonPath("$.author").value("Alice"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testUpdateBook() throws Exception {
        // Создаём книгу для обновления
        BookRequestDTO requestDto = new BookRequestDTO();
        requestDto.setTitle("Old Title");
        requestDto.setAuthor("Old Author");
        requestDto.setDescription("Old desc");
        requestDto.setPublicationYear(2000);
        Book oldBook = bookService.createBook(requestDto);

        BookRequestDTO updateDto = new BookRequestDTO();
        updateDto.setTitle("New Title");
        updateDto.setAuthor("New Author");
        updateDto.setDescription("New desc");
        updateDto.setPublicationYear(2025);

        mvc.perform(put("/books/{id}", oldBook.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.author").value("New Author"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testDeleteBook() throws Exception {
        // Сначала создаём книгу
        BookRequestDTO requestDto = new BookRequestDTO();
        requestDto.setTitle("Book to Delete");
        requestDto.setAuthor("Unknown");
        requestDto.setDescription("Temp desc");
        requestDto.setPublicationYear(1999);
        Book saved = bookService.createBook(requestDto);

        mvc.perform(delete("/books/{id}", saved.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testGetAllBooks() throws Exception {
        // Создадим несколько книг
        BookRequestDTO dto1 = new BookRequestDTO();
        dto1.setTitle("Book1");
        dto1.setAuthor("Author1");
        dto1.setDescription("Desc1");
        dto1.setPublicationYear(2021);
        bookService.createBook(dto1);

        BookRequestDTO dto2 = new BookRequestDTO();
        dto2.setTitle("Book2");
        dto2.setAuthor("Author2");
        dto2.setDescription("Desc2");
        dto2.setPublicationYear(2022);
        bookService.createBook(dto2);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[1].title").exists());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testSearchBooks() throws Exception {
        // Создадим книгу, чтобы она попадала в поиск
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitle("UniqueTitleForSearch");
        dto.setAuthor("Searchable Author");
        dto.setDescription("Desc");
        dto.setPublicationYear(2021);
        bookService.createBook(dto);

        mvc.perform(get("/books/search")
                        .param("title", "UniqueTitleForSearch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("UniqueTitleForSearch"));
    }
}
