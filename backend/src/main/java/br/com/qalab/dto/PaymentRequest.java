package br.com.qalab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(
        @NotBlank @Pattern(regexp = "\\d{16}", message = "cardNumber must contain 16 digits") String cardNumber) {}
