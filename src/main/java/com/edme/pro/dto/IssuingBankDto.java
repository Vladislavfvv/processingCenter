package com.edme.pro.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssuingBankDto {
    private Long id;
    private String bic;
    private String bin;
    private String abbreviatedName;
}
