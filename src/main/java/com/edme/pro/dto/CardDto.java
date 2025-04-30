package com.edme.pro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long id;
    @NotBlank(message = "cardNumber is required")
    @Size(max = 50, message = "cardNumber must be at most 50 characters")
    private String cardNumber;
    // private Date expirationDate;
    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;
    @NotBlank(message = "holderName is required")
    @Size(max = 50, message = "holderName must be at most 50 characters")
    private String holderName;
    @NotNull(message = "CardStatus ID is required")
    private Long cardStatusId;
    @NotNull(message = "PaymentSystem ID is required")
    private Long paymentSystemId;
    @NotNull(message = "Account ID is required")
    private Long accountId;
   // private Timestamp receivedFromIssuingBank;
    //private Timestamp sentToIssuingBank;
   @PastOrPresent(message = "Received date must be in the past or present")
    private LocalDateTime receivedFromIssuingBank; // Была java.sql.Timestamp
    @PastOrPresent(message = "Sent date must be in the past or present")
    private LocalDateTime sentToIssuingBank;

}
