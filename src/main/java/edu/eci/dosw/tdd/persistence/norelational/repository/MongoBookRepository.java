package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.persistence.norelational.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoBookRepository extends MongoRepository<BookDocument, String> {
    Optional<BookDocument> findFirstByIsbn(String isbn);
}