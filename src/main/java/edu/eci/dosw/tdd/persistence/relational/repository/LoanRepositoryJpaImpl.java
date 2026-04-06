package edu.eci.dosw.tdd.persistence.relational.repository;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.core.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.persistence.relational.entity.LoanStatus;
import edu.eci.dosw.tdd.persistence.relational.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
@RequiredArgsConstructor
public class LoanRepositoryJpaImpl implements LoanRepositoryPort {

    private final LoanRepository loanRepository;

    @Override
    public Loan save(Loan loan) {
        return LoanMapper.toModel(loanRepository.save(LoanMapper.toEntity(loan)));
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id).map(LoanMapper::toModel);
    }

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll().stream()
                .map(LoanMapper::toModel)
                .toList();
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(LoanMapper::toModel)
                .toList();
    }

    @Override
    public List<Loan> findByUserIdAndStatus(Long userId, Status status) {
        LoanStatus loanStatus = status == Status.ACTIVE ? LoanStatus.ACTIVO : LoanStatus.DEVUELTO;
        return loanRepository.findByUserIdAndStatus(userId, loanStatus).stream()
                .map(LoanMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Loan> findFirstByUserIdAndBookIdAndStatus(Long userId, Long bookId, Status status) {
        LoanStatus loanStatus = status == Status.ACTIVE ? LoanStatus.ACTIVO : LoanStatus.DEVUELTO;
        return loanRepository.findFirstByUserIdAndBookIdAndStatus(userId, bookId, loanStatus)
                .map(LoanMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }
}