package service.hibernate;

import dao.hibernate.IssuingBankHibernateDaoImpl;
import model.IssuingBank;
import service.ServiceInterface;

import java.util.List;
import java.util.Optional;

public class IssuingBankHibernateService implements ServiceInterface<IssuingBank, Long> {

    private final IssuingBankHibernateDaoImpl issuingBankHibernateDao;

    public IssuingBankHibernateService(IssuingBankHibernateDaoImpl issuingBankHibernateDao) {
        this.issuingBankHibernateDao = issuingBankHibernateDao;
    }

    @Override
    public IssuingBank create(IssuingBank value) {
        return issuingBankHibernateDao.insert(value);
    }

    @Override
    public boolean update(IssuingBank value) {
        return issuingBankHibernateDao.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return issuingBankHibernateDao.delete(id);
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        return issuingBankHibernateDao.findById(id);
    }

    @Override
    public List<IssuingBank> findAll() {
        return issuingBankHibernateDao.findAll();
    }

    @Override
    public void createTable(String sql) {
        issuingBankHibernateDao.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return issuingBankHibernateDao.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return issuingBankHibernateDao.dropTable(s);
    }
}
