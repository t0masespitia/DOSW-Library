package edu.eci.dosw.tdd.core.repository;

import edu.eci.dosw.tdd.core.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}