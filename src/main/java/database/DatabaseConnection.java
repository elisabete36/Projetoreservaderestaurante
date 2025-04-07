package database;

// database/DatabaseConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:5204/seu_banco_de_dados"; // Substitua pelo seu banco de dados
    private static final String USER = "postgres"; // Substitua pelo seu usuário
    private static final String PASSWORD = "260105"; // Substitua pela sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); // Retorna a conexão
    }
}
