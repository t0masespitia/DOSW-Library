package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.persistence.mapper.LoanMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanDTO> getLoans() {
        return loanService.getLoans().stream()
                .map(loan -> new LoanDTO(
                        null,
                        loan.getUser().getId(),
                        loan.getBook().getId(),
                        loan.getLoanDate(),
                        loan.getReturnDate(),
                        loan.getStatus().name()
                ))
                .toList();
    }

    @PostMapping("/borrow")
    public LoanDTO borrowBook(@RequestBody LoanDTO request) {
        return LoanMapper.toDto(loanService.loanBook(request.userId(), request.bookId()));
    }

    @PostMapping("/return")
    public LoanDTO returnBook(@RequestBody LoanDTO request) {
        return LoanMapper.toDto(loanService.returnBook(request.userId(), request.bookId()));
    }
}