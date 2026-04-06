package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private static final int MAX_ACTIVE_LOANS = 3;

    private final LoanRepositoryPort loanRepository;
    private final UserService userService;
    private final BookService bookService;

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    public Loan loanBook(String username, String bookIsbn) {
        User user = userService.getUserByUsername(username);
        Book book = bookService.getBookByIsbn(bookIsbn);

        long activeLoansByUser = loanRepository
                .findByUserIdAndStatus(user.getId(), Status.ACTIVE).size();
        if (activeLoansByUser >= MAX_ACTIVE_LOANS) {
            throw new LoanLimitExceededException(username, MAX_ACTIVE_LOANS);
        }

        if (book.getAvailableStock() <= 0) {
            throw new BookNotAvailableException(bookIsbn);
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(DateUtil.today());
        loan.setStatus(Status.ACTIVE);

        Loan saved = loanRepository.save(loan);
        bookService.decrementStock(book);
        return saved;
    }

    public Loan returnBook(String username, String bookIsbn) {
        User user = userService.getUserByUsername(username);
        Book book = bookService.getBookByIsbn(bookIsbn);

        Loan loan = loanRepository
                .findFirstByUserIdAndBookIdAndStatus(user.getId(), book.getId(), Status.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un préstamo activo para el usuario y libro indicados."
                ));

        loan.setStatus(Status.RETURNED);
        loan.setReturnDate(DateUtil.today());

        Loan updated = loanRepository.save(loan);
        bookService.incrementStock(loan.getBook());
        return updated;
    }
}