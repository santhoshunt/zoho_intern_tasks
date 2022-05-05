package servletclass;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private final String URL = "jdbc:postgresql://localhost:5432/sandy";
    private final String USER = "sandy";
    private final String PASSWORD = "santhosh";
    private Connection connection = null;

    private void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successfull!");
        } catch (Exception e) {
            System.out.println("Connection to the database FAILED!");
            System.err.println(e);
        }
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            createConnection();
            if (connection != null) {
                return connection;
            } else {
                System.err.println("Could establish connection!");
            }
        }
        return null;
    }
}
