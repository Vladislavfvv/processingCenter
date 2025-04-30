package com.edme.pro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.model.Card;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class CardSpringDaoImpl implements DaoInterfaceSpring<Long, Card> {

    @PersistenceContext
    private EntityManager em;

    void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Card insert(Card value) {
        em.persist(value);
        em.flush();
        log.info("Insert card : {}", value);
        return value;
    }

    @Override
    public Card update(Card value) {
        return em.merge(value);
    }

    @Override
    public boolean delete(Long id) {
        Card card = em.find(Card.class, id);
        if (card != null) {
            em.remove(card);
            log.info("Delete card : {}", id);
            return true;
        } else {
            log.info("Not deleted card : {}", id);
            return false;
        }
    }

    @Override
    public Optional<Card> findById(Long id) {
        return Optional.ofNullable(em.find(Card.class, id));
    }

    @Override
  //  public List<Card> findAll() { return em.createQuery("select c from Card c", Card.class).getResultList();   }
    public List<Card> findAll() {
        return em.createQuery("""
                select c from Card c
                LEFT JOIN FETCH c.cardStatusId
                LEFT JOIN FETCH c.paymentSystemId
                LEFT JOIN FETCH c.accountId
                """, Card.class)
                .getResultList();   }

    @Override
    public boolean createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS processingcenterschema.card
                (
                    id                         bigserial primary key,
                    card_number                varchar(50),
                    expiration_date            date,
                    holder_name                varchar(50),
                    card_status_id             bigint REFERENCES processingcenterschema.card_status (id) ON DELETE CASCADE
                                                                                  ON UPDATE CASCADE,
                    payment_system_id          bigint REFERENCES processingcenterschema.payment_system (id) ON DELETE CASCADE
                                                                                  ON UPDATE CASCADE,
                    account_id                 bigint REFERENCES processingcenterschema.account ON DELETE CASCADE
                                                                                  ON UPDATE CASCADE,
                    received_from_issuing_bank timestamp,
                    sent_to_issuing_bank       timestamp
                    );
                """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Create table processingcenterschema.card");
            return true;
        } catch (Exception e) {
            log.info("Create table processingcenterschema.card");
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            em.createQuery("DELETE FROM Card").executeUpdate();
            log.info("Delete all Cards");
            return true;
        } catch (Exception e) {
            log.info("Delete all Cards");
            return false;
        }
    }

    @Override
    public boolean dropTable() {
       try {
           em.createNativeQuery("DROP TABLE IF EXISTS CARD CASCADE").executeUpdate();
           log.info("Drop table Card");
           return true;
       }catch (Exception e){
           log.info("Dont drop table Card");
           return false;
       }
    }

    @Override
    public Optional<Card> findByValue(String name) {
        List<Card> cardList = em.createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class)
                .setParameter("name", name)
                .getResultList();
        return cardList.stream().findFirst();
    }
}
