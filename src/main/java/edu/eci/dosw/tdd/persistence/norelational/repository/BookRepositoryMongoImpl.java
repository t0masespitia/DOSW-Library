package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.repository.BookRepositoryPort;
import edu.eci.dosw.tdd.persistence.norelational.document.BookDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class BookRepositoryMongoImpl implements BookRepositoryPort {

    private final MongoBookRepository mongoBookRepository;

    @Override
    public Book save(Book book) {
        BookDocument doc = BookDocument.builder()
                .id(book.getId() != null ? book.getId().toString() : null)
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .availability(BookDocument.Availability.builder()
                        .totalCopies(book.getTotalStock())
                        .availableCopies(book.getAvailableStock())
                        .borrowedCopies(book.getTotalStock() - book.getAvailableStock())
                        .status(book.getAvailableStock() > 0 ? "AVAILABLE" : "UNAVAILABLE")
                        .build())
                .build();
        BookDocument saved = mongoBookRepository.save(doc);
        return toModel(saved);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return mongoBookRepository.findById(id.toString()).map(this::toModel);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return mongoBookRepository.findFirstByIsbn(isbn).map(this::toModel);
    }

    @Override
    public List<Book> findAll() {
        return mongoBookRepository.findAll().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        mongoBookRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(Long id) {
        return mongoBookRepository.existsById(id.toString());
    }

    private Book toModel(BookDocument doc) {
        Book book = new Book();
        try {
            book.setId(doc.getId() != null ? Long.parseLong(doc.getId()) : null);
        } catch (NumberFormatException e) {
            book.setId(null);
        }
        book.setTitle(doc.getTitle());
        book.setAuthor(doc.getAuthor());
        book.setIsbn(doc.getIsbn());
        if (doc.getAvailability() != null) {
            book.setTotalStock(doc.getAvailability().getTotalCopies());
            book.setAvailableStock(doc.getAvailability().getAvailableCopies());
        }
        return book;

    }
}