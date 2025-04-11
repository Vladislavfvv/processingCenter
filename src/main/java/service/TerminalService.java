package service;

import dao.DAOInterface;
import model.Terminal;
import dao.DAOFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TerminalService implements ServiceInterface<Terminal, Long>{
    Logger logger = Logger.getLogger(TerminalService.class.getName());
    private final DAOInterface<Long, Terminal> terminalDAO;
    private final Connection connection;

    public TerminalService(Connection connection) {
        this.terminalDAO = DAOFactory.getTerminalDAO(connection);
        this.connection = connection;
    }
    @Override
    public Terminal create(Terminal terminal) {
        return terminalDAO.insert(terminal);
    }
    @Override
    public boolean update(Terminal terminal) {
        return terminalDAO.update(terminal);
    }
    @Override
    public boolean delete(Long id) {
        return terminalDAO.delete(id);
    }
    @Override
    public Optional<Terminal> findById(Long id) {
        return terminalDAO.findById(id);
    }
    @Override
    public List<Terminal> findAll() {
        return terminalDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        terminalDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return terminalDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return terminalDAO.dropTable(s);
    }

}
