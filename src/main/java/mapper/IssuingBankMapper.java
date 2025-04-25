package mapper;

import dto.IssuingBankDto;
import model.IssuingBank;

public class IssuingBankMapper {
    public static IssuingBank toEntity(IssuingBankDto dto) {
        return IssuingBank.builder()
                .bic(dto.getBic())
                .bin(dto.getBin())
                .abbreviatedName(dto.getAbbreviatedName())
                .build();
    }
    public static IssuingBankDto toDto(IssuingBank issuingBank) {
        return IssuingBankDto.builder()
                .bic(issuingBank.getBic())
                .bin(issuingBank.getBin())
                .abbreviatedName(issuingBank.getAbbreviatedName())
                .build();

    }
}
