package Book_Store_Management_System.DAO;

import Book_Store_Management_System.util.DBConnection;
import Book_Store_Management_System.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Register User
    public void registerUser(User user) {
        String query="INSERT INTO users(name,email,password) VALUES(?,?,?)";
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Login User
    public User loginUser(String email,String password) {
        String query="SELECT * FROM users WHERE email=? AND password=?";
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                // Create Object of class
                User user=new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // login failed
    }

    // Get User by Email
    public User getUserByEmail(String email) {
        String query="SELECT * FROM users WHERE email=?";
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                // Create an object of class
                User user=new User();
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
