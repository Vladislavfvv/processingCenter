package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*;
//import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "currency", schema = "processingcenterschema" )
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

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyLetterCode='" + currencyLetterCode + '\'' +
                ", currencyDigitalCode='" + currencyDigitalCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}


