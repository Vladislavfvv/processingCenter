package dao.spring;


import dao.DaoInterfaceSpring;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import model.CardStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class CardStatusSpringDaoImpl implements DaoInterfaceSpring<Long, CardStatus> {
    @PersistenceContext
    private EntityManager em;


    void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public CardStatus insert(CardStatus value) {
        em.persist(value);
        em.flush(); // гарантирует выполнение SQL
        log.info("Persisting CardStatus: {}", value);
        return value;
    }

    @Override
    public CardStatus update(CardStatus value) {
        return em.merge(value);
    }

    @Override
    public boolean delete(Long id) {
        CardStatus cardStatus = em.find(CardStatus.class, id);
        if (cardStatus != null) {
            em.remove(cardStatus);
            log.info("Removed CardStatus with id {}", id);
            return true;
        }
        log.info("Not removed CardStatus with id {}", id);
        return false;
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        return Optional.ofNullable(em.find(CardStatus.class, id));
    }

    @Override
    public List<CardStatus> findAll() {
        return em.createQuery("FROM CardStatus", CardStatus.class).getResultList();
    }

    @Override
    public boolean createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS processingcenterschema.card_status
                (
                    id               bigserial primary key,
                    card_status_name varchar(255) UNIQUE not null
                );
                """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Table {} created", CardStatusSpringDaoImpl.class.getSimpleName());
            return true;
        } catch (Exception e) {
            log.info("Table {} not created", CardStatusSpringDaoImpl.class.getSimpleName());
            return false;
        }


    }

    @Override
    public boolean deleteAll() {
        try {
            Query query = em.createNativeQuery("DELETE FROM processingcenterschema.card_status");
            int result = query.executeUpdate();
            return result > 0;  // return true if records were deleted, otherwise false
        } catch (Exception e) {
            log.info("Info from table {} not cleared", CardStatusSpringDaoImpl.class.getSimpleName());
            return false;
        }

    }

    @Override
    public boolean dropTable() {
        try {
            em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.card_status CASCADE").executeUpdate();
            log.info("Info from table {} dropped", CardStatusSpringDaoImpl.class.getSimpleName());
            return true;
        } catch (Exception e) {
            log.error("Error dropping table {}", CardStatusSpringDaoImpl.class.getSimpleName());
            return false;
        }

    }

    @Override
    public Optional<CardStatus> findByValue(String name) {
        List<CardStatus> cardStatuses = em.createQuery("FROM CardStatus cs WHERE cs.cardStatusName = :name", CardStatus.class)
                .setParameter("name", name)
                .getResultList();
        return cardStatuses.stream().findFirst();
    }
}
