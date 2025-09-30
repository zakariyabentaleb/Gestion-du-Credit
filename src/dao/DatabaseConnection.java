package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    String url = "jdbc:postgresql://localhost:5432/Credit-prjt";
    String username = "postgres";
    String password = "1234";

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion etablie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
