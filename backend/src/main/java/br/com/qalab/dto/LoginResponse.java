package br.com.qalab.dto;

public record LoginResponse(String accessToken, String tokenType, long expiresIn, String role) {}
