import com.java.model.AcquiringBank;
import com.java.model.Card;
import com.java.model.SalesPoint;
import com.java.service.AcquiringBankService;
import com.java.service.CardService;
import com.java.service.SalesPointService;
import com.java.util.ConnectionManager2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) throws SQLException {
        Logger logger = Logger.getLogger(Main.class.getClassLoader().getClass().getName());


//        // Инициализация пула соединений
//        // Пул соединений уже инициализируется в ConnectionManager2 статически
//
//        // Получаем DAO через фабрику
//        DAOInterface<AcquiringBank> acquiringBankDAO = DAOFactory.getAcquiringBankDAO();
//
//        // Работа с DAO
//
//        // Закрытие соединений, если нужно
//        DAOFactory.closeConnection();

        Connection connection = ConnectionManager2.open(); // Получаем соединение из пула

        try (Statement stmt = connection.createStatement()) {

            connection.setAutoCommit(false); // Начало транзакции
            System.out.println("Создание схемы и установка search_path...");
            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS processingCenterSchema");
            stmt.executeUpdate("SET search_path TO processingCenterSchema");

            System.out.println("Создание таблиц\n");

            System.out.println("Создание схемы и установка search_path...");
            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS processingCenterSchema");
            stmt.executeUpdate("SET search_path TO processingCenterSchema");

            System.out.println("Создание таблиц...");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card_status (" +
                    "id bigserial PRIMARY KEY, card_status_name varchar(255) UNIQUE NOT NULL)");
            System.out.println("Таблица card_status создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.payment_system (" +
                    "id bigserial PRIMARY KEY, payment_system_name varchar(50) UNIQUE NOT NULL)");
            System.out.println("Таблица payment_system создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.currency (" +
                    "id bigserial PRIMARY KEY, currency_digital_code varchar(3), currency_letter_code varchar(3), currency_name varchar(255))");
            System.out.println("Таблица currency создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.issuing_bank (" +
                    "id bigserial PRIMARY KEY, bic varchar(9), bin varchar(5), abbreviated_name varchar(255))");
            System.out.println("Таблица issuing_bank создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.acquiring_bank (" +
                    "id bigserial PRIMARY KEY, bic varchar(9), abbreviated_name varchar(255))");
            System.out.println("Таблица acquiring_bank создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.sales_point (" +
                    "id bigserial PRIMARY KEY, pos_name varchar(255), pos_address varchar(255), pos_inn varchar(12), " +
                    "acquiring_bank_id bigint REFERENCES processingCenterSchema.acquiring_bank(id) ON DELETE CASCADE ON UPDATE CASCADE)");
            System.out.println("Таблица sales_point создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.merchant_category_code (" +
                    "id bigserial PRIMARY KEY, mcc varchar(4), mcc_name varchar(255))");
            System.out.println("Таблица merchant_category_code создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.terminal (" +
                    "id bigserial PRIMARY KEY, terminal_id varchar(9), " +
                    "mcc_id bigint REFERENCES processingCenterSchema.merchant_category_code(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "pos_id bigint REFERENCES processingCenterSchema.sales_point(id) ON DELETE CASCADE ON UPDATE CASCADE)");
            System.out.println("Таблица terminal создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.response_code (" +
                    "id bigserial PRIMARY KEY, error_code varchar(2), error_description varchar(255), error_level varchar(255))");
            System.out.println("Таблица response_code создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction_type (" +
                    "id bigserial PRIMARY KEY, transaction_type_name varchar(255), operator varchar(1))");
            System.out.println("Таблица transaction_type создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.account (" +
                    "id bigserial PRIMARY KEY, account_number varchar(50), balance decimal, " +
                    "currency_id bigint REFERENCES processingCenterSchema.currency(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "issuing_bank_id bigint REFERENCES processingCenterSchema.issuing_bank(id) ON DELETE CASCADE ON UPDATE CASCADE)");
            System.out.println("Таблица account создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card (" +
                    "id bigserial PRIMARY KEY, card_number varchar(50), expiration_date date, holder_name varchar(50), " +
                    "card_status_id bigint REFERENCES processingCenterSchema.card_status(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "payment_system_id bigint REFERENCES processingCenterSchema.payment_system(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "account_id bigint REFERENCES processingCenterSchema.account(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "received_from_issuing_bank timestamp, sent_to_issuing_bank timestamp)");
            System.out.println("Таблица card создана.");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction (" +
                    "id bigserial PRIMARY KEY, transaction_date date, sum decimal, transaction_name varchar(255), " +
                    "account_id bigint REFERENCES processingCenterSchema.account(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "transaction_type_id bigint REFERENCES processingCenterSchema.transaction_type(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "card_id bigint REFERENCES processingCenterSchema.card(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "terminal_id bigint REFERENCES processingCenterSchema.terminal(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "response_code_id bigint REFERENCES processingCenterSchema.response_code(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "authorization_code varchar(6), received_from_issuing_bank timestamp, sent_to_issuing_bank timestamp)");
            System.out.println("Таблица transaction создана.");

            System.out.println("Все таблицы успешно созданы.");


            connection.commit(); // Фиксируем транзакцию
            System.out.println("Все таблицы успешно созданы и транзакция зафиксирована!!!!!!!!!!!!!!!!!!");

        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблиц: " + e.getMessage());
            try {
                connection.rollback(); // Откат всех изменений, если что-то пошло не так
                System.err.println("Транзакция отменена!!!!!!!!!!!!!!!!!!!!");
            } catch (SQLException rollbackEx) {
                System.err.println("Ошибка при откате транзакции: " + rollbackEx.getMessage());
            }
        }

        CardService cardService = new CardService(connection);

        cardService.create(new Card("4123450000000019", "2025-12-31", "Ivanov", 2, 1, 1, "2022-10-21 15:26:06.175", "2022-10-21 15:27:08.271"));

        cardService.create(new Card("4123450000000019", "2025-12-31", "Ivanov", 2, 1, 1, "2022-10-21 15:26:06.175", "2022-10-21 15:27:08.271"));






//        // 1. Создание таблиц
//        System.out.println("Создание таблиц...");
//        stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS processingCenterSchema");
//        stmt.executeUpdate("SET search_path TO processingCenterSchema");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card_status (" +
//                "id bigserial PRIMARY KEY, card_status_name varchar(255) UNIQUE NOT NULL)");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.payment_system (" +
//                "id bigserial PRIMARY KEY, payment_system_name varchar(50) UNIQUE NOT NULL)");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.currency (" +
//                "id bigserial PRIMARY KEY, currency_digital_code varchar(3), currency_letter_code varchar(3), currency_name varchar(255))");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.issuing_bank (" +
//                "id bigserial PRIMARY KEY, bic varchar(9), bin varchar(5), abbreviated_name varchar(255))");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.account (" +
//                "id bigserial PRIMARY KEY, account_number varchar(50), balance decimal, currency_id bigint REFERENCES processingCenterSchema.currency(id), issuing_bank_id bigint REFERENCES processingCenterSchema.issuing_bank(id))");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card (" +
//                "id bigserial PRIMARY KEY, card_number varchar(50), expiration_date date, holder_name varchar(50), " +
//                "card_status_id bigint REFERENCES processingCenterSchema.card_status(id), " +
//                "payment_system_id bigint REFERENCES processingCenterSchema.payment_system(id), " +
//                "account_id bigint REFERENCES processingCenterSchema.account(id), " +
//                "received_from_issuing_bank timestamp, sent_to_issuing_bank timestamp)");
//
//        System.out.println("Таблицы созданы.");


//        AcquiringBankService acquiringBankService = new AcquiringBankService(connection);
//        SalesPointService salesPointService = new SalesPointService(connection);
//
//
//        AcquiringBank acquiringBank = new AcquiringBank("123456789", "PriorBank");
//        acquiringBankService.create(acquiringBank);
//
//
//        SalesPoint salesPoint = new SalesPoint("First Shop", "Minsk, Nezavisimosty, 1", "123456789102", acquiringBank);
//        salesPointService.create(salesPoint);
//
//
//
//
//
//          System.out.println(salesPointService.findById(3L));
//
//        List<SalesPoint> salesPoints = salesPointService.findAll();
//        for (SalesPoint sp : salesPoints) {
//            System.out.println(sp.toString());
//        }
//
//        salesPointService.delete(5L);
//       // salesPoints.
//
//        System.out.println(" ---------------------------------------------------- ");
//
//        for (SalesPoint sp : salesPoints) {
//            System.out.println(sp.toString());
//        }
//
////типа транзакции - метод обновления SalesPoint записи
//
//        updateMethod(salesPointService, acquiringBankService, connection);


//        // Очистить все записи из таблицы sales_point
//        boolean clearResult = salesPointService.clearSalesPoints();
//        if (clearResult) {
//            System.out.println("Таблица sales_point была очищена.");
//        } else {
//            System.out.println("Не удалось очистить таблицу sales_point.");
//        }

        // Удалить таблицу sales_point
//        boolean removeResult = salesPointService.removeSalesPointsTable();
//        if (removeResult) {
//            System.out.println("Таблица sales_point была удалена.");
//        } else {
//            System.out.println("Не удалось удалить таблицу sales_point.");
//        }


// Используем CardService
        // CardService cardService = new CardService(connection); // Передаем его в CardService
        //Card card = new Card(...);
        // cardService.createCard(card);

        // Закрываем соединение (возвращаем его в пул)
        // ConnectionManager2.close(connection);













 /*       System.out.println("Hello, World!");
        Class<Driver> driverClass = Driver.class;
        String DROP_TABLE_SQL = "DROP TABLE IF EXISTS acquiring_bank3";
        String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS acquiring_bank3 (
                    id SERIAL PRIMARY KEY,
                    bic VARCHAR(9) NOT NULL,
                    abbreviated_name VARCHAR(255) NOT NULL
                );
                """;
        //String SET_SCHEMA_SQL = "SET search_path TO processingCenterSchema";
        try (Connection connection = ConnectionManager2.open()
        ) {
            Statement statement = connection.createStatement();
// Переключаемся на схему
            // statement.execute(SET_SCHEMA_SQL);
            statement.executeUpdate(CREATE_TABLE_SQL);
            logger.info("Table created");
            System.out.println("Connected to database");
            System.out.println(connection.getMetaData().getDatabaseProductName());
            System.out.println(connection.getMetaData().getDatabaseProductVersion());
            System.out.println(connection.getMetaData().getDriverName());
            System.out.println(connection.getMetaData().getDriverVersion());
            System.out.println(connection.getMetaData().getDriverMajorVersion());
            System.out.println(connection.getMetaData().getDriverMinorVersion());
            System.out.println(connection.getTransactionIsolation());
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
*/


    }

//    public static void updateMethod(SalesPointService salesPointService, AcquiringBankService acquiringBankService, Connection connection) {
//        Optional<SalesPoint> existingSalesPoint = salesPointService.findById(4L);
//        if (existingSalesPoint.isPresent()) {
//            SalesPoint salesPointNew = existingSalesPoint.get();
//            // Изменените полей salesPoint
//            salesPointNew.setPosName("Second Shop");
//            salesPointNew.setPosAddress("Moscow");
//            salesPointNew.setPosInn("111111111111");
//            salesPointNew.setAcquiringBank(acquiringBankService.findById(14L).orElse(null));
//
//            boolean result = salesPointService.update(salesPointNew);
//            if (result) {
//                System.out.println("SalesPoint обновлен");
//            } else {
//                System.out.println("Ошибка при обновлении SalesPoint");
//            }
//        } else {
//            System.out.println("Не найден SalesPoint с таким ID");
//        }
//    }


}
