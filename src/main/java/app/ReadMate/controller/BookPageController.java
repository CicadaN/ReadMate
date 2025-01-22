package app.ReadMate.controller;

import app.ReadMate.dto.BookRequestDTO;
import app.ReadMate.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookService bookService;

    // Страница со списком книг
    @GetMapping("/books-page")
    public String showBooks(Model model) {
        // Загружаем все книги в виде DTO (BookRequestDTO)
        List<BookRequestDTO> allBooks = bookService.getAllBooks();

        // Кладём их в модель
        model.addAttribute("books", allBooks);
        model.addAttribute("title", "Список книг");

        // Говорим layout'у: использовать дочерний шаблон "books" (books.html)
        model.addAttribute("childTemplate", "books");

        // Возвращаем "layout" — ваш главный шаблон
        return "layout";
    }

    // Страница детальной информации о книге
    @GetMapping("/books-page/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        // Загружаем конкретную книгу как DTO
        BookRequestDTO bookDTO = bookService.getBookByIdDTO(id);

        // Кладём DTO в модель
        model.addAttribute("book", bookDTO);
        model.addAttribute("title", "Информация о книге");
        model.addAttribute("childTemplate", "bookDetails");

        return "layout";
    }
}

