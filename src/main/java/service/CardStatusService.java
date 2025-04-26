package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.CardStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CardStatusService implements ServiceInterface<CardStatus, Long> {
    Logger logger = Logger.getLogger(CardStatusService.class.getName());
    private final DAOInterface<Long, CardStatus> cardStatusDAO;
    private final Connection connection;

    public CardStatusService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.cardStatusDAO = DAOFactory.getCardStatusDAO(connection);
    }

    @Override
    public CardStatus create(CardStatus cardStatus) {
        return cardStatusDAO.insert(cardStatus);
    }

    @Override
    public boolean update(CardStatus cardStatus) {
        return cardStatusDAO.update(cardStatus);
    }

    @Override
    public boolean delete(Long id) {
        return cardStatusDAO.delete(id);
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        return cardStatusDAO.findById(id);
    }

    @Override
    public List<CardStatus> findAll() {
        return cardStatusDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        cardStatusDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return cardStatusDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return cardStatusDAO.dropTable(s);
    }

}
