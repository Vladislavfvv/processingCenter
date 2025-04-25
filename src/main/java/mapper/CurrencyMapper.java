package mapper;

import dto.CurrencyDto;
import model.Currency;

public class CurrencyMapper {
    public static Currency toEntity(CurrencyDto dto){
        return Currency.builder()
                .currencyDigitalCode(dto.getCurrencyDigitalCode())
                .currencyLetterCode(dto.getCurrencyLetterCode())
                .currencyName(dto.getCurrencyName())
                .build();
    }

    public static CurrencyDto toDto(Currency entity){
        CurrencyDto dto = new CurrencyDto();
        dto.setCurrencyDigitalCode(entity.getCurrencyDigitalCode());
        dto.setCurrencyLetterCode(entity.getCurrencyLetterCode());
        dto.setCurrencyName(entity.getCurrencyName());
        return dto;
    }

}
