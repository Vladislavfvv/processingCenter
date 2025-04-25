package dao.spring;

import dao.DaoInterfaceSpring;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import model.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class AccountSpringDaoImpl implements DaoInterfaceSpring<Long, Account> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Account insert(Account value) {
        em.persist(value);
        em.flush(); // гарантирует выполнение SQL
        log.info("Inserted account " + value);
        return value;
    }

    @Override
    public Account update(Account value) {
        return em.merge(value);
    }

    @Override
    public boolean delete(Long id) {
        Account account = em.find(Account.class, id);
        if (account != null) {
            em.remove(account);
            log.info("Deleted account " + id);
            return true;
        } else {
            log.info("Account with id = {} not deleted ", id);
            return false;
        }

    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(em.find(Account.class, id));
    }

    @Override
    public List<Account> findAll() {
        return em.createQuery("FROM Account", Account.class).getResultList();
    }

    @Override
    public boolean createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS processingcenterschema.account
                (
                    id              bigserial UNIQUE primary key,
                    account_number  varchar(50) not null,
                    balance         decimal,
                    currency_id     bigint REFERENCES processingcenterschema.currency (id) ON DELETE CASCADE
                                                                    ON UPDATE CASCADE,
                    issuing_bank_id bigint REFERENCES processingcenterschema.issuing_bank (id) ON DELETE CASCADE
                                                                    ON UPDATE CASCADE
                    );
                """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Table created");
            return true;
        } catch (Exception e) {
            log.info("Table not created");
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            em.createQuery("DELETE FROM Account").executeUpdate();
            log.info("Table {} cleared", Account.class.getSimpleName());
            return true;
        } catch (Exception e) {
            log.info("Table {} not cleared", Account.class.getSimpleName());
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
            em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.account CASCADE").executeUpdate();
            log.info("Table {} dropped", Account.class.getSimpleName());
            return true;
        } catch (Exception e) {
            log.info("Table {} not dropped", Account.class.getSimpleName());
            return false;
        }
    }

    @Override
    public Optional<Account> findByValue(String name) {
        List<Account> accountList = em.createQuery("FROM Account a WHERE a.accountNumber=:name", Account.class)
                .setParameter("name", name)
                .getResultList();

        return accountList.stream().findFirst();
    }
}
