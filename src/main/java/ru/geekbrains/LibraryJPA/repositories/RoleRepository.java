package ru.geekbrains.LibraryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.LibraryJPA.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
