package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.MerchantCategoryCode;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MerchantCategoryCodeService implements ServiceInterface<MerchantCategoryCode, Long>{
    private static final Logger logger = Logger.getLogger(MerchantCategoryCodeService.class.getName());
    private final DAOInterface<Long, MerchantCategoryCode> merchantCategoryCodeDAO;
    private final Connection connection;

    public MerchantCategoryCodeService(Connection connection) {
        this.merchantCategoryCodeDAO = DAOFactory.getMerchantCategoryCodeDAO(connection);
        this.connection = connection;
    }
    @Override
    public MerchantCategoryCode create(MerchantCategoryCode merchantCategoryCode) {
        return merchantCategoryCodeDAO.insert(merchantCategoryCode);
    }
    @Override
    public boolean update(MerchantCategoryCode merchantCategoryCode) {
        return merchantCategoryCodeDAO.update(merchantCategoryCode);
    }
    @Override
    public boolean delete(Long id) {
        return merchantCategoryCodeDAO.delete(id);
    }
    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        return merchantCategoryCodeDAO.findById(id);
    }
    @Override
    public List<MerchantCategoryCode> findAll() {
        return merchantCategoryCodeDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        merchantCategoryCodeDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return merchantCategoryCodeDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return merchantCategoryCodeDAO.dropTable(s);
    }









//    // Метод для очистки всех записей из таблицы
//    public boolean clearMerchantCategoryCodeDAO() {
//        try {
//            return merchantCategoryCodeDAO.deleteAll("merchant_category_code");
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//            return false;
//        }
//    }
//
//    // Метод для удаления таблицы
//    public boolean removeMerchantCategoryCodeDAO(MerchantCategoryCode MerchantCategoryCode) {
//        try {
//            return merchantCategoryCodeDAO.dropTable("merchant_category_code");
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//            return false;
//        }
//    }
}
