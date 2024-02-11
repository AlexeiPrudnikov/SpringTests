package ru.geekbrains.LibraryJPA.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.LibraryJPA.models.Book;
import ru.geekbrains.LibraryJPA.models.Issue;
import ru.geekbrains.LibraryJPA.models.IssueRequest;
import ru.geekbrains.LibraryJPA.repositories.BookRepository;
import ru.geekbrains.LibraryJPA.repositories.IssueRepository;
import ru.geekbrains.LibraryJPA.repositories.ReaderRepository;

import javax.naming.directory.NoSuchAttributeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    public List<Issue> getAll(){
        return issueRepository.findAll();
    }
    public Optional<Issue> getIssueByID(long id){
        return issueRepository.findById(id);
    }
    public List<Book> getBooksByReader(long id){
        List<Issue> issues = issueRepository.findBooksByReader(id);
        List<Book> books = new ArrayList<>();
        for (Issue issue: issues){
            books.add(bookRepository.getById(issue.getBookId()));
        }
        return books;
    }
    public Issue closeIssue(long id) throws NoSuchAttributeException, NoSuchElementException {
        Issue currentIssue = getIssueByID(id).get();
        if (currentIssue != null){
            if (currentIssue.getReturned_at() == null) {
                currentIssue.setReturned_at(LocalDate.now());
                issueRepository.save(currentIssue);
                return currentIssue;
            }
            else{
                throw new NoSuchAttributeException("Запрос с id = " + id + " уже был закрыт");
            }
        }
        throw new NoSuchElementException("Не найден запрос книги с id = " + id);
    }
    public Issue addIssue(IssueRequest request) {
        if (bookRepository.findById(request.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerRepository.findById(request.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }
        Issue issue = new Issue(request.getBookId(), request.getReaderId(), LocalDate.now());
        List<Issue> issues = issueRepository.findAll();
        for (int i = 0; i < issues.size(); i++) {
            if(request.getBookId() == issues.get(i).getBookId() && issues.get(i).getReturned_at() == null){
                return null;
            }
        }
        issueRepository.save(issue);
        return issue;
    }
}
