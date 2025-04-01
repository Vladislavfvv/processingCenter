package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.AcquiringBank;
import com.java.model.SalesPoint;
import com.java.util.ConnectionManager2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
//import java.util.logging.Logger;

public class SalesPointJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, SalesPoint> {
    private static final Logger logger = Logger.getLogger(SalesPointJDBCDaoImpl.class.getName());
//    private final Connection connection;
    private final AcquiringBankJDBCDaoImpl acquiringBankJDBCDao;
//    public SalesPointJDBCImpl(Connection connection) {
//        this.connection = connection;
//        this.acquiringBankJDBCDao = new AcquiringBankJDBCDaoImpl(connection); // Инициализируем DAO после получения connection
//
//    }

    public SalesPointJDBCDaoImpl(Connection connection) {
        super(connection);
        this.acquiringBankJDBCDao = new AcquiringBankJDBCDaoImpl(connection); // Инициализируем DAO после получения connection
    }

    public boolean clearSalesPoints() {
        return deleteAll("processingcenterschema.sales_point");
    }

    public boolean removeSalesPointsTable() {
        return dropTable("processingcenterschema.sales_point");
    }


    // private static final String FIND_BY_ID = "SELECT id, pos_name, pos_address, pos_inn, acquiring_bank_id, bic, abbreviatedName FROM salespoint";

    private static final String JoinTables = "SELECT sp.id, sp.pos_name, sp.pos_address, sp.pos_inn, ab.id AS acquiring_bank_id, ab.bic, ab.abbreviatedName FROM SalesPoint sp JOIN AcquiringBank ab ON sp.acquiring_bank_id = ab.id;";

    private static final String FIND_BY_ID = "SELECT sp.id, sp.pos_name, sp.pos_address, sp.pos_inn, ab.id AS acquiring_bank_id, ab.bic, ab.abbreviated_name " +
            "FROM processingCenterSchema.sales_point sp " +
            "JOIN processingCenterSchema.acquiring_bank ab ON sp.acquiring_bank_id = ab.id " +
            "WHERE sp.id = ?";

    private static final String FIND_ALL_SQL = "SELECT sp.id, sp.pos_name, sp.pos_address, sp.pos_inn, " +
            "ab.id AS bank_id, ab.bic, ab.abbreviated_name " +
            "FROM processingCenterSchema.sales_point sp " +
            "JOIN processingCenterSchema.acquiring_bank ab ON sp.acquiring_bank_id = ab.id";


