package ru.geekbrains.LibraryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.LibraryJPA.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
