package ru.geekbrains.LibraryJPA.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name="Books", description="Работа с книгами в библиотеке")
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    @Operation(
            summary = "Позволяет получить все книги",
            description = "Позволяет получить все книги в библиотеке"
    )
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Позволяет получить книгу по id",
            description = "Позволяет получить книгу по id в библиотеке"
    )
    public Optional<Book> getBookById(@PathVariable @Parameter(description = "Идентификатор книги") long id) {
        return bookService.getBookByID(id);
    }

    @PostMapping("/book")
    @Operation(
            summary = "Позволяет добавить книгу",
            description = "Позволяет получить книгу по id в библиотеке"
    )
    public Book addBook(@PathParam("name") @Parameter(description = "Название книги") String name,
                        @PathParam("author") @Parameter(description = "Автор") String author) {
        return bookService.addBook(name, author);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Позволяет изменить книгу по id",
            description = "Позволяет изменить книгу по id в библиотеке"
    )
    public Book updateBook(@PathVariable long id,
                           @PathParam("name")  String name,
                           @PathParam("author") String author) {
        return bookService.updateBook(id, name, author);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Позволяет удалить книгу по id",
            description = "Позволяет удалить книгу по id в библиотеке"
    )
    public ResponseEntity<?> deleteBook(@PathVariable @Parameter(description = "Идентификатор книги")  long id){
        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
