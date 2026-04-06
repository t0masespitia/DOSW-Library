package edu.eci.dosw.tdd.core.repository;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findAll();
    List<Loan> findByUserId(Long userId);
    List<Loan> findByUserIdAndStatus(Long userId, Status status);
    Optional<Loan> findFirstByUserIdAndBookIdAndStatus(Long userId, Long bookId, Status status);
    void deleteById(Long id);
}