package dao.spring;

import dao.DaoInterfaceSpring;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import model.IssuingBank;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class IssuingBankSpringDaoImpl implements DaoInterfaceSpring<Long, IssuingBank> {

    @PersistenceContext
    private EntityManager em;

    void setEntityManager(EntityManager em) {
        this.em = em;
    }


    @Override
    public IssuingBank insert(IssuingBank value) {
        em.persist(value);
        em.flush();
        log.info("IssuingBank added: " + value);
        return value;
    }

    @Override
    public IssuingBank update(IssuingBank value) {
        return em.merge(value);
    }

    @Override
    public boolean delete(Long id) {
        IssuingBank bank = em.find(IssuingBank.class, id);
        if (bank != null) {
            em.remove(bank);
            log.info("IssuingBank deleted: " + id);
            return true;
        } else {
            log.info("IssuingBank deleted: " + id);
            return false;
        }
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        return Optional.ofNullable(em.find(IssuingBank.class, id));
    }

    @Override
    public List<IssuingBank> findAll() {
        return em.createQuery("from IssuingBank", IssuingBank.class).getResultList();
    }

    @Override
    public boolean createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS processingcenterschema.issuing_bank
                (
                    id               bigserial primary key,
                    bic              varchar(9)   not null,
                    bin              varchar(5)   not null,
                    abbreviated_name varchar(255) not null
                    );
                """;
        try {
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
            log.info("IssuingBank table created");
            return true;
        } catch (Exception e) {
            log.info("IssuingBank table failed to create");
            em.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            em.createQuery("delete from IssuingBank").executeUpdate();
            log.info("IssuingBank table cleared");
        } catch (Exception e) {
            log.info("IssuingBank table failed to cleared");
            return false;
        }

        return false;
    }

    @Override
    public boolean dropTable() {
        try {
            em.createNativeQuery("DROP TABLE IF EXISTS processingCenterSchema.issuing_bank").executeUpdate();
            log.info("IssuingBank table dropped");
            return true;
        } catch (Exception e) {
            log.info("IssuingBank table failed to drop");
            return false;
        }
    }

    @Override
    public Optional<IssuingBank> findByValue(String name) {
        List<IssuingBank> issuingBankList  = em.createQuery("FROM IssuingBank ib WHERE ib.abbreviatedName = :name", IssuingBank.class)
                .setParameter("name", name).getResultList();
        return issuingBankList.stream().findFirst();
    }
}
