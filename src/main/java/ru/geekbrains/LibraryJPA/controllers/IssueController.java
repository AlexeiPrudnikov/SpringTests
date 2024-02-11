package ru.geekbrains.LibraryJPA.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.LibraryJPA.models.Issue;
import ru.geekbrains.LibraryJPA.services.IssueService;

import javax.naming.directory.NoSuchAttributeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issues")
@AllArgsConstructor
@Tag(name="Issues", description="Выдача книг читателям в библиотеке")
public class IssueController {
    private final IssueService issueService;
    @GetMapping("/all")
    @Operation(
            summary = "Позволяет получить все факты выдачи книг читателям",
            description = "Позволяет получить все факты выдачи книг читателям в библиотеке"
    )
    public List<Issue> getAll(){
        return issueService.getAll();
    }
    @GetMapping("/{id}")
    public Optional<Issue> getByID(@PathVariable @Parameter(description = "Идентификатор выдачи") long id){
        return issueService.getIssueByID(id);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Позволяет получить факт выдачи книги читателю по id",
            description = "Позволяет получить факт выдачи книги читателю по id в библиотеке"
    )
    public Issue returnBook(@PathVariable @Parameter(description = "Идентификатор выдачи") long id) throws NoSuchAttributeException {
        issueService.closeIssue(id);
        return issueService.getIssueByID(id).get();
    }

}
