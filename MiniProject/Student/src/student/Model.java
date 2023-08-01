package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

interface DatabaseConnector {
    void establishConnection();
    void closeConnection();
}

public class Model implements DatabaseConnector {
    protected Connection connection;
    private String url;
    private String username;
    private String password;

    public Model(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        establishConnection();
    }
    @Override
    public void establishConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }
    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }
}

