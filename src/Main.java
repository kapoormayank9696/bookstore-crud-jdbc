package Book_Store_Management_System;

import Book_Store_Management_System.DAO.BookDAO;
import Book_Store_Management_System.DAO.OrderDAO;
import Book_Store_Management_System.DAO.UserDAO;
import Book_Store_Management_System.model.Book;
import Book_Store_Management_System.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    // Main function
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        UserDAO userDAO=new UserDAO();
        BookDAO bookDAO=new BookDAO();
        OrderDAO orderDAO=new OrderDAO();

        User loggedInUser=null;
        while (true) {
            System.out.println("\n====== BOOK STORE MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Books");
            System.out.println("4. Place Order");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice=sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    // Register
                    System.out.print("Enter Name: ");
                    String name=sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email=sc.nextLine();

                    System.out.print("Enter Password: ");
                    String password=sc.nextLine();

                    User newUser=new User(0,name,email,password);
                    userDAO.registerUser(newUser);

                    System.out.println("User registered successfully!");
                    break;
                case 2:
                    // Login
                    System.out.print("Enter Email: ");
                    String loginEmail=sc.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword=sc.nextLine();

                    loggedInUser=userDAO.loginUser(loginEmail,loginPassword);
                    if(loggedInUser != null) {
                        System.out.println("Login successfully! Welcome "+loggedInUser.getName());
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;
                case 3:
                    // View Books
                    List<Book> books=bookDAO.getAllBooks();
                    System.out.println("\n--- Available Books ---");
                    for (Book b : books) {
                        System.out.println("ID: " + b.getId() +
                                " | Title: " + b.getTitle() +
                                " | Author: " + b.getAuthor() +
                                " | Price: " + b.getPrice() +
                                " | Stock: " + b.getStock());
                    }
                    break;
                case 4:
                    // Place Order
                    if(loggedInUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.print("Enter Book ID: ");
                    int bookId=sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity=sc.nextInt();

                    Book book=bookDAO.getBookById(bookId);
                    if(book == null) {
                        System.out.println("Book not found!");
                        break;
                    }
                    if(book.getStock() < quantity) {
                        System.out.println("Insufficient stock!");
                        break;
                    }
                    orderDAO.placeOrder(
                            loggedInUser.getId(),
                            bookId,
                            quantity,
                            book.getPrice()
                    );
                    break;
                case 5:
                    System.out.println("Thank you for using Book Store!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
