package edu.eci.dosw.tdd.persistence.relational.repository;

import edu.eci.dosw.tdd.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.tdd.persistence.relational.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByUserId(Long userId);
    List<LoanEntity> findByUserIdAndStatus(Long userId, LoanStatus status);
    Optional<LoanEntity> findFirstByUserIdAndBookIdAndStatus(Long userId, Long bookId, LoanStatus status);
}