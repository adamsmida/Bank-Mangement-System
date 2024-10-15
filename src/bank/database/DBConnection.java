package bank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL="jdbc:postgresql://localhost:5432/bank_management";
    private static final String USERNAME="postgres";
    private static final String PASSWORD="120852";
    private static Connection connection=null;

    public static Connection getConnection() {
        try {
        	DriverManager.registerDriver(new org.postgresql.Driver());
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
