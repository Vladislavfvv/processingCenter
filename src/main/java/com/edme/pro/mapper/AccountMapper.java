package com.edme.pro.mapper;

import com.edme.pro.dto.AccountDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;

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
        if (account == null) return null;

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());

        Currency currency = account.getCurrencyId();
        if (currency != null) {
            accountDto.setCurrencyId(currency.getId());
        }else {
            accountDto.setCurrencyId(null); // или выбрось исключение с понятным текстом
        }

       // accountDto.setCurrencyId(account.getCurrencyId().getId());
        IssuingBank issuingBank = account.getIssuingBankId();
        if (issuingBank != null) {
            accountDto.setIssuingBankId(issuingBank.getId());
        }
        else {
            accountDto.setIssuingBankId(null);
        }
      //  accountDto.setIssuingBankId(account.getIssuingBankId().getId());

        return accountDto;

    }


}
