package service.spring;

import dao.DaoInterfaceSpring;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CurrencySpringServiceTest {
    @Mock
    private DaoInterfaceSpring<Long, Currency> currencyDao;

    @InjectMocks
    private CurrencySpringService currencyService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @BeforeEach
    public void setUp() {
        // создаем мок объекта DaoInterfaceSpring
        currencyDao = Mockito.mock(DaoInterfaceSpring.class);
        // передача мок в сервис
        currencyService = new CurrencySpringService(currencyDao);
    }
    @Test
    public void testAddCurrency_whenCurrencyNotExists_shouldAdd() {
        // подготавливаем данные
        String digitalCode = "643";
        String letterCode = "RUB";
        String name = "Russian Ruble";

        // задаём поведение мока: findByValue возвращает пустой Optional
        Mockito.when(currencyDao.findByValue(name)).thenReturn(Optional.empty());

        // имитируем поведение метода insert
        Currency currency = new Currency();
        currency.setCurrencyName(name);
        currency.setCurrencyDigitalCode(digitalCode);
        currency.setCurrencyLetterCode(letterCode);
        currency.setId(1L);

        // когда insert вызывается, возвращаем нашу валюту
        Mockito.when(currencyDao.insert(Mockito.any())).thenReturn(currency);

        // вызываем метод сервиса
        Optional<Currency> result = currencyService.save(name, digitalCode, letterCode);

        // проверяем, что результат не пустой
        assertTrue(result.isPresent());
        // проверяем, что имя валюты совпадает с ожидаемым
        assertEquals(name, result.get().getCurrencyName());
    }

    @Test
    public void testSaveExists_shouldReturnEmpty() {
        // подготавливаем данные
        String name = "Russian Ruble";

        // имитируем наличие валюты с таким именем
        Mockito.when(currencyDao.findByValue(name)).thenReturn(Optional.of(new Currency()));

        // вызываем метод
        Optional<Currency> result = currencyService.save(name, "643", "RUB");

        // ожидаем, что результат будет пустым
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFind() {
        // подготавливаем тестовые данные
        Long id = 1L;
        Currency currency = new Currency();
        currency.setId(id);

        // задаём поведение мока
        Mockito.when(currencyDao.findById(id)).thenReturn(Optional.of(currency));

        // вызываем метод сервиса
        Optional<Currency> result = currencyService.findById(id);

        // проверяем, что результат найден
        assertTrue(result.isPresent());
        // проверяем, что id совпадает
        assertEquals(id, result.get().getId());
    }

    @Test
    public void testDelete_shouldReturnTrue() {
        // задаём поведение мока: удаление успешно
        Mockito.when(currencyDao.delete(1L)).thenReturn(true);

        // вызываем метод сервиса
        boolean result = currencyService.delete(1L);

        // ожидаем true
        assertTrue(result);
    }

    @Test
    public void testUpdateCurrency_whenExists_shouldUpdate() {
        // подготавливаем данные
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCurrencyName("Russian Ruble");

        // задаём поведение мока: findById возвращает валюту
        Mockito.when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));
        // задаём поведение update
        Mockito.when(currencyDao.update(currency)).thenReturn(currency);

        currency.setCurrencyName("Ruble");//смена названия перед обновлением
        // вызываем метод сервиса
        Optional<Currency> result = currencyService.update(currency);

        // проверяем, что обновление прошло успешно
        assertTrue(result.isPresent());
        assertEquals("Ruble", result.get().getCurrencyName());
    }

    @Test
    public void testUpdate_whenNotExists_shouldReturnEmpty() {
        // подготавливаем данные
        Currency currency = new Currency();
        currency.setId(1L);

        // задаём поведение мока: findById возвращает пустой Optional
        Mockito.when(currencyDao.findById(1L)).thenReturn(Optional.empty());

        // вызываем метод
        Optional<Currency> result = currencyService.update(currency);

        // ожидаем пустой результат
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAll_shouldReturnList() {
        // подготавливаем тестовый список валют
        Currency currency1 = new Currency();
        Currency currency2 = new Currency();
        List<Currency> currencies = List.of(currency1, currency2);

        // задаём поведение мока
        Mockito.when(currencyDao.findAll()).thenReturn(currencies);

        // вызываем метод
        List<Currency> result = currencyService.findAll();

        // проверяем размер списка
        assertEquals(2, result.size());
    }

}