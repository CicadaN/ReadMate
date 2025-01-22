package app.ReadMate.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String title;

    @NotBlank
    @Size(max = 50, message = "Имя автора должно быть не длиннее 50 символов")
    private String author;

    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN должен содержать 10 или 13 цифр")
    @Column(unique = true)
    private String isbn;

    @NotBlank
    @Size(max = 50, message = "Название издателя не должно превышать 50 символов")
    private String publisher;

    @Positive(message = "Количество страниц должно быть положительным числом")
    private Integer pages;

    @NotBlank
    @Size(max = 30, message = "Жанр не должен превышать 30 символов")
    private String genre;

    @NotBlank
    @Size(min = 20, max = 500, message = "Описание должно быть от 20 до 500 символов")
    private String description;

    @Min(value = 1440, message = "Год публикации должен быть не раньше 1440 года")
    private Integer publicationYear;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

}
