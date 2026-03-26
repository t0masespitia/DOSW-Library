package edu.eci.dosw.tdd.controller.dto;

public record BookDTO(
        Long id,
        String title,
        String author,
        String isbn,
        Integer totalStock,
        Integer availableStock
) {
}