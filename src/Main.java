import com.java.util.ConnectionManager2;
import org.postgresql.Driver;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Class<Driver> driverClass = Driver.class;



        try(Connection connection = ConnectionManager2.open()) {
            ;
            System.out.println("Connected to database");
            System.out.println(connection.getMetaData().getDatabaseProductName());
            System.out.println(connection.getMetaData().getDatabaseProductVersion());
            System.out.println(connection.getMetaData().getDriverName());
            System.out.println(connection.getMetaData().getDriverVersion());
            System.out.println(connection.getMetaData().getDriverMajorVersion());
            System.out.println(connection.getMetaData().getDriverMinorVersion());
            System.out.println(connection.getTransactionIsolation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
