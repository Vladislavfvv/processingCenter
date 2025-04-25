package mapper;

import dto.AccountDto;
import model.Account;
import model.Currency;
import model.IssuingBank;

public class AccountMapper {
    public static Account toEntity(AccountDto dto, Currency currency, IssuingBank issuingBank) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setCurrencyId(currency);
        account.setIssuingBankId(issuingBank);

        return account;
    }

    public static AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());
        accountDto.setCurrencyId(account.getCurrencyId().getId());
        accountDto.setIssuingBankId(account.getIssuingBankId().getId());

        return accountDto;

    }


}
