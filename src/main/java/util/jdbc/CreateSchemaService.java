package util.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSchemaService {
    private final Connection connection;

    public CreateSchemaService(Connection connection) {
        this.connection = connection;
    }

    public void createJDBCTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);

            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS processingCenterSchema");
            stmt.executeUpdate("SET search_path TO processingCenterSchema");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card_status (" +
                    "id bigserial PRIMARY KEY, card_status_name varchar(255) UNIQUE NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.payment_system (" +
                    "id bigserial PRIMARY KEY, payment_system_name varchar(50) UNIQUE NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.currency (" +
                    "id bigserial PRIMARY KEY, currency_digital_code varchar(3), currency_letter_code varchar(3), currency_name varchar(255))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.issuing_bank (" +
                    "id bigserial PRIMARY KEY, bic varchar(9), bin varchar(5), abbreviated_name varchar(255))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.acquiring_bank (" +
                    "id bigserial PRIMARY KEY, bic varchar(9), abbreviated_name varchar(255))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.sales_point (" +
                    "id bigserial PRIMARY KEY, pos_name varchar(255), pos_address varchar(255), pos_inn varchar(12), " +
                    "acquiring_bank_id bigint REFERENCES processingCenterSchema.acquiring_bank(id) ON DELETE CASCADE ON UPDATE CASCADE)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.merchant_category_code (" +
                    "id bigserial PRIMARY KEY, mcc varchar(4), mcc_name varchar(255))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.terminal (" +
                    "id bigserial PRIMARY KEY, terminal_id varchar(9), " +
                    "mcc_id bigint REFERENCES processingCenterSchema.merchant_category_code(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "pos_id bigint REFERENCES processingCenterSchema.sales_point(id) ON DELETE CASCADE ON UPDATE CASCADE)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.response_code (" +
                    "id bigserial PRIMARY KEY, error_code varchar(2), error_description varchar(255), error_level varchar(255))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction_type (" +
                    "id bigserial PRIMARY KEY, transaction_type_name varchar(255), operator varchar(1))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.account (" +
                    "id bigserial PRIMARY KEY, account_number varchar(50), balance decimal, " +
                    "currency_id bigint REFERENCES processingCenterSchema.currency(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "issuing_bank_id bigint REFERENCES processingCenterSchema.issuing_bank(id) ON DELETE CASCADE ON UPDATE CASCADE)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.card (" +
                    "id bigserial PRIMARY KEY, card_number varchar(50), expiration_date date, holder_name varchar(50), " +
                    "card_status_id bigint REFERENCES processingCenterSchema.card_status(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "payment_system_id bigint REFERENCES processingCenterSchema.payment_system(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "account_id bigint REFERENCES processingCenterSchema.account(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "received_from_issuing_bank timestamp, sent_to_issuing_bank timestamp)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction (" +
                    "id bigserial PRIMARY KEY, transaction_date date, sum decimal, transaction_name varchar(255), " +
                    "account_id bigint REFERENCES processingCenterSchema.account(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "transaction_type_id bigint REFERENCES processingCenterSchema.transaction_type(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "card_id bigint REFERENCES processingCenterSchema.card(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "terminal_id bigint REFERENCES processingCenterSchema.terminal(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "response_code_id bigint REFERENCES processingCenterSchema.response_code(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "authorization_code varchar(6), received_from_issuing_bank timestamp, sent_to_issuing_bank timestamp)");

            connection.commit();
            System.out.println("Все таблицы успешно созданы и транзакция зафиксирована.");
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Ошибка при создании таблиц: " + e.getMessage());
            throw e;
        }
    }
}
