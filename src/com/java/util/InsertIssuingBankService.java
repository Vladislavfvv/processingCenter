package com.java.util;

import com.java.model.IssuingBank;
import com.java.service.IssuingBankService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InsertIssuingBankService {
    private static final Logger logger = Logger.getLogger(InsertIssuingBankService.class.getName());
    private final IssuingBankService issuingBankService;


    public InsertIssuingBankService(IssuingBankService issuingBankService) {
        this.issuingBankService = issuingBankService;
    }

    public void insertIssuingBank() {
        try {

            List<IssuingBank> issuingBankList = new ArrayList<>();

            issuingBankList.add(issuingBankService.create(new IssuingBank(null, "041234569", "12345", "ОАО Приорбанк")));
            issuingBankList.add(issuingBankService.create(new IssuingBank(null, "041234570", "45256", "ОАО Сбербанк")));
            issuingBankList.add(issuingBankService.create(new IssuingBank(null, "041234571", "98725", "ЗАО МТБ Банк")));

            // Log successful insertions
            for (IssuingBank bank : issuingBankList) {
                logger.info("Inserted bank: " + bank);
            }

        } catch (Exception e) {
            logger.severe("Ошибка при вставке issuingBank: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
