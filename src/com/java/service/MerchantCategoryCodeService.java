package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.MerchantCategoryCode;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MerchantCategoryCodeService {
    Logger logger = Logger.getLogger(MerchantCategoryCodeService.class.getName());
    private final DAOInterface<Long, MerchantCategoryCode> merchantCategoryCodeDAO;
    private final Connection connection;

    public MerchantCategoryCodeService(Connection connection) {
        this.merchantCategoryCodeDAO = DAOFactory.getMerchantCategoryCodeDAO(connection);
        this.connection = connection;
    }

    public MerchantCategoryCode create(MerchantCategoryCode merchantCategoryCode) {
        return merchantCategoryCodeDAO.insert(merchantCategoryCode);
    }

    public boolean update(MerchantCategoryCode merchantCategoryCode) {
        return merchantCategoryCodeDAO.update(merchantCategoryCode);
    }

    public boolean delete(Long id) {
        return merchantCategoryCodeDAO.delete(id);
    }

    public Optional<MerchantCategoryCode> findById(Long id) {
        return merchantCategoryCodeDAO.findById(id);
    }

    public List<MerchantCategoryCode> findAll() {
        return merchantCategoryCodeDAO.findAll();
    }

    // Метод для очистки всех записей из таблицы
    public boolean clearMerchantCategoryCodeDAO() {
        try {
            return merchantCategoryCodeDAO.deleteAll("merchant_category_code");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    // Метод для удаления таблицы
    public boolean removeMerchantCategoryCodeDAO(MerchantCategoryCode MerchantCategoryCode) {
        try {
            return merchantCategoryCodeDAO.dropTable("merchant_category_code");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }
}
