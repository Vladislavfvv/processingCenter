package dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDto {
    private Long id;
    private String cardNumber;
    private Date expirationDate;
    private String holderName;
    private Long cardStatusId;
    private Long paymentSystemId;
    private Long accountId;
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

}
