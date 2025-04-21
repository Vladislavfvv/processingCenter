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
@Table(name = "transaction_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_type_name", length = 255, nullable = false)
    private String transactionTypeName;
    @Column(name = "operator", length = 1, nullable = false)
    private String operator;

}
