package edu.eci.dosw.tdd.controller.dto;

public record LoginRequest(
        String username,
        String password
) {}