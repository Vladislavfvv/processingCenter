package service.hibernate;

import dao.hibernate.PaymentSystemHibernateDaoImpl;
import model.PaymentSystem;
import service.ServiceInterface;

import java.util.List;
import java.util.Optional;

public class PaymentSystemHibernateService implements ServiceInterface<PaymentSystem, Long> {

    private final PaymentSystemHibernateDaoImpl paymentSystemHibernateDaoImpl;

    public PaymentSystemHibernateService(PaymentSystemHibernateDaoImpl paymentSystemHibernateDaoImpl) {
        this.paymentSystemHibernateDaoImpl = paymentSystemHibernateDaoImpl;
    }

    @Override
    public PaymentSystem create(PaymentSystem value) {
        return paymentSystemHibernateDaoImpl.insert(value);
    }

    @Override
    public boolean update(PaymentSystem value) {
        return paymentSystemHibernateDaoImpl.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return paymentSystemHibernateDaoImpl.delete(id);
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemHibernateDaoImpl.findById(id);
    }

    @Override
    public List<PaymentSystem> findAll() {
        return paymentSystemHibernateDaoImpl.findAll();
    }

    @Override
    public void createTable(String sql) {
        paymentSystemHibernateDaoImpl.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String hql) {
        return paymentSystemHibernateDaoImpl.deleteAll(hql);
    }

    @Override
    public boolean dropTable(String tableName) {
        return paymentSystemHibernateDaoImpl.dropTable(tableName);
    }
}
