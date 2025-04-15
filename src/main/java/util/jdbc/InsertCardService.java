package util.jdbc;

import exception.DaoException;
import model.Card;
import service.AccountService;
import service.CardService;
import service.CardStatusService;
import service.PaymentSystemService;
import model.Account;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

public class InsertCardService {

    private static final Logger logger = Logger.getLogger(InsertCardService.class.getName());
    private final CardService cardService;
    private final CardStatusService cardStatusService;
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;

    public InsertCardService(CardService cardService, CardStatusService cardStatusService,
                             PaymentSystemService paymentSystemService, AccountService accountService) {
        this.cardService = cardService;
        this.cardStatusService = cardStatusService;
        this.paymentSystemService = paymentSystemService;
        this.accountService = accountService;
    }

//    public void insertMultipleCards() {
//
//        try (Connection connection = ConnectionManager.open()) {
//            List<Card> cardList = List.of(
//                    new Card("4123450000000019",
//                            Date.valueOf("2025-12-31"),
//                            "IVAN I. IVANOV",
//                            cardStatusService.findById(2L).orElseThrow(),
//                            paymentSystemService.findById(1L).orElseThrow(),
//                            accountService.findById(1L).orElseThrow(),
//                            Timestamp.valueOf("2022-10-21 15:26:06.175"),
//                            Timestamp.valueOf("2022-10-21 15:27:08.271")),
//
//                    new Card("5123450000000024",
//                            Date.valueOf("2025-12-31"),
//                            "SEMION E. PETROV",
//                            cardStatusService.findById(3L).orElseThrow(),
//                            paymentSystemService.findById(2L).orElseThrow(),
//                            accountService.findById(2L).orElseThrow(),
//                            Timestamp.valueOf("2022-04-05 10:23:05.372"),
//                            Timestamp.valueOf("2022-04-05 10:24:02.175")),
//                    new Card("5123450000000048",
//                            Date.valueOf("2025-12-31"),
//                            "GEORGIY E. ROMANOV",
//                            cardStatusService.findById(2L).orElseThrow(),
//                            paymentSystemService.findById(1L).orElseThrow(),
//                            accountService.findById(2L).orElseThrow(),
//                            Timestamp.valueOf("2023-04-05 10:23:05.372"),
//                            Timestamp.valueOf("2023-04-05 10:24:02.175")),
//                    new Card("5123450000000050",
//                            Date.valueOf("2025-12-31"),
//                            "NIKITA E. POTAPOV",
//                            cardStatusService.findById(4L).orElseThrow(),
//                            paymentSystemService.findById(2L).orElseThrow(),
//                            accountService.findById(3L).orElseThrow(),
//                            Timestamp.valueOf("2023-05-08 10:23:05.372"),
//                            Timestamp.valueOf("2023-05-08 10:24:02.175"))
//            );
//
//            for(Card c: cardList) {
//                Card newCard = cardService.create(c);
//                if (newCard != null) {
//                    logger.info("Карта " + newCard.getCardNumber() + " успешно добавлена.");
//                } else {
//                    logger.warning("Не удалось добавить карту " + newCard.getCardNumber());
//                }
//            }
//        } catch (SQLException e) {
//            logger.severe("Ошибка при работе с соединением: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            logger.severe("Ошибка при вставке карт: " + e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println("Поздравляю!!! 4 Карты наконец-то добавлены.");
//    }


    public void insertMultipleCards() {
        List<Card> cardList = List.of(
                new Card("4123450000000019", Date.valueOf("2025-12-31"), "IVAN I. IVANOV",
                        cardStatusService.findById(2L).orElseThrow(),
                        paymentSystemService.findById(1L).orElseThrow(),
                        accountService.findById(1L).orElseThrow(),
                        Timestamp.valueOf("2022-10-21 15:26:06.175"),
                        Timestamp.valueOf("2022-10-21 15:27:08.271")),

                new Card("5123450000000024", Date.valueOf("2025-12-31"), "SEMION E. PETROV",
                        cardStatusService.findById(3L).orElseThrow(),
                        paymentSystemService.findById(2L).orElseThrow(),
                        accountService.findById(2L).orElseThrow(),
                        Timestamp.valueOf("2022-04-05 10:23:05.372"),
                        Timestamp.valueOf("2022-04-05 10:24:02.175")),

                new Card("5123450000000048", Date.valueOf("2025-12-31"), "GEORGIY E. ROMANOV",
                        cardStatusService.findById(2L).orElseThrow(),
                        paymentSystemService.findById(1L).orElseThrow(),
                        accountService.findById(2L).orElseThrow(),
                        Timestamp.valueOf("2023-04-05 10:23:05.372"),
                        Timestamp.valueOf("2023-04-05 10:24:02.175")),

                new Card("5123450000000050", Date.valueOf("2025-12-31"), "NIKITA E. POTAPOV",
                        cardStatusService.findById(3L).orElseThrow(),
                        paymentSystemService.findById(2L).orElseThrow(),
                        accountService.findById(1L).orElseThrow(),
                        Timestamp.valueOf("2023-05-08 10:23:05.372"),
                        Timestamp.valueOf("2023-05-08 10:24:02.175"))
        );

        try {
            for (Card card : cardList) {
                Card newCard = cardService.create(card);
                if (newCard != null) {
                    logger.info("Карта " + newCard.getCardNumber() + " успешно добавлена.");
                } else {
                    logger.warning("Не удалось добавить карту " + card.getCardNumber());
                }
            }
            System.out.println("Поздравляю!!! 4 Карты наконец-то добавлены.");
        } catch (DaoException e) {
            logger.severe("Ошибка при работе с соединением: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.severe("Ошибка при вставке карт: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
