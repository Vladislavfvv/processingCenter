package com.edme.pro.mapper;

import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.Currency;

public class CurrencyMapper {
    public static Currency toEntity(CurrencyDto dto){
        return Currency.builder()
                .id(dto.getId())
                .currencyDigitalCode(dto.getCurrencyDigitalCode())
                .currencyLetterCode(dto.getCurrencyLetterCode())
                .currencyName(dto.getCurrencyName())
                .build();
    }

//    public static CurrencyDto toDto(Currency entity){
//        CurrencyDto dto = new CurrencyDto();
//        dto.setCurrencyDigitalCode(entity.getCurrencyDigitalCode());
//        dto.setCurrencyLetterCode(entity.getCurrencyLetterCode());
//        dto.setCurrencyName(entity.getCurrencyName());
//        return dto;
//    }

    public static CurrencyDto toDto(Currency entity) {
        return CurrencyDto.builder()
                .id(entity.getId())
                .currencyDigitalCode(entity.getCurrencyDigitalCode())
                .currencyLetterCode(entity.getCurrencyLetterCode())
                .currencyName(entity.getCurrencyName())
                .build();
    }

}
