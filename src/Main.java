import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.AcquiringBank;
import com.java.model.Card;
import com.java.service.AcquiringBankService;
import com.java.service.CardService;
import com.java.util.ConnectionManager2;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
       // CardService cardService = new CardService(connection); // Передаем его в CardService

        AcquiringBankService acquiringBankService = new AcquiringBankService(connection);
        // Используем CardService

        AcquiringBank acquiringBank = new AcquiringBank("123456789", "PriorBank");

        acquiringBankService.createAcquiringBank(acquiringBank);
        //Card card = new Card(...);
       // cardService.createCard(card);

        // Закрываем соединение (возвращаем его в пул)
        // ConnectionManager2.close(connection);




        //----------------------------------------------------
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


}
