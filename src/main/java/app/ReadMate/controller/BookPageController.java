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
    @GetMapping("/books_details")
    public String showBooks(Model model) {
        List<BookRequestDTO> allBooks = bookService.getAllBooks();

        model.addAttribute("books", allBooks);
        model.addAttribute("title", "Список книг");

        model.addAttribute("childTemplate", "books");

        return "layout";
    }

    // Страница детальной информации о книге
    @GetMapping("/books_details/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        BookRequestDTO bookDTO = bookService.getBookByIdDTO(id);

        model.addAttribute("book", bookDTO);
        model.addAttribute("title", "Информация о книге");
        model.addAttribute("childTemplate", "bookDetails");

        return "layout";
    }
}

