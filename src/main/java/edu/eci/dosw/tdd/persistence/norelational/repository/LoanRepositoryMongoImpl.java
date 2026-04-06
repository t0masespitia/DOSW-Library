package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.core.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.persistence.norelational.document.LoanDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.User;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class LoanRepositoryMongoImpl implements LoanRepositoryPort {

    private final MongoLoanRepository mongoLoanRepository;

    @Override
    public Loan save(Loan loan) {
        LoanDocument doc = LoanDocument.builder()
                .id(loan.getId() != null ? loan.getId().toString() : null)
                .userId(loan.getUser() != null ? loan.getUser().getUsername() : null)
                .bookId(loan.getBook() != null ? loan.getBook().getIsbn() : null)
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .status(loan.getStatus() != null ? loan.getStatus().name() : null)
                .build();
        return toModel(mongoLoanRepository.save(doc));
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return mongoLoanRepository.findById(id.toString()).map(this::toModel);
    }

    @Override
    public List<Loan> findAll() {
        return mongoLoanRepository.findAll().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        return List.of(); // MongoDB usa username, no Long
    }

    @Override
    public List<Loan> findByUserIdAndStatus(Long userId, Status status) {
        return List.of(); // MongoDB usa username, no Long
    }

    @Override
    public Optional<Loan> findFirstByUserIdAndBookIdAndStatus(Long userId, Long bookId, Status status) {
        return Optional.empty(); // MongoDB usa username/isbn, no Long
    }

    @Override
    public void deleteById(Long id) {
        mongoLoanRepository.deleteById(id.toString());
    }

    private Loan toModel(LoanDocument doc) {
        Loan loan = new Loan();
        loan.setLoanDate(doc.getLoanDate());
        loan.setReturnDate(doc.getReturnDate());
        loan.setStatus(doc.getStatus() != null ? Status.valueOf(doc.getStatus()) : null);

        if (doc.getUserId() != null) {
            User user = new User();
            user.setUsername(doc.getUserId()); // userId guarda el username
            loan.setUser(user);
        }

        if (doc.getBookId() != null) {
            Book book = new Book();
            book.setIsbn(doc.getBookId()); // bookId guarda el isbn
            loan.setBook(book);
        }

        return loan;
    }
}