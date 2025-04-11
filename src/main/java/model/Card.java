package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Getter
@Setter
@Entity
@Table(name = "card")
public class Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_number", length = 50, nullable = false)
    private String cardNumber;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "holder_name", length = 50, nullable = false)
    private String holderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_status_id", referencedColumnName = "id", nullable = false)
    private CardStatus cardStatusId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_system_id", referencedColumnName = "id", nullable = false)
    private PaymentSystem paymentSystemId; //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account accountId; //change!!!
    //@OneToOne(cascade = CascadeType.ALL)
    @Column(name = "received_from_issuing_bank")
    private Timestamp receivedFromIssuingBank;
    @Column(name = "sent_to_issuing_bank")
    private Timestamp sentToIssuingBank;


    public Card(String cardNumber, Date expirationDate, String holderName,
                CardStatus cardStatusId, PaymentSystem paymentSystemId, Account accountId,
                Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.holderName = holderName;
        this.cardStatusId = cardStatusId;
        this.paymentSystemId = paymentSystemId;
        this.accountId = accountId;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

}
