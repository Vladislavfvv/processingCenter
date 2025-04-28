package com.edme.pro.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyDto {
    private Long id;
    private String currencyDigitalCode;
    private String currencyLetterCode;
    private String currencyName;
}
