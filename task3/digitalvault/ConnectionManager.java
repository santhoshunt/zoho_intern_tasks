package digitalvault;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private final String URL = "jdbc:postgresql://localhost:5432/sandy";
    private final String USER = "sandy";
    private final String PASSWORD = "santhosh";
    private Connection connection = null;

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successfull!");
        } catch (Exception e) {
            System.out.println("Connection to the database FAILED!");
            System.err.println(e);
        }
        return connection;
    }
}
