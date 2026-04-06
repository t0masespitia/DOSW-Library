package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.persistence.norelational.document.LoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoLoanRepository extends MongoRepository<LoanDocument, String> {
    List<LoanDocument> findByUserId(String userId);
    List<LoanDocument> findByUserIdAndStatus(String userId, String status);
    Optional<LoanDocument> findFirstByUserIdAndBookIdAndStatus(String userId, String bookId, String status);
}