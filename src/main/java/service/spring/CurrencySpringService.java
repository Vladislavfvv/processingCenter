package service.spring;

import dao.DaoInterfaceSpring;
import dto.CurrencyDto;
import model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencySpringService {
    private final DaoInterfaceSpring<Long, Currency> currencyDao;

    public CurrencySpringService(DaoInterfaceSpring<Long, Currency> currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Transactional
    public Optional<Currency> addCurrency(String digitalCode, String letterCode, String currencyName) {
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

    public Optional<Currency> getCurrencyById(Long id) {
        return currencyDao.findById(id);
    }

    public List<Currency> getAllCurrencies() {
        return currencyDao.findAll();
    }

    @Transactional
    public boolean deleteCurrency(Long id) {
        return currencyDao.delete(id);
    }

    public Optional<Currency> getCurrencyByName(String name) {
        return currencyDao.findByValue(name);
    }

    @Transactional
    public Optional<Currency> updateCurrency(Currency currency) {
        Optional<Currency> existing = currencyDao.findById(currency.getId());
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Currency updated = currencyDao.update(currency);
        return Optional.of(updated);
    }
}

//    public Optional<CurrencyDto> addCurrency(String name, String digitalCode, String letterCode) {
//        if (currencyDao.findByValue(name).isPresent()) {
//            return Optional.empty();
//        }
//        Currency currency = new Currency();
//        currency.setCurrencyName(name);
//        currency.setCurrencyDigitalCode(digitalCode);
//        currency.setCurrencyLetterCode(letterCode);
//        Currency saved = currencyDao.insert(currency);
//        return Optional.of(mapToDto(saved));
//    }
//
//    public Optional<CurrencyDto> getCurrencyById(Long id) {
//        return currencyDao.findById(id).map(this::mapToDto);
//    }
//
//    public List<CurrencyDto> getAllCurrencies() {
//        return currencyDao.findAll().stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    public boolean deleteCurrency(Long id) {
//        return currencyDao.delete(id);
//    }
//
//    public Optional<CurrencyDto> getCurrencyByName(String name) {
//        return currencyDao.findByValue(name).map(this::mapToDto);
//    }
//
//    public Optional<CurrencyDto> updateCurrency(CurrencyDto dto) {
//        Optional<Currency> existing = currencyDao.findById(dto.getId());
//        if (existing.isEmpty()) {
//            return Optional.empty();
//        }
//        Currency updatedCurrency = mapToEntity(dto);
//        Currency saved = currencyDao.update(updatedCurrency);
//        return Optional.of(mapToDto(saved));
//    }
//
//    private CurrencyDto mapToDto(Currency currency) {
//        return new CurrencyDto(
//                currency.getId(),
//                currency.getCurrencyName(),
//                currency.getCurrencyDigitalCode(),
//                currency.getCurrencyLetterCode()
//        );
//    }
//
//    private Currency mapToEntity(CurrencyDto dto) {
//        Currency currency = new Currency();
//        currency.setId(dto.getId());
//        currency.setCurrencyName(dto.getCurrencyName());
//        currency.setCurrencyDigitalCode(dto.getCurrencyDigitalCode());
//        currency.setCurrencyLetterCode(dto.getCurrencyLetterCode());
//        return currency;
//    }

//-----------------------------------------------------------
    // private final DaoInterfaceSpring<Long, Currency> currencyDao;

//    public CurrencySpringService(DaoInterfaceSpring<Long, Currency> currencyDao) {
//        this.currencyDao = currencyDao;
//    }
//
//    @Transactional
//    public Currency addCurrency(String name, String digitalCode, String letterCode) {
//        Currency currency = new Currency();
//        currency.setCurrencyName(name);
//        currency.setCurrencyDigitalCode(digitalCode);
//        currency.setCurrencyLetterCode(letterCode);
//        return currencyDao.insert(currency);
//    }
//
//
//    public Optional<Currency> getCurrencyById(Long id) {
//        return currencyDao.findById(id);
//    }
//
//    public List<Currency> getAllCurrencies() {
//        return currencyDao.findAll();
//    }
//
//    @Transactional
//    public boolean deleteCurrency(Long id) {
//        return currencyDao.delete(id);
//    }
//
//    public Optional<Currency> getCurrencyByName(String name) {
//        return currencyDao.findByValue(name);
//    }
//
//    @Transactional
//    public Currency updateCurrency(Currency currency) {
//        return currencyDao.update(currency);
//    }
//
//    private CurrencyDto mapToDto(Currency currency) {
//        return new CurrencyDto(
//                currency.getId(),
//                currency.getCurrencyName(),
//                currency.getCurrencyDigitalCode(),
//                currency.getCurrencyLetterCode()
//        );
//    }
//
//    private Currency mapToEntity(CurrencyDto dto) {
//        Currency currency = new Currency();
//        currency.setId(dto.getId());
//        currency.setCurrencyName(dto.getCurrencyName());
//        currency.setCurrencyDigitalCode(dto.getCurrencyDigitalCode());
//        currency.setCurrencyLetterCode(dto.getCurrencyLetterCode());
//        return currency;
//    }
//}
