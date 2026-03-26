package edu.eci.dosw.tdd.controller.dto;


public record UserDTO(
        Long id,
        String name,
        String email,
        String username
) {
}