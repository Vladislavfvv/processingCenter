package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Where(clause = "deleted=false")
@Table(name = "account") // Название таблицы в базе данных
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", length = 255, nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "issuing_bank_id", referencedColumnName = "id", nullable = false)
    private IssuingBank issuingBankId;
}
