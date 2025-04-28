package com.edme.pro.mapper;

import com.edme.pro.dto.PaymentSystemDto;
import com.edme.pro.model.PaymentSystem;

public class PaymentSystemMapper {
    public static PaymentSystem toEntity(PaymentSystemDto dto) {
        return PaymentSystem.builder()
                .id(dto.getId())
                .paymentSystemName(dto.getPaymentSystemName())
                .build();
    }
    public static PaymentSystemDto toDto(PaymentSystem entity) {
        return PaymentSystemDto.builder()
                .id(entity.getId())
                .paymentSystemName(entity.getPaymentSystemName())
                .build();

    }
}
