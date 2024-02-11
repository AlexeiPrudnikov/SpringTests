package ru.geekbrains.LibraryJPA.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.models.Issue;
import ru.geekbrains.LibraryJPA.models.Reader;
import ru.geekbrains.LibraryJPA.services.BookService;
import ru.geekbrains.LibraryJPA.services.IssueService;
import ru.geekbrains.LibraryJPA.services.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/ui")
public class UIHtmlController {
    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueService issueService;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/readers")
    public String getAllReaders(Model model) {
        List<Reader> readers = readerService.getAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/readers/{id}")
    public String getBooksByReader(Model model, @PathVariable long id) {

        try {
            Reader reader = readerService.getReaderByID(id).get();
            List<Book> books = issueService.getBooksByReader(id);
            model.addAttribute("reader", reader);
            model.addAttribute("books", books);
            return "booksbyreader";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Читатель с id = " + id + " не найден");
            return "libraryerror";
        }
    }

    @GetMapping("/issues")
    public String getAllIssues(Model model) {
        List<Issue> issues = issueService.getAll();
        model.addAttribute("issues", issues);
        model.addAttribute("readerService", readerService);
        model.addAttribute("bookService", bookService);
        return "issues";
    }

}
