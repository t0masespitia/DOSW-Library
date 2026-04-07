package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepositoryPort loanRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private LoanService loanService;

    private User testUser;
    private Book testBook;
    private Loan testLoan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("juan");
        testUser.setRole("USER");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setIsbn("9780132350884");
        testBook.setAvailableStock(5);
        testBook.setTotalStock(5);

        testLoan = new Loan();
        testLoan.setId(1L);
        testLoan.setUser(testUser);
        testLoan.setBook(testBook);
        testLoan.setLoanDate(LocalDate.now());
        testLoan.setStatus(Status.ACTIVE);
    }

    // Test 1: Dado que tengo 1 reserva registrada, cuando la consulto, la consulta es exitosa validando el id
    @Test
    void givenOneLoan_whenGetLoans_thenReturnLoanWithId() {
        when(loanRepository.findAll()).thenReturn(List.of(testLoan));

        List<Loan> loans = loanService.getLoans();

        assertFalse(loans.isEmpty());
        assertEquals(1L, loans.get(0).getId());
    }

    // Test 2: Dado que no hay reservas, cuando consulto, no retorna ningún resultado
    @Test
    void givenNoLoans_whenGetLoans_thenReturnEmptyList() {
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        List<Loan> loans = loanService.getLoans();

        assertTrue(loans.isEmpty());
    }

    // Test 3: Dado que no hay reservas, cuando creo una, la creación es exitosa
    @Test
    void givenNoLoans_whenCreateLoan_thenLoanIsCreated() {
        when(userService.getUserByUsername("juan")).thenReturn(testUser);
        when(bookService.getBookByIsbn("9780132350884")).thenReturn(testBook);
        when(loanRepository.findByUserIdAndStatus(any(), any())).thenReturn(Collections.emptyList());
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan created = loanService.loanBook("juan", "9780132350884");

        assertNotNull(created);
        assertEquals(Status.ACTIVE, created.getStatus());
    }

    // Test 4: Dado que tengo 1 reserva, cuando la elimino, la eliminación es exitosa
    @Test
    void givenOneLoan_whenDelete_thenDeleteIsSuccessful() {
        doNothing().when(loanRepository).deleteById(1L);

        assertDoesNotThrow(() -> loanRepository.deleteById(1L));
        verify(loanRepository, times(1)).deleteById(1L);
    }

    // Test 5: Dado que tengo 1 reserva, cuando la elimino y consulto, no retorna ningún resultado
    @Test
    void givenOneLoan_whenDeleteAndGet_thenReturnEmptyList() {
        doNothing().when(loanRepository).deleteById(1L);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        loanRepository.deleteById(1L);
        List<Loan> loans = loanService.getLoans();

        assertTrue(loans.isEmpty());
    }
}