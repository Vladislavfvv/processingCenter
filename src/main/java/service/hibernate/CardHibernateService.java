package service.hibernate;

import dao.hibernate.CardHibernateDaoImpl;
import model.Card;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.CardValidator;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

public class CardHibernateService implements ServiceInterfaceHibernate<Long, Card>{
    private final CardHibernateDaoImpl cardHibernateDao;

    public CardHibernateService(CardHibernateDaoImpl cardHibernateDao) {
        this.cardHibernateDao = cardHibernateDao;
    }

    @Override
    public Card create(Card value) {
        // Проверка номера карты перед сохранением
        if (!CardValidator.validateCardNumber(value.getCardNumber())) {
            // throw new IllegalArgumentException("Некорректный номер карты: не проходит проверку по алгоритму Луна.");
            return null;
        }
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Card saved = cardHibernateDao.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(Card value) {
        // Проверка номера карты перед сохранением
        if (!CardValidator.validateCardNumber(value.getCardNumber())) {
            throw new IllegalArgumentException("Некорректный номер карты: не проходит проверку по алгоритму Луна.");
        }
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardHibernateDao.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardHibernateDao.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<Card> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return cardHibernateDao.findById(session, id);
        }
    }

    @Override
    public List<Card> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return cardHibernateDao.findAll(session);
        }
    }

    @Override
    public void createTable(String sql) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            cardHibernateDao.createTableQuery(session, sql);
            tx.commit();
        }
    }

    @Override
    public boolean deleteAll(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardHibernateDao.deleteAll(session, tableName);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = cardHibernateDao.dropTable(session, tableName);
            tx.commit();
            return result;
        }
    }
}
