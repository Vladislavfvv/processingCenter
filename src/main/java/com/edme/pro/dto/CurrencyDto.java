package com.edme.pro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Long id;
   @NotBlank(message = "currencyDigitalCode is required")
   @Size(min = 3, max = 3, message = "currencyDigitalCode must be exactly 3 characters")
    private String currencyDigitalCode;
    @NotBlank(message = "currencyLetterCode is required")
    @Size(min = 3, max = 3, message = "currencyLetterCode must be exactly 3 characters")
    private String currencyLetterCode;
    @NotBlank(message = "currencyName  is required")
    @Size(max = 255, message = "currencyName  must be at most 255 characters")
    private String currencyName;
}
