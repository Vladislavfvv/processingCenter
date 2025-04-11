package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOInterface;
import exception.DaoException;
import model.AcquiringBank;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AcquiringBankJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, AcquiringBank> {
    private static final Logger logger = Logger.getLogger(AcquiringBankJDBCDaoImpl.class.getName());



    public AcquiringBankJDBCDaoImpl(Connection connection) {
        super(connection);
        //this.connection = connection;
        //    private static final AcquiringBankJDBCDaoImpl INSTANCE = new AcquiringBankJDBCDaoImpl();
        //
        //    private AcquiringBankJDBCDaoImpl() {
        //    }
        //
        //    public static AcquiringBankJDBCDaoImpl getInstance() {
        //        return INSTANCE;
        //    }
        //private final Connection connection;
      //  DAOAbstract daoAbstract = new DAOAbstract(connection);
    }

    private static final String CREATE_TABLE_ACQUIRING_BANK = "CREATE TABLE IF NOT EXISTS processingCenterSchema.acquiring_bank (id serial primary key, bic varchar(9) not null, abbreviated_name varchar(255) not null);";
    //private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS processingCenterSchema.acquiring_bank2 (id serial primary key, bic varchar(9) not null, abbreviated_name varchar(255) not null);";

//    String CREATE_TABLE_SQL3 = """
//             CREATE TABLE IF NOT EXISTS acquiring_bank3 (id SERIAL PRIMARY KEY,\s
//             bic VARCHAR(9) NOT NULL,\s
//             abbreviated_name VARCHAR(255) NOT NULL);\s
//            \s""";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS acquiring_bank";
    private static final String TRUNCATE_SQL = "TRUNCATE TABLE acquiring_bank";
    private static final String DELETE_ALL_SQL = "DELETE FROM acquiring_bank";
    private static final String DELETE_SQL = "DELETE FROM acquiring_bank where id = ?";
    private static final String INSERT_SQL = "INSERT INTO acquiring_bank(bic, abbreviated_name) values(?,?)";
    private static final String FIND_ALL_SQL = "SELECT id, bic, abbreviated_name FROM acquiring_bank;";
    private static final String FIND_BY_ID_SQL = "SELECT id, bic, abbreviated_name FROM acquiring_bank WHERE id = ?";
    //получение по идентификатору
//    private static final String FIND_BY_ID_SQL = "SELECT id, bic, abbreviated_name " +
//            "FROM processingCenterSchema.acquiring_bank WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE acquiring_bank SET bic = ?, abbreviated_name = ? WHERE id = ?";


    @Override
    public AcquiringBank insert(AcquiringBank acquiringBank) {
        try {
            // Проверяем наличие таблицы перед вставкой
            if (!isTableExists(connection, "acquiring_bank")) {
                logger.warning("Таблица acquiring_bank не существует. Создаю...");
                createTableQuery(CREATE_TABLE_ACQUIRING_BANK);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);//второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, acquiringBank.getBic());
            preparedStatement.setString(2, acquiringBank.getAbbreviatedName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) { //берем следующий id за последним в БД и присваиваем ему новый ID
                acquiringBank.setId(resultSet.getLong("id"));
            }
            logger.info("acquiringBank with " + acquiringBank.getId() + "  was added");
            return acquiringBank;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }


    //Если подключен ломбок - использовать аннотацию сникесроуз
    @Override
    public Optional<AcquiringBank> findById(Long id) {
        try (Connection connection = ConnectionManager.open()) {
            return findById(id, connection);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске AcquiringBank value", e);
        }
    }

    public Optional<AcquiringBank> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            AcquiringBank acquiringBank = null;
            if (resultSet.next()) {
                acquiringBank = new AcquiringBank(
                        resultSet.getLong("id"),
                        resultSet.getString("bic"),
                        resultSet.getString("abbreviated_name")
                );
                // }
                //return Optional.ofNullable(acquiringBank);//
                logger.info("acquiringBank with " + acquiringBank.getId() + "  was founded");
                return Optional.of(acquiringBank);
            } else {
                return Optional.empty(); // или пустой Optional
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске AcquiringBank value", e);
        }
    }

    @Override
    public List<AcquiringBank> findAll() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AcquiringBank> acquiringBanks = new ArrayList<>();
            while (resultSet.next()) {
                acquiringBanks.add(buildAcquiringBank(resultSet));
            }
            return acquiringBanks;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private AcquiringBank buildAcquiringBank(ResultSet resultSet) throws SQLException {
        return new AcquiringBank(
                resultSet.getLong("id"),
                resultSet.getString("bic"),
                resultSet.getString("abbreviated_name")
        );
    }


    @Override
    public boolean update(AcquiringBank value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, value.getBic());
            preparedStatement.setString(2, value.getAbbreviatedName());
            preparedStatement.setLong(3, value.getId());
            logger.info("acquiringBank with " + value.getId() + "  was updated");
            return preparedStatement.executeUpdate() > 0; //проверка обновилась ли хотя бы 1 строка

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при обновлении AcquiringBank value", e);
        }

    }

    @Override
    public boolean delete(Long key) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, key);
            logger.info("acquiringBank with id = " + key + "  was deleted");
            return preparedStatement.executeUpdate() > 0;//если получилось удалить, тогда возвращаем true, иначе - false
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

//    @Override
//    public void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_TABLE_SQL);
//            logger.info("Table created");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_ACQUIRING_BANK);
    }


    @Override
    public boolean deleteAll(String s) {
        return deleteAllService(s);
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService(s);
    }

    @Override
    public Optional<AcquiringBank> findByValue(String cardNumber) {
        return Optional.empty();
    }


//    private boolean isTableExists(Connection connection, String tableName) {
//        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'processingcenterschema' AND table_name = ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, tableName);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getBoolean(1);
//                }
//            }
//        } catch (SQLException e) {
//            logger.severe("Ошибка при проверке таблицы: " + e.getMessage());
//            throw new DaoException(e);
//        }
//        return false;
//    }

//    @Override
//    public boolean dropTable() {
//        try (Connection connection = ConnectionManager2.open()) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(DROP_TABLE_SQL);
//            logger.info("Table dropped");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean deleteAll() {
//        try (Connection connection = ConnectionManager2.open()) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(DELETE_ALL_SQL);
//            logger.info("All records were deleted from the AcquiringBank table");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//        return true;
//    }


}
