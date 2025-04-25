package service.spring;


import dao.DaoInterfaceSpring;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import mapper.AccountMapper;
import model.Account;
import model.Currency;
import model.IssuingBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountDtoSpringService {
    private final DaoInterfaceSpring<Long, Currency> currencyDao;
    private final DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
    private final DaoInterfaceSpring<Long, Account> accountDao;

    public AccountDtoSpringService(DaoInterfaceSpring<Long, Currency> currencyDao, DaoInterfaceSpring<Long, IssuingBank> issuingBankDao, DaoInterfaceSpring<Long, Account> accountDao) {
        this.currencyDao = currencyDao;
        this.issuingBankDao = issuingBankDao;
        this.accountDao = accountDao;
    }

    @Transactional
    public Optional<AccountDto> save(AccountDto accountDto) {
        if (accountDto.getId() != null) {
            Optional<Account> existsAccount = accountDao.findById(accountDto.getId());
            if (existsAccount.isPresent()) {
                log.info("Account with ID {} already exists", accountDto.getId());
                return Optional.of(AccountMapper.toDto(existsAccount.get()));
            }
        }

        //проверка есть ли такой еще
        Optional<Account> existingAccount = accountDao.findByValue(accountDto.getAccountNumber());
        if (existingAccount.isPresent()) {
            log.info("Account with ID {} already exists", accountDto.getId());
            return Optional.of(AccountMapper.toDto(existingAccount.get()));
        }
        //иначе создаем новый
        // но сразу проверяем есть ли для него currency и issuingBank
        Currency currency = currencyDao.findById(accountDto.getCurrencyId()).orElse(Currency.builder().build()); // или заглушка
        IssuingBank issuingBank = issuingBankDao.findById(accountDto.getIssuingBankId()).orElse(IssuingBank.builder().build()); //то же


        Account account = AccountMapper.toEntity(accountDto, currency, issuingBank);
        Account accountSaved = accountDao.insert(account);

        log.info("Account with ID {} saved", accountDto.getId());
        return Optional.of(AccountMapper.toDto(accountSaved));
    }

    public List<AccountDto> findAll() {
        try{
            return accountDao.findAll().stream().map(AccountMapper::toDto).collect(Collectors.toList());
        }catch (Exception e) {
            log.info("Error finding all accounts");
            return new ArrayList<>();
        }
    }

    public Optional<AccountDto> findById(Long id) {
        return accountDao.findById(id).map(AccountMapper::toDto);
    }

    public Optional<AccountDto> findByValue(String value) {
        return accountDao.findByValue(value).map(AccountMapper::toDto);
    }

    public Optional<AccountDto> update(AccountDto accountDto) {
        if (accountDto.getId() == null) {
            log.info("Account with ID {} does not exist", accountDto.getId());
            return Optional.empty();
        }
        Optional<Account> existingAccount = accountDao.findById(accountDto.getId());
        if (existingAccount.isEmpty()) {
           log.info("Account with ID {} does not exist", accountDto.getId());
           return Optional.empty();
        }
        Account accountEntity = existingAccount.get();
        Currency currency = currencyDao.findById(accountDto.getCurrencyId()).orElse(null);
        IssuingBank issuingBank = issuingBankDao.findById(accountDto.getIssuingBankId()).orElse(null);
        accountEntity.setAccountNumber(accountDto.getAccountNumber());
        accountEntity.setBalance(accountDto.getBalance());
        accountEntity.setCurrencyId(currency);
        accountEntity.setIssuingBankId(issuingBank);
        accountDao.update(accountEntity);
        log.info("Account with ID {} updated", accountDto.getId());
        return Optional.of(AccountMapper.toDto(accountEntity));
    }

    @Transactional
    public boolean delete(Long id) {
        return accountDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        return accountDao.deleteAll();
    }

    @Transactional
    public boolean dropTable() {
        return accountDao.dropTable();
    }
}
