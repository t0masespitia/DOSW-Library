package edu.eci.dosw.tdd.controller.dto;

public record CreateLoanRequest(
        Long userId,
        Long bookId
) {
}