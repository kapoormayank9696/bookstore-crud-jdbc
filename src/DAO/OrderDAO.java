package Book_Store_Management_System.DAO;

import Book_Store_Management_System.util.DBConnection;

import java.sql.*;

public class OrderDAO {

    // Place Order
    public void placeOrder(int userId, int bookId, int quantity, double pricePerUnit) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // Step 0: Check stock
            String checkQuery = "SELECT stock FROM books WHERE id=?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentStock = rs.getInt("stock");
                if (currentStock < quantity) {
                    throw new RuntimeException("Insufficient stock!");
                }
            } else {
                throw new RuntimeException("Book not found!");
            }

            // Step 1: Insert order
            String orderQuery = "INSERT INTO orders(user_id,total_amount) VALUES(?,?)";
            PreparedStatement orderStmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);

            double totalAmount = quantity * pricePerUnit;
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, totalAmount);
            orderStmt.executeUpdate();

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            if (orderId == 0) {
                throw new RuntimeException("Failed to generate order ID");
            }

            // Step 2: Insert order item
            String itemQuery = "INSERT INTO order_items(order_id,book_id,quantity,price) VALUES(?,?,?,?)";
            PreparedStatement itemStmt = connection.prepareStatement(itemQuery);
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, bookId);
            itemStmt.setInt(3, quantity);
            itemStmt.setDouble(4, pricePerUnit);
            itemStmt.executeUpdate();

            // Step 3: Update stock
            String stockQuery = "UPDATE books SET stock = stock - ? WHERE id=?";
            PreparedStatement stockStmt = connection.prepareStatement(stockQuery);
            stockStmt.setInt(1, quantity);
            stockStmt.setInt(2, bookId);
            stockStmt.executeUpdate();

            connection.commit();
            System.out.println("Order placed Successfully!");

        } catch (SQLException e) {
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
