package ru.geekbrains.LibraryJPA.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "readers")
@Data
@Schema(name = "Читатель")
public class Reader {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "rId")
    @Schema(name = "Идентификатор")
    private long id;
    @Column(name = "rName", nullable = false)
    @Schema(name = "Фамилия читателя")
    private String name;
    @OneToMany
    @JoinColumn (name = "rID")
    @Schema(name = "Список запросов читателя")
    private List<Issue> issues = new ArrayList<>();
    public Reader(){
    }
    public Reader(String name) {
        this.name = name;
    }
    public static Reader ofName (String name){
        Reader reader = new Reader();
        reader.setName(name);
        return reader;
    }
}
