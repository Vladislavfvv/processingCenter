package com.edme.pro.mapper;

import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.model.CardStatus;

public class CardStatusMapper {
    public static CardStatus toEntity(CardStatusDto dto) {
        return CardStatus.builder()
               // .id(dto.getId())
                .cardStatusName(dto.getCardStatusName())
                .build();
    }

    public static CardStatusDto toDto(CardStatus entity) {
        return CardStatusDto.builder()
               // .id(entity.getId())
                .cardStatusName(entity.getCardStatusName())
                .build();
    }
}
