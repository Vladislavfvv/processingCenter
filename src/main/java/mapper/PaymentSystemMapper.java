package mapper;

import dto.PaymentSystemDto;
import model.PaymentSystem;

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
