package edu.eci.dosw.tdd.controller.dto;


import java.time.LocalDate;

public record LoanDTO(
        Long id,
        Long userId,
        Long bookId,
        LocalDate loanDate,
        LocalDate returnDate,
        String status
) {
}