package ru.geekbrains.LibraryJPA.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.models.Reader;
import ru.geekbrains.LibraryJPA.services.ReaderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
@Tag(name="Readers", description="Работа с читателями библиотеки")
public class ReaderController {
    private final ReaderService readerService;
    @GetMapping("/all")
    @Operation(
            summary = "Позволяет получить всех читатвелей",
            description = "Позволяет получить всех читатвелей в библиотеке"
    )
    public List<Reader> getAll(){
        return readerService.getAll();
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Позволяет получить читателя по id",
            description = "Позволяет получить читателя по id в библиотеке"
    )
    public Optional<Reader> getReaderByID(@PathVariable @Parameter(description = "Идентификатор читателя") long id){
        return readerService.getReaderByID(id);
    }
    @PostMapping("/reader")
    @Operation(
            summary = "Позволяет добавить читателя",
            description = "Позволяет добавить читателя в библиотеку"
    )
    public Reader addReader(@PathParam("name") String name) {
        return readerService.addReader(name);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Позволяет изменить читателя по id",
            description = "Позволяет изменить читателя в библиотеке"
    )
    public Reader updateReader(@PathVariable @Parameter(description = "Идентификатор читателя") long id,
                               @PathParam("name") String name) {
        return readerService.updateReader(id, name);
    }
}
