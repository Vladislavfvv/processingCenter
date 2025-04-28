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
@Table(name = "payment_system")
public class PaymentSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_system_name", length = 50, nullable = false)
    private String paymentSystemName;
}
