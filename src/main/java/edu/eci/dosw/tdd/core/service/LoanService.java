package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.util.DateUtil;
import edu.eci.dosw.tdd.persistence.entity.BookEntity;
import edu.eci.dosw.tdd.persistence.entity.LoanEntity;
import edu.eci.dosw.tdd.persistence.entity.LoanStatus;
import edu.eci.dosw.tdd.persistence.entity.UserEntity;
import edu.eci.dosw.tdd.persistence.mapper.LoanMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private static final int MAX_ACTIVE_LOANS = 3;

    private final LoanRepository loanRepository;
    private final UserService userService;
    private final BookService bookService;

    public LoanService(LoanRepository loanRepository, UserService userService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<Loan> getLoans() {
        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toModel)
                .toList();
    }

    public LoanEntity loanBook(Long userId, Long bookId) {
        UserEntity user = userService.getUserEntityById(userId);
        BookEntity book = bookService.getBookEntityById(bookId);

        long activeLoansByUser = loanRepository.findByUserIdAndStatus(userId, LoanStatus.ACTIVO).size();
        if (activeLoansByUser >= MAX_ACTIVE_LOANS) {
            throw new LoanLimitExceededException(String.valueOf(userId), MAX_ACTIVE_LOANS);
        }

        if (book.getAvailableStock() <= 0) {
            throw new BookNotAvailableException(String.valueOf(bookId));
        }

        LoanEntity loan = LoanEntity.builder()
                .user(user)
                .book(book)
                .loanDate(DateUtil.today())
                .status(LoanStatus.ACTIVO)
                .build();

        LoanEntity saved = loanRepository.save(loan);
        bookService.decrementInventory(book);
        return saved;
    }

    public LoanEntity returnBook(Long userId, Long bookId) {
        LoanEntity loan = loanRepository
                .findFirstByUserIdAndBookIdAndStatus(userId, bookId, LoanStatus.ACTIVO)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un préstamo activo para el usuario y libro indicados."
                ));

        loan.setStatus(LoanStatus.DEVUELTO);
        loan.setReturnDate(DateUtil.today());

        LoanEntity updated = loanRepository.save(loan);
        bookService.incrementInventory(loan.getBook());
        return updated;
    }
}