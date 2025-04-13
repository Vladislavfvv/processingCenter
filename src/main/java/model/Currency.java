package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "currency_digital_code", length = 3, nullable = false)
    private String currencyDigitalCode;
    @Column(name = "currency_letter_code", length = 3, nullable = false)
    private String currencyLetterCode;
    @Column(name = "currency_name", length = 255, nullable = false)
    private String currencyName;
}


