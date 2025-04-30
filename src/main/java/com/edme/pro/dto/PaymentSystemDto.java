package com.edme.pro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemDto {
   private Long id;
   @NotBlank(message = "paymentSystemName is required")
   @Size(max = 50, message = "paymentSystemName must be at most 50 characters")
    private String paymentSystemName;
}
