package dao.spring;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencySpringDaoImplTest {
    private CurrencySpringDaoImpl currencyDao;
    private EntityManager em;
    private TypedQuery<Currency> query;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class); // мок EntityManager
        query = mock(TypedQuery.class); // мок Query

        currencyDao = new CurrencySpringDaoImpl();
        // сетим мок в приватное поле
        currencyDao.setEntityManager(em);
    }

    @Test
    void testInsert() {
        Currency currency = new Currency();
        // Вызов метода DAO
        Currency result = currencyDao.insert(currency);
        // Проверяем, что метод persist был вызван на EntityManager
        assertEquals(currency, result);
        verify(em).persist(currency); // проверяем что вызвался метод persist
    }

    @Test
    void testUpdate() {
        Currency currency = new Currency();
        // Настройка мокa для метода merge
        when(em.merge(currency)).thenReturn(currency);
        // Вызов метода DAO
        Currency result = currencyDao.update(currency);
        // Проверяем, что метод merge был вызван на EntityManager
        assertEquals(currency, result);
        verify(em).merge(currency);
    }

    @Test
    void testDeleteFound() {
        Currency currency = new Currency();
        when(em.find(Currency.class, 1L)).thenReturn(currency);
        // Настройка мока для метода find
        boolean result = currencyDao.delete(1L);
        // Вызов метода DAO
        assertTrue(result);
        verify(em).remove(currency);
    }

    @Test
    void testDeleteNotFound() {
        // Настройка мока для метода find, чтобы вернуть null (валюта не найдена)
        when(em.find(Currency.class, 1L)).thenReturn(null);
        // Вызов метода DAO
        boolean result = currencyDao.delete(1L);
        // Проверка, что метод remove не был вызван
        assertFalse(result);//проверяет, что метод delete вернул false, что подтверждает, что объект не был найден и не был удален.
        verify(em, never()).remove(any()); //проверяет, что метод remove не был вызван ни разу. В данном контексте это означает, что если объект не найден (вернулся null), удаление не происходит, и метод remove не должен вызываться.
    }

    @Test
    void testFindAll() {
        List<Currency> expected = Collections.singletonList(new Currency());
        // Настроим мок для createQuery и getResultList
        // Говорим mock EntityManager, что при вызове createQuery(...) он вернёт mock Query
        when(em.createQuery(anyString(), eq(Currency.class))).thenReturn(query);
        // А у mock Query на getResultList() вернуть заранее заданный список
        when(query.getResultList()).thenReturn(expected);
        // Вызов метода DAO
        List<Currency> result = currencyDao.findAll();
        // Проверяем, что результат совпадает с ожидаемым
        assertEquals(expected, result);
    }

    //Тест проверяет метод findByValue вашего DAO-слоя, который ищет валюту в базе данных по определенному значению (например, по имени или коду валюты). Для этого мы мокируем работу с базой данных, чтобы не взаимодействовать с настоящей БД, а просто убедиться, что код правильно взаимодействует с объектами и выполняет правильные операции
    @Test
    void testFindByValue() {
        //Здесь создается экземпляр класса Currency, который будет использоваться в качестве возвращаемого объекта из мока. Мы также создаем список resultList, который содержит только один элемент — нашу валюту.
        Currency currency = new Currency();
        List<Currency> resultList = Collections.singletonList(currency);
        //Здесь происходит настройка поведения моков для объектов, которые имитируют работу с базой данных:
        //Этот вызов имитирует создание SQL-запроса в EntityManager. Мы говорим, что когда вызывается метод createQuery с любым строковым параметром (anyString()) и типом результата Currency.class, он должен возвращать заранее настроенный мок query. Мок query представляет собой объект, который будет отвечать за дальнейшую работу с запросом
        when(em.createQuery(anyString(), eq(Currency.class))).thenReturn(query);
        //Этот вызов имитирует установку параметра в запросе. Мы говорим, что когда вызывается метод setParameter (для установки параметра запроса), возвращается сам объект query (это нужно для того, чтобы методы setParameter могли вызываться цепочечно). Таким образом, мок имитирует корректную работу с параметрами запроса, например, для передачи имени валюты.
        when(query.setParameter(anyString(), any())).thenReturn(query);
        //Этот вызов говорит, что когда мы вызываем метод getResultList на объекте query, он должен вернуть список resultList, который в данном случае содержит одну валюту.
        when(query.getResultList()).thenReturn(resultList);
        //Здесь мы вызываем тестируемый метод findByValue, передавая ему строку "Test" в качестве значения для поиска. Этот метод будет использовать уже настроенный мок для выполнения запроса.
        Optional<Currency> result = currencyDao.findByValue("Test");
        //В тесте findByValue метод DAO будет вызывать:
        //createQuery с SQL-запросом,
        //затем установит параметр поиска с помощью setParameter,
        //и, наконец, вернет результат через getResultList.
        //Мы ожидаем, что этот метод вернет результат — в данном случае объект Currency.
        assertTrue(result.isPresent()); //проверяем, что метод findByValue возвращает не пустое значение. В нашем тесте это означает, что найден хотя бы один объект типа Currency (в данном случае, это объект currency, который предварительно настроен).
        assertEquals(currency, result.get());//проверяем, что возвращаемый объект соответствует ожидаемому объекту currency. Это гарантирует, что метод корректно нашел валюту и вернул правильный результат.
    }
}