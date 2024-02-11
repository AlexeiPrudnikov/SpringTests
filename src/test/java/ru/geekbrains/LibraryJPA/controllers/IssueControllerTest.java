package ru.geekbrains.LibraryJPA.controllers;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.LibraryJPA.JUnitSpringBootBase;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.models.Issue;
import ru.geekbrains.LibraryJPA.models.Reader;
import ru.geekbrains.LibraryJPA.repositories.BookRepository;
import ru.geekbrains.LibraryJPA.repositories.IssueRepository;
import ru.geekbrains.LibraryJPA.repositories.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class IssueControllerTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Data
    static class JUnitIssueResponse {
        private Long id;
        private Long readerId;
        private Long bookId;
        LocalDate issued_at;
        LocalDate returned_at;
    }
    @Test
    void testGetAll(){
        bookRepository.saveAll(List.of(
                Book.ofBook("FirstTitle", "FirstAuthor"),
                Book.ofBook("SecondTitle", "SecondAuthor"),
                Book.ofBook("ThirdTitle", "ThirdAuthor")
        ));
        readerRepository.saveAll(List.of(
                Reader.ofName("FirstReader"),
                Reader.ofName("SecondReader"),
                Reader.ofName("ThirdReader")
        ));
        List<Book> books = bookRepository.findAll();
        List<Reader> readers = readerRepository.findAll();
        Random random = new Random();
        for (Book book : books){
            Issue issue = new Issue();
            issue.setBookId(book.getId());
            issue.setReaderId(readers.get(random.nextInt(readers.size())).getId());
            issue.setIssued_at(LocalDate.now());
            issueRepository.save(issue);
        }
        List<Issue> expected = issueRepository.findAll();
        List<IssueControllerTest.JUnitIssueResponse> responseBody = webTestClient.get()
                .uri("/issues/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<IssueControllerTest.JUnitIssueResponse>>() {
                })
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (IssueControllerTest.JUnitIssueResponse issueResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), issueResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getBookId(), issueResponse.getBookId()) &&
                            Objects.equals(it.getReaderId(), issueResponse.getReaderId()) &&
                            Objects.equals(it.getIssued_at(), issueResponse.getIssued_at()));
            Assertions.assertTrue(found);
        }
    }
    @Test
    void testFindByIdSuccess() {

        Book book = bookRepository.save(Book.ofBook("Title", "Author"));
        Reader reader = readerRepository.save(Reader.ofName("Ivanov"));
        Issue expected = issueRepository.save(Issue.onIssue(book.getId(),reader.getId(),LocalDate.now()));
        IssueControllerTest.JUnitIssueResponse response = webTestClient.get()
                .uri("/issues/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssueControllerTest.JUnitIssueResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getBookId(), response.getBookId());
        Assertions.assertEquals(expected.getReaderId(), response.getReaderId());
        Assertions.assertEquals(expected.getIssued_at(), response.getIssued_at());
        Assertions.assertEquals(expected.getReturned_at(), response.getReturned_at());
    }
    @Test
    void testReturnBook(){
        Book book = bookRepository.save(Book.ofBook("Title", "Author"));
        Reader reader = readerRepository.save(Reader.ofName("Ivanov"));
        Issue expected = issueRepository.save(Issue.onIssue(book.getId(),reader.getId(),LocalDate.now()));
        IssueControllerTest.JUnitIssueResponse response = webTestClient.put()
                .uri("/issues/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssueControllerTest.JUnitIssueResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getBookId(), response.getBookId());
        Assertions.assertEquals(expected.getReaderId(), response.getReaderId());
        Assertions.assertEquals(expected.getIssued_at(), response.getIssued_at());
        Assertions.assertEquals(LocalDate.now(), response.getReturned_at());
    }
}
