package Book_Store_Management_System.util;

import java.sql.*;

public class DBConnection {
    private static final String url="jdbc:mysql://127.0.0.1:3306/bookstore_management";
    private static final String username="root";
    private static final String password="9689";
    // Main function
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
