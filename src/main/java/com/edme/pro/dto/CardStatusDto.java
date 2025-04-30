package com.edme.pro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatusDto {
    private Long id;
    @NotBlank(message = "cardStatusName is required")
    @Size(max = 255, message = "cardStatusName must be at most 255 characters")
    private String cardStatusName;
}
