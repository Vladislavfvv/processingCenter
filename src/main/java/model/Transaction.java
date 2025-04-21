package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_date", nullable = false )
    private Date transactionDate;
    @Column(name = "sum")
    private BigDecimal sum;
    @Column(name = "transaction_name", length = 255, nullable = false)
    private String transactionName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account accountId; //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id", nullable = false)
    private TransactionType transactionTypeId;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", referencedColumnName = "id", nullable = false)
    private Card cardId;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_id", referencedColumnName = "id", nullable = false)
    private Terminal terminalId;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_code_id", referencedColumnName = "id", nullable = false)
    private ResponseCode responseCodeId;  //change!!!
    @Column(name = "authorization_code", length = 6, nullable = false)
    private String authorizationCode;
    @Column(name = "received_from_issuing_bank", nullable = false)
    private Timestamp receivedFromIssuingBank;
    @Column(name = "sent_to_issuing_bank", nullable = false)
    private Timestamp sentToIssuingBank;

}
