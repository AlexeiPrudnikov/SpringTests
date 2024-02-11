package ru.geekbrains.LibraryJPA.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "issues")
@Data
@Schema(name = "Выдача книги читателю")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Идентификатор")
    private long id;
    @Column(name = "rID", nullable = false)
    @Schema(name = "Идентификатор читателя")
    private long readerId;
    @Column(name = "bID", nullable = false)
    @Schema(name = "Идентификатор книги")
    private long bookId;
    @Column (name = "issuesDate", nullable = false)
    @Schema(name = "Дата выдачи книги")
    private LocalDate issued_at;
    @Column (name = "returnedDate")
    @Schema(name = "Дата возврата книги")
    private LocalDate returned_at;
    public Issue(long readerId, long bookId, LocalDate issued_at) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.issued_at = issued_at;
    }
    public static Issue onIssue(long bId, long rID, LocalDate issued_at){
        Issue issue = new Issue();
        issue.setBookId(bId);
        issue.setReaderId(rID);
        issue.setIssued_at(issued_at);
        return issue;
    }
    public Issue() {
    }
}
