package dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentSystemDto {
    private Long id;
    private String paymentSystemName;
}
