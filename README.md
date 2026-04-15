# 📚 Book Store Management System
[![Java](https://img.shields.io/badge/Java-17+-blue)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8+-green)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)
A console-based Book Store Management System in Java that allows users to register, login, view books, place orders, and for admins to manage the store inventory. Implements JDBC, DAO pattern, and secure password storage with BCrypt.


---

✅ Features

User Features

Register with name, email, and password (hashed using BCrypt)
Login with email and password
View all available books with price and stock
Place orders with automatic stock validation

Admin Features

Add, update, and delete books
View all books in inventory
Admin credentials (hardcoded):
Username: admin
Password: 1234

---

## Technologies Used

- **Java 17+**
- **MySQL 8+**
- **JDBC** for database connectivity
- **DAO Design Pattern** for database operations
- **Service Layer** for business logic
- **Console-based UI** using `Scanner`

---

## 🗂 Project Structure
Book_Store_Management_System/
│
├─ src/
│  ├─ DAO/           # Data Access Objects: UserDAO, BookDAO, OrderDAO
│  ├─ model/         # Model classes: User, Book, Order, OrderItem
│  ├─ service/       # Business logic layer
│  ├─ util/          # DBConnection utility
│  └─ Main.java      # Entry point
│
├─ lib/              # External libraries (MySQL connector, BCrypt)
└─ README.md
- 
- └─ README.md

---

### ⚙️ Requirements
Java JDK 17+ (tested with JDK 24)
MySQL Database (or PostgreSQL if configured)
External libraries (add to project classpath if not using Maven):
mysql-connector-java-9.4.0.jar
jbcrypt-0.4.jar

---

### 🗄 Database Setup

Create a database (e.g., book_store) and tables:

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(100),
    price DOUBLE,
    stock INT
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total_amount DOUBLE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    book_id INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

---

### ▶️ How to Run
Open the project in IntelliJ IDEA.
Add external libraries to the project (if not using Maven):
mysql-connector-java-9.4.0.jar
jbcrypt-0.4.jar
Configure DBConnection.java with your database credentials.
Run Main.java.
Use the console menu to interact with the system.

---

### 📝 Usage
Main Menu
1. Register
2. Login
3. View Books
4. Place Order
5. Admin Panel
6. Exit
Register: Create a new user account.
Login: Log in as an existing user.
View Books: Display all available books.
Place Order: Purchase books (login required).
Admin Panel: Manage books (admin credentials required).
Exit: Close the application.

---

### ⚠️ Notes
Passwords are stored securely using BCrypt hashing.
Admin login is currently hardcoded (admin / 1234).
Orders automatically update book stock.
Input validation is implemented for user and book details.
---

### License 📝
This project is open-source and available under the MIT License.

Author

Mayank Kapoor
GitHub: https://github.com/kapoormayank9696
---

If you want, I can also **make a shorter, more attractive GitHub-ready version** that looks modern with badges for Java, MySQL, and license.  

Do you want me to do that version too?

---

### 🚀 Future Improvements
Use a database for admin authentication instead of hardcoded credentials.
Implement order history for users.
Add a GUI for a better user experience.
Support multiple admin users with roles and permissions.
