package Book_Store_Management_System.DAO;

import Book_Store_Management_System.model.Book;
import Book_Store_Management_System.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Add Book
    public void addBook(Book book) {
        String query="INSERT INTO books(title, author, price, stock) VALUES(?,?,?,?)";
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setDouble(3,book.getPrice());
            preparedStatement.setInt(4,book.getStock());
            int rows=preparedStatement.executeUpdate();
            if(rows > 0) {
                System.out.println("Book added Successfully..");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get All Books
    public List<Book> getAllBooks() {
        List<Book> list=new ArrayList<>();
        String query="SELECT * FROM books";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                // Create book class object
                Book book=new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getDouble("price"));
                book.setStock(resultSet.getInt("stock"));
                list.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Update Book
    public void updateBook(Book book) {
        String query="UPDATE books SET title=?, author=?, price=?, stock=? WHERE id=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3,book.getPrice());
            preparedStatement.setInt(4,book.getStock());
            preparedStatement.setInt(5,book.getId());
            int rows=preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("Book update Successfully..");
            }else {
                System.out.println("Book not found!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete Book
    public void deleteBook(int id) {
        String query="DELETE FROM books WHERE id=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            int rows=preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("Book delete Successfully..");
            }else {
                System.out.println("Book not found!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Select a single book
    public Book getBookById(int id) {
        String query="SELECT * FROM books WHERE id=?";
        Book book=null;
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                // Book class object creates
                book=new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getDouble("price"));
                book.setStock(resultSet.getInt("stock"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }
}
