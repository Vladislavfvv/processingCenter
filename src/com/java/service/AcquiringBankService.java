package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.AcquiringBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AcquiringBankService {

    private final DAOInterface<Long, AcquiringBank> acquiringBankDAO;
    private final Connection connection;

    public AcquiringBankService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.acquiringBankDAO = DAOFactory.getAcquiringBankDAO(connection);
    }

    public AcquiringBank createAcquiringBank(AcquiringBank acquiringBank) {
        return acquiringBankDAO.insert(acquiringBank);
    }

    public boolean updateAcquiringBank(AcquiringBank acquiringBank) {
        return acquiringBankDAO.update(acquiringBank);
    }

    public boolean deleteAcquiringBank(Long acquiringBankId) {
        return acquiringBankDAO.delete(acquiringBankId);
    }

    public Optional<AcquiringBank> getAcquiringBank(Long acquiringBankId) {
        return acquiringBankDAO.findById(acquiringBankId);
    }

    public List<AcquiringBank> getAllAcquiringBanks() {
        return acquiringBankDAO.findAll();
    }

}
