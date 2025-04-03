import com.java.model.AcquiringBank;
import com.java.model.SalesPoint;
import com.java.service.AcquiringBankService;
import com.java.service.SalesPointService;
import com.java.util.ConnectionManager2;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
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


        AcquiringBankService acquiringBankService = new AcquiringBankService(connection);
        SalesPointService salesPointService = new SalesPointService(connection);


        AcquiringBank acquiringBank = new AcquiringBank("123456789", "PriorBank");
        acquiringBankService.create(acquiringBank);


        SalesPoint salesPoint = new SalesPoint("First Shop", "Minsk, Nezavisimosty, 1", "123456789102", acquiringBank);
        salesPointService.create(salesPoint);





          System.out.println(salesPointService.findById(3L));

        List<SalesPoint> salesPoints = salesPointService.findAll();
        for (SalesPoint sp : salesPoints) {
            System.out.println(sp.toString());
        }

        salesPointService.delete(5L);
       // salesPoints.

        System.out.println(" ---------------------------------------------------- ");

        for (SalesPoint sp : salesPoints) {
            System.out.println(sp.toString());
        }

//типа транзакции - метод обновления SalesPoint записи

        updateMethod(salesPointService, acquiringBankService, connection);


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

    public static void updateMethod(SalesPointService salesPointService, AcquiringBankService acquiringBankService, Connection connection) {
        Optional<SalesPoint> existingSalesPoint = salesPointService.findById(4L);
        if (existingSalesPoint.isPresent()) {
            SalesPoint salesPointNew = existingSalesPoint.get();
            // Изменените полей salesPoint
            salesPointNew.setPosName("Second Shop");
            salesPointNew.setPosAddress("Moscow");
            salesPointNew.setPosInn("111111111111");
            salesPointNew.setAcquiringBank(acquiringBankService.findById(14L).orElse(null));

            boolean result = salesPointService.update(salesPointNew);
            if (result) {
                System.out.println("SalesPoint обновлен");
            } else {
                System.out.println("Ошибка при обновлении SalesPoint");
            }
        } else {
            System.out.println("Не найден SalesPoint с таким ID");
        }
    }


}
