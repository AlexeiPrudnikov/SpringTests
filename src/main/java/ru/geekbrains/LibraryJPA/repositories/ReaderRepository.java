package ru.geekbrains.LibraryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.LibraryJPA.models.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
