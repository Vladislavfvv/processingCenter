package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
//@Where(clause = "deleted=false")//обеспечивает soft delete для одной строки из базы
//для его использования необходимо добавить поле-флаг - смотри снизу закоммменчено и этот флаг менять в зависимости от статуса удалено или нет
@Table(name = "account") // Название таблицы в базе данных
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", length = 255, nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "issuing_bank_id", referencedColumnName = "id", nullable = false)
    private IssuingBank issuingBankId;


    // @Column(name = "deleted", nullable = false) - для мягкого удаления
    //    private boolean deleted = false; - для мягкого удаления и потом сеттерами указывать account.setDeleted(true); - удалено
    // эта запись сохранится в таблице, но потом при выводе ее не покажет
}
