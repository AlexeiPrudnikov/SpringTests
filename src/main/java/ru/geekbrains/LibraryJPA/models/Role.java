package ru.geekbrains.LibraryJPA.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@Schema(name = "Роли")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "rId")
    @Schema(name = "Идентификатор")
    private long id;
    @Column(name = "rname", nullable = false)
    @Schema(name = "Роль")
    private String role;

}
