package edu.eci.dosw.tdd.controller.dto;

import edu.eci.dosw.tdd.persistence.entity.LoanStatus;
import java.time.LocalDate;

public record LoanDTO(
        Long id,
        Long userId,
        Long bookId,
        LocalDate loanDate,
        LocalDate returnDate,
        LoanStatus status
) {}