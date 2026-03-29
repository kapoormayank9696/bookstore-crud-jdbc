package Book_Store_Management_System.src.DAO;

import Book_Store_Management_System.src.util.DBConnection;
import Book_Store_Management_System.src.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Register User (with hashed password)
    public void registerUser(User user) {
        String query = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    // Login User (check hashed password)
    public User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(storedHashedPassword);
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error logging in user", e);
        }
        return null; // login failed
    }

    // Get User by Email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}