package dao.hibernate;

import dao.DAOInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CardStatus;
import model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;
import util.SqlQueryLoader;

import java.util.List;
import java.util.Optional;

@Slf4j
public class IssuingBankHibernateDaoImpl extends AbstractHibernateDao<Long, IssuingBank> {
    public IssuingBankHibernateDaoImpl() {
        super(IssuingBank.class);
    }

    @Override
    public IssuingBank insert(Session session, IssuingBank entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, IssuingBank entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        IssuingBank entity = session.get(IssuingBank.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<IssuingBank> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(IssuingBank.class, id));
    }

    @Override
    public List<IssuingBank> findAll(Session session) {
        Query<IssuingBank> query = session.createQuery("FROM IssuingBank", IssuingBank.class);
        return query.getResultList();
    }

    @Override
    public boolean createTableQuery(Session session, String sql) {
        session.createNativeQuery(sql).executeUpdate();
        return true;
    }

    @Override
    public boolean deleteAll(Session session, String tableName) {
        String hql = "DELETE FROM " + tableName;
        session.createQuery(hql).executeUpdate();
        return true;
    }

    @Override
    public boolean dropTable(Session session, String tableName) {
        session.createNativeQuery("DROP TABLE IF EXISTS " + tableName).executeUpdate();
        return true;
    }

    @Override
    public Optional<IssuingBank> findByValue(Session session, String value) {

        return Optional.ofNullable(session.get(IssuingBank.class, value));
    }
}
