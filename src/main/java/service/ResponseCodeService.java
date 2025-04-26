package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.ResponseCode;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ResponseCodeService implements ServiceInterface<ResponseCode, Long> {
    Logger logger = Logger.getLogger(ResponseCodeService.class.getName());
    private final DAOInterface<Long, ResponseCode> responseCodeDAO;
    private final Connection connection;

    public ResponseCodeService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.responseCodeDAO = DAOFactory.getResponseCodeDAO(connection);
    }
    @Override
    public ResponseCode create(ResponseCode responseCode) {
        return responseCodeDAO.insert(responseCode);
    }
    @Override
    public boolean update(ResponseCode responseCode) {
        return responseCodeDAO.update(responseCode);
    }
    @Override
    public boolean delete(Long id) {
        return responseCodeDAO.delete(id);
    }
    @Override
    public Optional<ResponseCode> findById(Long id) {
        return responseCodeDAO.findById(id);
    }
    @Override
    public List<ResponseCode> findAll() {
        return responseCodeDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        responseCodeDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return responseCodeDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return responseCodeDAO.dropTable(s);
    }

}
