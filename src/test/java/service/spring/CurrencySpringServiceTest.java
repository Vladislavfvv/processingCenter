package service.spring;

import dao.DaoInterfaceSpring;
import dto.CurrencyDto;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencySpringServiceTest {
    @Mock
    private DaoInterfaceSpring<Long, Currency> currencyDao;

    @InjectMocks
    private CurrencySpringService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    void addCurrency_success() {
        Currency currency = new Currency(1L, "Ruble", "643", "RUB");
        when(currencyDao.findByValue("Ruble")).thenReturn(Optional.empty());
        when(currencyDao.insert(any(Currency.class))).thenReturn(currency);

        Optional<Currency> result = currencyService.addCurrency("Ruble", "643", "RUB");

        assertTrue(result.isPresent());
        assertEquals("Ruble", result.get().getCurrencyName());
        assertEquals("643", result.get().getCurrencyDigitalCode());
        assertEquals("RUB", result.get().getCurrencyLetterCode());
        verify(currencyDao, times(1)).insert(any(Currency.class));
    }

    @Test
    void addCurrency_alreadyExists() {
        when(currencyDao.findByValue("Ruble")).thenReturn(Optional.of(new Currency()));

        Optional<Currency> result = currencyService.addCurrency("Ruble", "643", "RUB");

        assertTrue(result.isEmpty());
        verify(currencyDao, never()).insert(any(Currency.class));
    }

    @Test
    void getCurrencyById_found() {
        Currency currency = new Currency(1L, "Euro", "978", "EUR");
        when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));

        Optional<Currency> result = currencyService.getCurrencyById(1L);

        assertTrue(result.isPresent());
        assertEquals("Euro", result.get().getCurrencyName());
    }

    @Test
    void getCurrencyById_notFound() {
        when(currencyDao.findById(99L)).thenReturn(Optional.empty());

        Optional<Currency> result = currencyService.getCurrencyById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllCurrencies_returnsList() {
        List<Currency> currencies = List.of(
                new Currency(1L, "Dollar", "840", "USD"),
                new Currency(2L, "Yen", "392", "JPY")
        );
        when(currencyDao.findAll()).thenReturn(currencies);

        List<Currency> result = currencyService.getAllCurrencies();

        assertEquals(2, result.size());
        assertEquals("Dollar", result.get(0).getCurrencyName());
        assertEquals("Yen", result.get(1).getCurrencyName());
    }

    @Test
    void deleteCurrency_success() {
        when(currencyDao.delete(1L)).thenReturn(true);

        boolean result = currencyService.deleteCurrency(1L);

        assertTrue(result);
        verify(currencyDao, times(1)).delete(1L);
    }

    @Test
    void getCurrencyByName_found() {
        Currency currency = new Currency(1L, "Pound", "826", "GBP");
        when(currencyDao.findByValue("Pound")).thenReturn(Optional.of(currency));

        Optional<Currency> result = currencyService.getCurrencyByName("Pound");

        assertTrue(result.isPresent());
        assertEquals("Pound", result.get().getCurrencyName());
    }

    @Test
    void getCurrencyByName_notFound() {
        when(currencyDao.findByValue("Franc")).thenReturn(Optional.empty());

        Optional<Currency> result = currencyService.getCurrencyByName("Franc");

        assertFalse(result.isPresent());
    }

    @Test
    void updateCurrency_success() {
        Currency currency = new Currency(1L, "Swiss Franc", "756", "CHF");
        when(currencyDao.findById(1L)).thenReturn(Optional.of(currency));
        when(currencyDao.update(any(Currency.class))).thenReturn(currency);

        Optional<Currency> result = currencyService.updateCurrency(currency);

        assertTrue(result.isPresent());
        assertEquals("Swiss Franc", result.get().getCurrencyName());
        verify(currencyDao, times(1)).update(any(Currency.class));
    }

    @Test
    void updateCurrency_notFound() {
        Currency currency = new Currency(2L, "Peso", "484", "MXN");
        when(currencyDao.findById(2L)).thenReturn(Optional.empty());

        Optional<Currency> result = currencyService.updateCurrency(currency);

        assertTrue(result.isEmpty());
        verify(currencyDao, never()).update(any(Currency.class));
    }
}