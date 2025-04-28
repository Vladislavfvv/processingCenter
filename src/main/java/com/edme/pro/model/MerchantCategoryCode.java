package com.edme.pro.model;

import lombok.*;
import jakarta.persistence.*;
//import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

