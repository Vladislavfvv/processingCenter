package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.CardStatus;
import model.PaymentSystem;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PaymentSystemService implements ServiceInterface<PaymentSystem, Long> {
    Logger logger = Logger.getLogger(PaymentSystemService.class.getName());
    private final DAOInterface<Long, PaymentSystem> paymentSystemDAO;
    private final Connection connection;

    public PaymentSystemService(Connection connection) {
        this.paymentSystemDAO = DAOFactory.getPaymentSystemDAO(connection);
        this.connection = connection;
    }

    @Override
    public PaymentSystem create(PaymentSystem paymentSystem) {
        return paymentSystemDAO.insert(paymentSystem);
    }

    @Override
    public boolean update(PaymentSystem paymentSystem) {
        return paymentSystemDAO.update(paymentSystem);
    }

    @Override
    public boolean delete(Long id) {
        return paymentSystemDAO.delete(id);
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemDAO.findById(id);
    }

    @Override
    public List<PaymentSystem> findAll() {
        return paymentSystemDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        paymentSystemDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return paymentSystemDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return paymentSystemDAO.dropTable(s);
    }

}
