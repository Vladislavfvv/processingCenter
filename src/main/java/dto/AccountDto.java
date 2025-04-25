package dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Long id;//пришлось добавить из-за update - без него не придумал
    private String accountNumber;
    private BigDecimal balance;
    private Long currencyId;
    private Long issuingBankId;
}
