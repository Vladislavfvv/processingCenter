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
@Table(name = "terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "terminal_id", length = 9, nullable = false)
    private String terminalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mcc_id", referencedColumnName = "id", nullable = false)
    private MerchantCategoryCode mccId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos_id", referencedColumnName = "id", nullable = false)
    private SalesPoint posId;

}
