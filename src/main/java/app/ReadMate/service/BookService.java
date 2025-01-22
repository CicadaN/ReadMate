package app.ReadMate.service;

import app.ReadMate.dto.BookRequestDTO;
import app.ReadMate.mapper.BookMapper;
import app.ReadMate.model.Book;
import app.ReadMate.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public Book createBook(BookRequestDTO bookRequest) {
        Book book = bookMapper.toEntity(bookRequest); // Вызов через инжектированный бин
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

    }

    public BookRequestDTO getBookByIdDTO(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return bookMapper.toDto(book);
    }

    public List<BookRequestDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    public Book updateBook(Long id, BookRequestDTO bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Book updatedBook = bookMapper.toEntity(bookRequest);
        updatedBook.setId(existingBook.getId()); // Сохраняем ID
        return bookRepository.save(updatedBook);
    }

}
