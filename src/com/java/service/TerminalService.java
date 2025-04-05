package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.MerchantCategoryCode;
import com.java.model.Terminal;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TerminalService {
    Logger logger = Logger.getLogger(TerminalService.class.getName());
    private final DAOInterface<Long, Terminal> terminalDAO;
    private final Connection connection;

    public TerminalService(Connection connection) {
        this.terminalDAO = DAOFactory.getTerminalDAO(connection);
        this.connection = connection;
    }

    public Terminal create(Terminal terminal) {
        return terminalDAO.insert(terminal);
    }

    public boolean update(Terminal terminal) {
        return terminalDAO.update(terminal);
    }

    public boolean delete(Long id) {
        return terminalDAO.delete(id);
    }

    public Optional<Terminal> findById(Long id) {
        return terminalDAO.findById(id);
    }

    public List<Terminal> findAll() {
        return terminalDAO.findAll();
    }

    // Метод для очистки всех записей из таблицы
    public boolean clearTerminalDAO() {
        try {
            return terminalDAO.deleteAll("terminal");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    // Метод для удаления таблицы
    public boolean removeTerminalDAO(Terminal terminal) {
        try {
            return terminalDAO.dropTable("terminal");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }
}
