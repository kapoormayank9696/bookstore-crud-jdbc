package Book_Store_Management_System.src;

import Book_Store_Management_System.src.DAO.BookDAO;
import Book_Store_Management_System.src.DAO.OrderDAO;
import Book_Store_Management_System.src.DAO.UserDAO;
import Book_Store_Management_System.src.model.Book;
import Book_Store_Management_System.src.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        OrderDAO orderDAO = new OrderDAO();
        User loggedInUser = null;
        while (true) {
            System.out.println("\n====== BOOK STORE MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Books");
            System.out.println("4. Place Order");
            System.out.println("5. Admin Panel");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1: // Register
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();
                    User newUser = new User(0, name, email, password);
                    userDAO.registerUser(newUser);
                    System.out.println("User registered successfully!");
                    break;
                case 2: // Login
                    System.out.print("Enter Email: ");
                    String loginEmail = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = sc.nextLine();

                    loggedInUser = userDAO.loginUser(loginEmail, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Login successful! Welcome " + loggedInUser.getName());
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;
                case 3: // View Books
                    List<Book> books = bookDAO.getAllBooks();
                    System.out.println("\n--- Available Books ---");
                    for (Book b : books) {
                        System.out.println("ID: " + b.getId() +
                                " | Title: " + b.getTitle() +
                                " | Author: " + b.getAuthor() +
                                " | Price: " + b.getPrice() +
                                " | Stock: " + b.getStock());
                    }
                    break;
                case 4: // Place Order
                    if (loggedInUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine(); // consume newline
                    Book book = bookDAO.getBookById(bookId);
                    if (book == null) {
                        System.out.println("Book not found!");
                        break;
                    }
                    if (book.getStock() < quantity) {
                        System.out.println("Insufficient stock!");
                        break;
                    }
                    orderDAO.placeOrder(loggedInUser.getId(), bookId, quantity, book.getPrice());
                    break;
                case 5: // Admin Panel
                    System.out.print("Enter Admin Username: ");
                    String adminUser = sc.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String adminPass = sc.nextLine();

                    if (adminUser.equals("admin") && adminPass.equals("1234")) {
                        adminMenu(sc, bookDAO);
                    } else {
                        System.out.println("Invalid Admin Credentials!");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using Book Store!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void adminMenu(Scanner sc, BookDAO bookDAO) {
        while (true) {
            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Books");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: // Add Book
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter Stock: ");
                    int stock = sc.nextInt();
                    sc.nextLine();

                    Book newBook = new Book(0, title, author, price, stock);
                    bookDAO.addBook(newBook);
                    break;
                case 2: // Update Book
                    System.out.print("Enter Book ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    Book updateBook = bookDAO.getBookById(updateId);
                    if (updateBook == null) {
                        System.out.println("Book not found!");
                        break;
                    }
                    System.out.print("Enter new Title: ");
                    updateBook.setTitle(sc.nextLine());
                    System.out.print("Enter new Author: ");
                    updateBook.setAuthor(sc.nextLine());
                    System.out.print("Enter new Price: ");
                    updateBook.setPrice(sc.nextDouble());
                    System.out.print("Enter new Stock: ");
                    updateBook.setStock(sc.nextInt());
                    sc.nextLine();
                    bookDAO.updateBook(updateBook);
                    break;
                case 3: // Delete Book
                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    bookDAO.deleteBook(deleteId);
                    break;
                case 4: // View Books
                    List<Book> books = bookDAO.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                case 5: // Back
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}