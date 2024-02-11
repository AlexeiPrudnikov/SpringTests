package ru.geekbrains.LibraryJPA.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.repositories.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    @PostConstruct
    private void fillBookRepository(){
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            bookRepository.save(new Book(faker.book().title(), faker.book().author()));
        }
    }
    public List<Book> getAll(){
        return bookRepository.findAll();
    }
    public Optional<Book> getBookByID(long id){
        return bookRepository.findById(id);
    }
    public Book addBook(String name, String author){
        return bookRepository.save(new Book(name,author));
    }
    public Book updateBook(long id, String name, String author){
        Optional<Book> result = bookRepository.findById(id);
        if (result != null){
            Book book =  result.get();
            book.setName(name);
            book.setAuthor(author);
            bookRepository.save(book);
            return book;
        }
        return null;
    }
    public void deleteBook(long id){
        try {
            bookRepository.deleteById(id);
        }
        catch (Exception ex){
            throw new NoSuchElementException("Не найден запрос книги с id = " + id);
        }
    }
}
