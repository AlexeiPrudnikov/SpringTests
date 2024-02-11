package ru.geekbrains.LibraryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.LibraryJPA.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
