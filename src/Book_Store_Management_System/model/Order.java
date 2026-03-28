package Book_Store_Management_System.model;

public class Order {
    // Private Access Modifier And Data Members
    private int id;
    private int userId;
    private int bookId;
    private int quantity;
    private double price;

    // Default Constructor
    public Order(){}

    // Parameterized Constructor
    public Order(int id,int userId,int bookId,int quantity,double price) {
        this.id=id;
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    // Setters
    public void setId(int id) {
        this.id=id;
    }
    public void setUserId(int userId) {
        if(userId < 0) {
            throw new IllegalArgumentException("User ID cannot be negative");
        }
        this.userId=userId;
    }
    public void setBookId(int bookId) {
        if(bookId < 0) {
            throw new IllegalArgumentException("Book ID cannot be negative");
        }
        this.bookId=bookId;
    }
    public void setQuantity(int quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity=quantity;
    }
    public void setPrice(double price) {
        if(price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price=price;
    }

    // Getters
    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public int getBookId() {
        return bookId;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
