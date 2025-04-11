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
@Table(name = "merchant_category_code")
public class MerchantCategoryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mcc", length = 4, nullable = false)
    private String mcc;
    @Column(name = "mcc_name", length = 255, nullable = false)
    private String mcc_name;


}

