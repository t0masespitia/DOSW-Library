package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        bookService = new BookService();
        loanService = new LoanService(userService, bookService);
    }

    @Test
    @DisplayName("Debe cargar usuarios e inventario inicial correctamente")
    void shouldLoadInitialUsersAndInventoryCorrectly() {
        assertEquals(2, userService.getUsers().size());
        assertEquals(2, bookService.getInventory().get("b1"));
        assertEquals(1, bookService.getInventory().get("b2"));
    }

    @Test
    @DisplayName("Debe realizar un préstamo exitosamente cuando hay disponibilidad")
    void shouldLoanBookSuccessfullyWhenBookIsAvailable() {
        Loan loan = loanService.loanBook("u1", "b1");

        assertNotNull(loan);
        assertEquals(Status.ACTIVE, loan.getStatus());
        assertEquals("u1", loan.getUser().getId());
        assertEquals("b1", loan.getBook().getId());
        assertEquals(1, bookService.getInventory().get("b1"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando un libro ya no tiene ejemplares disponibles")
    void shouldThrowExceptionWhenBookIsNotAvailable() {
        loanService.loanBook("u1", "b2");

        assertThrows(BookNotAvailableException.class,
                () -> loanService.loanBook("u2", "b2"));
    }

    @Test
    @DisplayName("Debe iniciar sin préstamos registrados")
    void shouldStartWithNoLoansRegistered() {
        assertTrue(loanService.getLoans().isEmpty());
    }

    @Test
    @DisplayName("Debe devolver un libro exitosamente y actualizar inventario")
    void shouldReturnBookSuccessfullyAndRestoreInventory() {
        loanService.loanBook("u1", "b1");

        Loan returnedLoan = loanService.returnBook("u1", "b1");

        assertNotNull(returnedLoan);
        assertEquals(Status.RETURNED, returnedLoan.getStatus());
        assertNotNull(returnedLoan.getReturnDate());
        assertEquals(2, bookService.getInventory().get("b1"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el usuario supera el límite de préstamos activos")
    void shouldThrowExceptionWhenUserExceedsActiveLoanLimit() {
        loanService.loanBook("u1", "b1");
        loanService.returnBook("u1", "b1");
        loanService.loanBook("u1", "b1");
        loanService.loanBook("u1", "b2");
        loanService.loanBook("u1", "b1");

        assertThrows(LoanLimitExceededException.class,
                () -> loanService.loanBook("u1", "b1"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se intenta devolver un libro sin préstamo activo")
    void shouldThrowExceptionWhenReturningBookWithoutActiveLoan() {
        assertThrows(IllegalArgumentException.class,
                () -> loanService.returnBook("u1", "b1"));
    }
}