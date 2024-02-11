package ru.geekbrains.LibraryJPA.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.LibraryJPA.JUnitSpringBootBase;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.repositories.BookRepository;

import java.util.List;
import java.util.Objects;


public class BookControllerTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitBookResponse {
        private Long id;
        private String name;
        private String author;
    }

    @Test
    void testFindByIdSuccess() {

        Book expected = bookRepository.save(Book.ofBook("Title", "Author"));

        JUnitBookResponse responseBody = webTestClient.get()
                .uri("/books/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
        Assertions.assertEquals(expected.getAuthor(), responseBody.getAuthor());
    }

    @Test
    void testGetAll() {
        // подготовил данные
        bookRepository.saveAll(List.of(
                Book.ofBook("FirstTitle", "FirstAuthor"),
                Book.ofBook("SecondTitle", "SecondAuthor"),
                Book.ofBook("ThirdTitle", "ThirdAuthor")
        ));

        List<Book> expected = bookRepository.findAll();
        List<JUnitBookResponse> responseBody = webTestClient.get()
                .uri("/books/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitBookResponse bookResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), bookResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), bookResponse.getName()) &&
                            Objects.equals(it.getAuthor(), bookResponse.getAuthor()));
            Assertions.assertTrue(found);
        }
    }
    @Test
    void addBook() {
        BookControllerTest.JUnitBookResponse request = new BookControllerTest.JUnitBookResponse();
        request.setName("Title");
        request.setAuthor("Author");
        BookControllerTest.JUnitBookResponse response = webTestClient.post()
                .uri("/books/book?name=" + request.getName() + "&author=" + request.getAuthor())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookControllerTest.JUnitBookResponse.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertTrue(bookRepository.findById(response.getId()).isPresent());
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getAuthor(), response.getAuthor());
    }
}
