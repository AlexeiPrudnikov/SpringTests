package ru.geekbrains.LibraryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.LibraryJPA.models.Issue;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("select i from Issue i where i.readerId = :id and i.returned_at is null")
    List<Issue> findBooksByReader(Long id);
}
