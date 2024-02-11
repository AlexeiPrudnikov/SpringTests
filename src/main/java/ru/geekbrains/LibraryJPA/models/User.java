package ru.geekbrains.LibraryJPA.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Stream;

@Data
@Entity
@Table(name = "users")
@Schema(name = "Пользователи")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "uId")
    @Schema(name = "Идентификатор")
    private long id;
    @Column(name = "ulogin", nullable = false)
    @Schema(name = "Login")
    private String login;
    @Column(name = "upassword", nullable = false)
    @Schema(name = "Password")
    private String password;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "uid"),
            inverseJoinColumns = @JoinColumn(
                    name = "rid"))
    private List<Role> roles;

}
