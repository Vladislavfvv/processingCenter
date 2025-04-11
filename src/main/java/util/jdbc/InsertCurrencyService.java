package util.jdbc;

import service.CurrencyService;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;

import model.Currency;


public class InsertCurrencyService {
    private static final Logger logger = Logger.getLogger(InsertCardStatusService.class.getName());
    private final CurrencyService currencyService;

    public InsertCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void insertCurrencyServices() {
        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(currencyService.create(new Currency(null, "643", "RUB", "Russian Ruble")));
        currencyList.add(currencyService.create(new Currency(null, "980", "UAN", "Hryvnia")));
        currencyList.add(currencyService.create(new Currency(null, "840", "USD", "US Dollar")));
        currencyList.add(currencyService.create(new Currency(null, "978", "EUR", "Euro")));
        currencyList.add(currencyService.create(new Currency(null, "392", "JPY", "Yen")));
        currencyList.add(currencyService.create(new Currency(null, "156", "CNY", "Yuan Renminbi")));
        currencyList.add(currencyService.create(new Currency(null, "826", "GBP", "Pound Sterling")));


        for (Currency c : currencyList) {
            // Проверяем, существует ли уже валюта с таким кодом
            // if (currencyService.getCurrencyByCode(c.getCurrencyCode()) == null) {
            // Если не существует, добавляем в базу данных
            currencyService.create(c);
            logger.info("Валюта с кодом " + c.getCurrencyDigitalCode() + " и названием " + c.getCurrencyName() + " добавлена.");
            //    } else {
            //        logger.warning("Валюта с кодом " + c.getCurrencyCode() + " уже существует.");
            //   }
        }

    }
}
