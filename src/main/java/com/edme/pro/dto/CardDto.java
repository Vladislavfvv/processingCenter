package com.edme.pro.dto;

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
    private String cardNumber;
    // private Date expirationDate;
    private LocalDate expirationDate;
    private String holderName;
    private Long cardStatusId;
    private Long paymentSystemId;
    private Long accountId;
   // private Timestamp receivedFromIssuingBank;
    //private Timestamp sentToIssuingBank;
    private LocalDateTime receivedFromIssuingBank; // Была java.sql.Timestamp
    private LocalDateTime sentToIssuingBank;

}
