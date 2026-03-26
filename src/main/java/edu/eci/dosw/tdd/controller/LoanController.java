package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.CreateLoanRequest;
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
                .map(LoanMapper::toDto)
                .toList();
    }

    @PostMapping("/borrow")
    public LoanDTO borrowBook(@RequestBody CreateLoanRequest request) {
        return LoanMapper.toDto(loanService.loanBook(request.userId(), request.bookId()));
    }

    @PostMapping("/return")
    public LoanDTO returnBook(@RequestBody CreateLoanRequest request) {
        return LoanMapper.toDto(loanService.returnBook(request.userId(), request.bookId()));
    }
}