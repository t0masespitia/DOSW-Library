package edu.eci.dosw.tdd.controller.dto;

public record LoginResponse(
        String token,
        String username,
        String role
) {}