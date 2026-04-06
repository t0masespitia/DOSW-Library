package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.CreateLoanRequest;
import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.persistence.relational.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans().stream()
                .map(LoanMapper::toDto)
                .toList();
    }

    @PostMapping("/borrow")
    @PreAuthorize("hasAnyRole('USER', 'BIBLIOTECARIO')")
    public LoanDTO borrowBook(@RequestBody CreateLoanRequest request) {
        return LoanMapper.toDto(loanService.loanBook(request.username(), request.bookIsbn()));
    }

    @PostMapping("/return")
    @PreAuthorize("hasAnyRole('USER', 'BIBLIOTECARIO')")
    public LoanDTO returnBook(@RequestBody CreateLoanRequest request) {
        return LoanMapper.toDto(loanService.returnBook(request.username(), request.bookIsbn()));
    }
}