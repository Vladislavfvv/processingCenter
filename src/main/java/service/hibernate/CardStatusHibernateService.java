package service.hibernate;

import dao.hibernate.CardStatusHibernateDaoImpl;
import lombok.extern.slf4j.Slf4j;
import model.CardStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.ServiceInterface;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;


@Slf4j
public class CardStatusHibernateService implements ServiceInterface<CardStatus, Long> {
    private final CardStatusHibernateDaoImpl cardStatusHibernateDao;


    public CardStatusHibernateService(CardStatusHibernateDaoImpl cardStatusHibernateDao) {
        this.cardStatusHibernateDao = cardStatusHibernateDao;
    }


    @Override
    public CardStatus create(CardStatus value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            CardStatus saved = cardStatusHibernateDao.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(CardStatus value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardStatusHibernateDao.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardStatusHibernateDao.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return cardStatusHibernateDao.findById(session, id);
        }
    }

    @Override
    public List<CardStatus> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return cardStatusHibernateDao.findAll(session);
        }
    }

    @Override
    public void createTable(Session session, String sql) {
        cardStatusHibernateDao.createTableQuery(session, sql);
    }

    @Override
    public boolean deleteAll(Session session, String sql) {
        return cardStatusHibernateDao.deleteAll(session, sql);
    }

    @Override
    public boolean dropTable(Session session, String sql) {
        return cardStatusHibernateDao.dropTable(session, sql);
    }
}
