package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Getter
@Setter
@Entity
@Table(name = "issuing_bank")
public class IssuingBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bic", length = 9, nullable = false)
    private String bic;
    @Column(name = "bin", length = 5, nullable = false)
    private String bin;
    @Column(name = "abbreviated_name", length = 255, nullable = false)
    private String abbreviatedName;

}
