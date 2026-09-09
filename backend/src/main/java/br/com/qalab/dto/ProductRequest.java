package br.com.qalab.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 80) String name,
        @NotBlank @Pattern(regexp = "[A-Z0-9-]{3,40}") String sku,
        @NotNull @DecimalMin("0.01") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @Min(0) int stock) {}
