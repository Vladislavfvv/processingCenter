package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.Card;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardService implements ServiceInterface<Card, Long> {
    private final DAOInterface<Long, Card> cardDAO;
    private final Connection connection;

    public CardService(Connection connection) {
        // Получаем DAO через фабрику
        this.cardDAO = DAOFactory.getCardDAO(connection);
        this.connection = connection;
    }

    public Card create(Card card) {
        return cardDAO.insert(card);
    }

    public Optional<Card> findByCardNumber(String cardNumber) {
        return cardDAO.findByValue(cardNumber); // Предположим, что у вас есть метод в DAO для поиска по номеру карты
    }

//    public boolean update(Card card) {
//        if (cardExists(card)) {
//            System.out.println("Карта уже есть в БД.");
//            return false;  // Карточка уже существует, не добавляем
//        } else {
//            return cardDAO.update(card);  // Если нет, добавляем карточку в базу
//        }
//    }


    public boolean update(Card card) {
        // Проверяем, существует ли карта с таким номером
        Optional<Card> existingCardOpt = findByCardNumber(card.getCardNumber());
        if (existingCardOpt.isPresent()) {
            Card existingCard = existingCardOpt.get();
            // Теперь мы можем обновить найденную карту
            existingCard.setExpirationDate(card.getExpirationDate());
            existingCard.setReceivedFromIssuingBank(card.getReceivedFromIssuingBank());
            existingCard.setSentToIssuingBank(card.getSentToIssuingBank());
            existingCard.setCardStatusId(card.getCardStatusId());
            existingCard.setPaymentSystemId(card.getPaymentSystemId());
            existingCard.setAccountId(card.getAccountId());

            // Теперь вызываем метод обновления с измененными полями
            return cardDAO.update(existingCard);
        } else {
            System.out.println("Карточка с таким номером не найдена.");
            return false;  // Карта с таким номером не найдена, обновление не будет выполнено
        }
    }

    public boolean delete(Long cardId) {
        return cardDAO.delete(cardId);
    }

    public Optional<Card> findById(Long cardId) {
        return cardDAO.findById(cardId);
    }

    public List<Card> findAll() {
        return cardDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        cardDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return cardDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return cardDAO.dropTable(s);
    }

//    // Метод для обновления номера карты и даты выдачи
//    public boolean updateCardNumberAndExpirationDate(Long cardId, String newCardNumber, Date newExpirationDate) {
//        // Ищем карту по ID
//        Optional<Card> cardOptional = cardDAO.findById(cardId);
//        if (cardOptional.isPresent()) {
//            Card card = cardOptional.get();
//
//            // Обновляем только номер карты и дату
//            card.setCardNumber(newCardNumber);
//            card.setExpirationDate(newExpirationDate);
//
//            // Вызываем метод обновления с измененными полями
//            return cardDAO.update(card);
//        } else {
//            //System.out.println("Карточка с таким ID не найдена.");
//            return false;
//        }
//    }


    public boolean cardExists(Card card) {
        List<Card> allCards = cardDAO.findAll();
        for (Card existingCard : allCards) {
            if (existingCard.equals(card)) {
                return true;  // Карточка уже существует
            }
        }
        return false;  // Карточка не найдена
    }
}
