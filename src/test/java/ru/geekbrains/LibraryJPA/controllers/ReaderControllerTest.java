package ru.geekbrains.LibraryJPA.controllers;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.geekbrains.LibraryJPA.JUnitSpringBootBase;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.models.Issue;
import ru.geekbrains.LibraryJPA.models.Reader;
import ru.geekbrains.LibraryJPA.repositories.BookRepository;
import ru.geekbrains.LibraryJPA.repositories.ReaderRepository;

import java.util.List;
import java.util.Objects;

public class ReaderControllerTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitReaderResponse {
        private Long id;
        private String name;
    }
    @Test
    void testFindByIdSuccess() {
        Reader expected = readerRepository.save(Reader.ofName("LastName"));
        ReaderControllerTest.JUnitReaderResponse responseBody = webTestClient.get()
                .uri("/readers/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReaderControllerTest.JUnitReaderResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void testGetAll() {
        // подготовил данные
        readerRepository.saveAll(List.of(
                Reader.ofName("FirstReader"),
                Reader.ofName("SecondReader"),
                Reader.ofName("ThirdReader")
        ));
        List<Reader> expected = readerRepository.findAll();
        List<BookControllerTest.JUnitBookResponse> responseBody = webTestClient.get()
                .uri("/readers/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<BookControllerTest.JUnitBookResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (BookControllerTest.JUnitBookResponse bookResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), bookResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), bookResponse.getName()));
            Assertions.assertTrue(found);
        }
    }
    @Test
    void addReaderTest(){
        JUnitReaderResponse request = new JUnitReaderResponse();
        request.setName("FirstReader");
        ReaderControllerTest.JUnitReaderResponse response = webTestClient.post()
                .uri("/readers/reader?name=" + request.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReaderResponse.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertTrue(readerRepository.findById(response.getId()).isPresent());
    }
}
