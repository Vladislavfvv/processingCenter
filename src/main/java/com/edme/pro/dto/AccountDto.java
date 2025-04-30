package com.edme.pro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
   @NotBlank(message = "Account number is required")
   @Size(max = 255, message = "Account number must be at most 255 characters")
    private String accountNumber;
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Balance must be non-negative")//минимально допустимое значение — 0.00 inclusive - активирует его
    @Digits(integer = 17, fraction = 2, message = "Balance must have up to 17 digits and 2 decimal places")
    private BigDecimal balance;
    @NotNull(message = "Currency ID is required")
    private Long currencyId;
    @NotNull(message = "IssuingBank ID is required")
    private Long issuingBankId;
}
