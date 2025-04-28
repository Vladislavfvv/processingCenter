package com.edme.pro.mapper;

import com.edme.pro.dto.IssuingBankDto;
import com.edme.pro.model.IssuingBank;

public class IssuingBankMapper {
    public static IssuingBank toEntity(IssuingBankDto dto) {
        return IssuingBank.builder()
                .id(dto.getId())
                .bic(dto.getBic())
                .bin(dto.getBin())
                .abbreviatedName(dto.getAbbreviatedName())
                .build();
    }
    public static IssuingBankDto toDto(IssuingBank issuingBank) {
        return IssuingBankDto.builder()
                .id(issuingBank.getId())
                .bic(issuingBank.getBic())
                .bin(issuingBank.getBin())
                .abbreviatedName(issuingBank.getAbbreviatedName())
                .build();

    }
}
