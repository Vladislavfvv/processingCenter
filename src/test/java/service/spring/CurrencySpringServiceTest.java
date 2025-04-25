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


//    @Test
//    void addCurrency_success() {
//        Currency currency = new Currency(1L, "Ruble", "643", "RUB");
//        when(currencyDao.insert(any(Currency.class))).thenReturn(currency);
//
//        CurrencyDto result = currencyService.addCurrency("Ruble", "643", "RUB");
//
//        assertNotNull(result);
//        assertEquals("Ruble", result.getCurrencyName());
//        assertEquals("643", result.getCurrencyDigitalCode());
//        assertEquals("RUB", result.getCurrencyLetterCode());
//        verify(currencyDao, times(1)).insert(any(Currency.class));
//    }
//
//    @Test
//    void getCurrencyById_found() {
//        Currency currency = new Currency(1L, "Euro", "978", "EUR");
//        when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));
//
//        Optional<CurrencyDto> result = currencyService.getCurrencyById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("Euro", result.get().getCurrencyName());
//    }
//
//    @Test
//    void getCurrencyById_notFound() {
//        when(currencyDao.findById(99L)).thenReturn(Optional.empty());
//
//        Optional<CurrencyDto> result = currencyService.getCurrencyById(99L);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void getAllCurrencies_returnsList() {
//        List<Currency> currencies = List.of(
//                new Currency(1L, "Dollar", "840", "USD"),
//                new Currency(2L, "Yen", "392", "JPY")
//        );
//        when(currencyDao.findAll()).thenReturn(currencies);
//
//        List<CurrencyDto> result = currencyService.getAllCurrencies();
//
//        assertEquals(2, result.size());
//        assertEquals("Dollar", result.get(0).getCurrencyName());
//    }
//
//    @Test
//    void deleteCurrency_success() {
//        when(currencyDao.delete(1L)).thenReturn(true);
//
//        boolean result = currencyService.deleteCurrency(1L);
//
//        assertTrue(result);
//        verify(currencyDao, times(1)).delete(1L);
//    }
//
//    @Test
//    void getCurrencyByName_found() {
//        Currency currency = new Currency(1L, "Pound", "826", "GBP");
//        when(currencyDao.findByValue("Pound")).thenReturn(Optional.of(currency));
//
//        Optional<CurrencyDto> result = currencyService.getCurrencyByName("Pound");
//
//        assertTrue(result.isPresent());
//        assertEquals("Pound", result.get().getCurrencyName());
//    }
//
//    @Test
//    void updateCurrency_success() {
//        CurrencyDto dto = new CurrencyDto(1L, "Swiss Franc", "756", "CHF");
//        Currency updatedCurrency = new Currency(1L, "Swiss Franc", "756", "CHF");
//        when(currencyDao.update(any(Currency.class))).thenReturn(updatedCurrency);
//
//        CurrencyDto result = currencyService.updateCurrency(dto);
//
//        assertNotNull(result);
//        assertEquals("Swiss Franc", result.getCurrencyName());
//    }



    //----------------------------------------------------------------------------
  //  @Test
//    void addCurrency_success() {
//        Currency currency = new Currency(1L, "Ruble", "643", "RUB");
//        when(currencyDao.findByValue("Ruble")).thenReturn(Optional.empty());
//        when(currencyDao.insert(any(Currency.class))).thenReturn(currency);
//
//        Optional<Currency> result = currencyService.addCurrency("Ruble", "643", "RUB");
//
//        assertTrue(result.isPresent());
//        assertEquals("Ruble", result.get().getCurrencyName());
//        assertEquals("643", result.get().getCurrencyDigitalCode());
//        assertEquals("RUB", result.get().getCurrencyLetterCode());
//        verify(currencyDao, times(1)).insert(any(Currency.class));
//    }
//
//    @Test
//    void addCurrency_alreadyExists() {
//        when(currencyDao.findByValue("Ruble")).thenReturn(Optional.of(new Currency()));
//
//        Optional<Currency> result = currencyService.addCurrency("Ruble", "643", "RUB");
//
//        assertTrue(result.isEmpty());
//        verify(currencyDao, never()).insert(any(Currency.class));
//    }
//
//    @Test
//    void getCurrencyById_found() {
//        Currency currency = new Currency(1L, "Euro", "978", "EUR");
//        when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));
//
//        Optional<Currency> result = currencyService.getCurrencyById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("Euro", result.get().getCurrencyName());
//    }
//
//    @Test
//    void getCurrencyById_notFound() {
//        when(currencyDao.findById(99L)).thenReturn(Optional.empty());
//
//        Optional<Currency> result = currencyService.getCurrencyById(99L);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void getAllCurrencies_returnsList() {
//        List<Currency> currencies = List.of(
//                new Currency(1L, "Dollar", "840", "USD"),
//                new Currency(2L, "Yen", "392", "JPY")
//        );
//        when(currencyDao.findAll()).thenReturn(currencies);
//
//        List<Currency> result = currencyService.getAllCurrencies();
//
//        assertEquals(2, result.size());
//        assertEquals("Dollar", result.get(0).getCurrencyName());
//        assertEquals("Yen", result.get(1).getCurrencyName());
//    }
//
//    @Test
//    void deleteCurrency_success() {
//        when(currencyDao.delete(1L)).thenReturn(true);
//
//        boolean result = currencyService.deleteCurrency(1L);
//
//        assertTrue(result);
//        verify(currencyDao, times(1)).delete(1L);
//    }
//
//    @Test
//    void getCurrencyByName_found() {
//        Currency currency = new Currency(1L, "Pound", "826", "GBP");
//        when(currencyDao.findByValue("Pound")).thenReturn(Optional.of(currency));
//
//        Optional<Currency> result = currencyService.getCurrencyByName("Pound");
//
//        assertTrue(result.isPresent());
//        assertEquals("Pound", result.get().getCurrencyName());
//    }
//
//    @Test
//    void getCurrencyByName_notFound() {
//        when(currencyDao.findByValue("Franc")).thenReturn(Optional.empty());
//
//        Optional<Currency> result = currencyService.getCurrencyByName("Franc");
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void updateCurrency_success() {
//        Currency currency = new Currency(1L, "Swiss Franc", "756", "CHF");
//        when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));
//        when(currencyDao.update(any(Currency.class))).thenReturn(currency);
//
//        Optional<Currency> result = currencyService.updateCurrency(currency);
//
//        assertTrue(result.isPresent());
//        assertEquals("Swiss Franc", result.get().getCurrencyName());
//        verify(currencyDao, times(1)).update(any(Currency.class));
//    }
//
//    @Test
//    void updateCurrency_notFound() {
//        Currency currency = new Currency(2L, "Peso", "484", "MXN");
//        when(currencyDao.findById(2L)).thenReturn(Optional.empty());
//
//        Optional<Currency> result = currencyService.updateCurrency(currency);
//
//        assertTrue(result.isEmpty());
//        verify(currencyDao, never()).update(any(Currency.class));
//    }
}