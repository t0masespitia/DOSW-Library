package edu.eci.dosw.tdd.persistence.relational.repository;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.repository.BookRepositoryPort;
import edu.eci.dosw.tdd.persistence.relational.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
@RequiredArgsConstructor
public class BookRepositoryJpaImpl implements BookRepositoryPort {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return BookMapper.toModel(bookRepository.save(BookMapper.toEntity(book)));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toModel);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).map(BookMapper::toModel);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }
}