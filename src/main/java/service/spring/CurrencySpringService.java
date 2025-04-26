package service.spring;

import dao.DaoInterfaceSpring;

import model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CurrencySpringService {
    private final DaoInterfaceSpring<Long, Currency> currencyDao;

    public CurrencySpringService(DaoInterfaceSpring<Long, Currency> currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Transactional
    public Optional<Currency> save(String digitalCode, String letterCode, String currencyName) {
        if (currencyDao.findByValue(currencyName).isPresent()) {
            return Optional.empty();
        }
        Currency currency = new Currency();
        currency.setCurrencyDigitalCode(digitalCode);
        currency.setCurrencyLetterCode(letterCode);
        currency.setCurrencyName(currencyName);
        Currency saved = currencyDao.insert(currency);
        return Optional.ofNullable(saved);
    }

    public Optional<Currency> findById(Long id) {
        return currencyDao.findById(id);
    }

    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    @Transactional
    public boolean delete(Long id) {
        return currencyDao.delete(id);
    }

    public Optional<Currency> findByValue(String name) {
        return currencyDao.findByValue(name);
    }

    @Transactional
    public Optional<Currency> update(Currency currency) {
        Optional<Currency> existing = currencyDao.findById(currency.getId());
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Currency updated = currencyDao.update(currency);
        return Optional.of(updated);
    }

    @Transactional
    public boolean deleteAll() {
        return currencyDao.deleteAll();
    }

    @Transactional
    public boolean dropTable() {
        return currencyDao.dropTable();
    }

}


