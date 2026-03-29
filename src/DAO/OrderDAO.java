package Book_Store_Management_System.src.DAO;

import Book_Store_Management_System.src.util.DBConnection;
import java.sql.*;

public class OrderDAO {

    // Place Order
    public void placeOrder(int userId, int bookId, int quantity, double price) {
        String checkQuery = "SELECT price, stock FROM books WHERE id=?";
        String orderQuery = "INSERT INTO orders(user_id,total_amount) VALUES(?,?)";
        String itemQuery = "INSERT INTO order_items(order_id,book_id,quantity,price) VALUES(?,?,?,?)";
        String stockQuery = "UPDATE books SET stock = stock - ? WHERE id=?";

        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);
            // Step 0: Check stock and get price
            double pricePerUnit;
            int stock;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, bookId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        pricePerUnit = rs.getDouble("price");
                        stock = rs.getInt("stock");
                        if (stock < quantity) throw new RuntimeException("Insufficient stock!");
                    } else {
                        throw new RuntimeException("Book not found!");
                    }
                }
            }
            // Step 1: Insert order
            double totalAmount = pricePerUnit * quantity;
            int orderId;
            try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, totalAmount);
                orderStmt.executeUpdate();
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new RuntimeException("Failed to generate order ID");
                    }
                }
            }
            // Step 2: Insert order item
            try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, bookId);
                itemStmt.setInt(3, quantity);
                itemStmt.setDouble(4, pricePerUnit);
                itemStmt.executeUpdate();
            }
            // Step 3: Update stock
            try (PreparedStatement stockStmt = connection.prepareStatement(stockQuery)) {
                stockStmt.setInt(1, quantity);
                stockStmt.setInt(2, bookId);
                stockStmt.executeUpdate();
            }
            connection.commit();
            System.out.println("Order placed Successfully!");

        } catch (SQLException | RuntimeException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Error placing order", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
