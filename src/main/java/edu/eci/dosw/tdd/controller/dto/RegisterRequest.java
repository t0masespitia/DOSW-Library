package edu.eci.dosw.tdd.controller.dto;

public record RegisterRequest(
        String name,
        String email,
        String username,
        String password
) {}