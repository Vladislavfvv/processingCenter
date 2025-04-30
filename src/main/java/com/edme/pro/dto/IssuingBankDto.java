package com.edme.pro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuingBankDto {
    private Long id;
    @NotBlank(message = "BIC is required")
    @Size(min = 9, max = 9, message = "BIC must be exactly 9 characters")
    private String bic;
    @NotBlank(message = "BIN is required")
    @Size(min = 5, max = 5, message = "BIN must be exactly 5 characters")
    private String bin;
    @NotBlank(message = "Abbreviated Name is required")
    @Size(max = 255, message = "Abbreviated Name must be at most 255 characters")
    private String abbreviatedName;
}