    //   private final AcquiringBankJDBCDaoImpl acquiringBankJDBCDao = DAOFactory.getAcquiringBankDAO(connection);

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS sales_point";
    private static final String DELETE_ALL_SQL = "DELETE FROM processingCenterSchema.sales_point";
    private static final String DELETE_SQL = "DELETE FROM processingCenterSchema.sales_point where id = ?";
    private static final String UPDATE_SQL = "UPDATE processingCenterSchema.sales_point SET pos_name = ?, pos_address = ?, pos_inn = ?, acquiring_bank_id = ?  WHERE id = ?";
    //AcquiringBank
    private SalesPoint buildSalesPoint(ResultSet resultSet) throws SQLException {
        // Получаем банк из DAO
        Optional<AcquiringBank> optionalBank = acquiringBankJDBCDao.findById(resultSet.getLong("acquiring_bank_id"));
        AcquiringBank acquiringBank = optionalBank.orElse(null);

//        AcquiringBank acquiringBank = new AcquiringBank(
//                resultSet.getLong("acquiring_bank_id"),
//                resultSet.getString("bic"),
//                resultSet.getString("abbreviatedName"));

        SalesPoint salesPoint = new SalesPoint(
                resultSet.getLong("id"),
                resultSet.getString("pos_name"),
                resultSet.getString("pos_address"),
                resultSet.getString("pos_inn"),
                acquiringBank
        );
        return salesPoint;
    }


    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager2.open()) {
            Statement statement = connection.createStatement();
            String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS processingCenterSchema.sales_point ("
                    + "id SERIAL PRIMARY KEY, "
                    + "pos_name VARCHAR(255) NOT NULL, "
                    + "pos_address VARCHAR(255) NOT NULL, "
                    + "pos_inn VARCHAR(12) NOT NULL, "
                    + "acquiring_bank_id BIGINT NOT NULL, "
                    + "CONSTRAINT fk_acquiring_bank FOREIGN KEY (acquiring_bank_id) REFERENCES processingCenterSchema.acquiring_bank(id) ON DELETE CASCADE);";
            statement.executeUpdate(CREATE_TABLE_SQL);
            logger.info("Table created");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public SalesPoint insert(SalesPoint value) {
        try (Connection connection = ConnectionManager2.open()) {
            // Проверяем наличие таблицы перед вставкой
            if (!DAOAbstract.isTableExists(connection, "acquiring_bank")) {
                logger.warning("Таблица acquiring_bank не существует. Создаю...");
                createTable();
            }
            if (!DAOAbstract.isTableExists(connection, "sales_point")) {
                logger.warning("Таблица sales_point не существует. Создаю...");
                createTable();
            }
            String INSERT_SQL = "INSERT INTO processingCenterSchema.sales_point (pos_name, pos_address, pos_inn, acquiring_bank_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);//второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, value.getPosName());
            preparedStatement.setString(2, value.getPosAddress());
            preparedStatement.setString(3, value.getPosInn());
            preparedStatement.setLong(4, value.getAcquiringBank().getId());  // ID банка

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Ошибка: не удалось создать точку продаж.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    value.setId(generatedKeys.getLong(1));
                }
            }
            logger.info("SalesPoint c  id = " + value.getId() + " был добавлен");
            return value;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(SalesPoint value) {
        if (value == null || value.getId() == null) {
            return false; // Нельзя обновить объект без ID
        }

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, value.getPosName());
            statement.setString(2, value.getPosAddress());
            statement.setString(3, value.getPosInn());
            statement.setLong(4, value.getAcquiringBank().getId()); // Устанавливаем ID банка
            statement.setLong(5, value.getId()); // WHERE id = ?

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Возвращаем true, если обновилась хотя бы 1 строка
        } catch (SQLException e) {
            logger.severe("Ошибка при обновлении SalesPoint: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long key) {
        try (Connection connection = ConnectionManager2.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, key);
            logger.info("SalesPoint c  id = " + key + " удалено");
            return preparedStatement.executeUpdate() > 0;//если получилось удалить, тогда возвращаем true, иначе - false
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }

    }

//    @Override
//    public Optional<SalesPoint> findById(Long key) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
//            preparedStatement.setLong(1, key);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            SalesPoint salesPoint = null;
//            if (resultSet.next()) {
//                salesPoint = buildSalesPoint(resultSet);
//            }
//            logger.info("Найдено c  id = " + key);
//            return Optional.ofNullable(salesPoint);
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public Optional<SalesPoint> findById(Long key) {
        try (Connection connection = ConnectionManager2.open()) {
           return findById(key, connection);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }


    public Optional<SalesPoint> findById(Long key, Connection connection) {
        try ( PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            SalesPoint salesPoint = null;
            if (resultSet.next()) {
                salesPoint = buildSalesPoint(resultSet);
            }
            logger.info("Найдено c  id = " + key);
            return Optional.ofNullable(salesPoint);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
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


//    public Optional<SalesPoint> findById(Long key, Connection connection) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
//            preparedStatement.setLong(1, key);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(buildSalesPoint(resultSet));
//            }
//
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//        return Optional.empty();
//    }

    @Override
    public List<SalesPoint> findAll() {
        List<SalesPoint> salesPoints = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                AcquiringBank acquiringBank = new AcquiringBank(
                        resultSet.getLong("bank_id"),
                        resultSet.getString("bic"),
                        resultSet.getString("abbreviated_name")
                );

                SalesPoint salesPoint = new SalesPoint(
                        resultSet.getLong("id"),
                        resultSet.getString("pos_name"),
                        resultSet.getString("pos_address"),
                        resultSet.getString("pos_inn"),
                        acquiringBank
                );
                salesPoints.add(salesPoint);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении списка точек продаж", e);
        }
        return salesPoints;
    }


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
//        return false;
//    }
}
