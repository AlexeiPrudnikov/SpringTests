package ru.geekbrains.LibraryJPA.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table (name = "books")
@Schema(name = "Книга")

public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bId")
    @Schema(name = "Идентификатор")
    private long id;
    @Column (name = "bName", nullable = false)
    @Schema(name = "Название книги")
    private String name;
    @Column(name = "bAuthor", nullable = false)
    @Schema(name = "Автор")
    private String author;
    @OneToMany
    @JoinColumn (name = "bID")
    @Schema(name = "Список запросов книги")
    private List<Issue> issues = new ArrayList<>();
    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }
    public static Book ofBook(String title, String author){
        Book book = new Book(title, author);
        return book;
    }
    public Book(){

    }
}
