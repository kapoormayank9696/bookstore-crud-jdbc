package Book_Store_Management_System.src.model;

public class Book {

    // Private Access Modifier And Data Members
    private int id;
    private String title;
    private String author;
    private double price;
    private int stock;

    // Default Constructor
    public Book(){}

    // Parameterized Constructor
    public Book(int id,String title,String author,double price,int stock) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    // Setters
    public void setId(int id) {
        this.id=id;
    }
    public void setTitle(String title) {
        if(title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title=title;
    }
    public void setAuthor(String author) {
        if(author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author=author;
    }
    public void setPrice(double price) {
        if(price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price=price;
    }
    public void setStock(int stock) {
        if(stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock=stock;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "Book{"+
                "id="+id+
                ", title='"+title+'\'' +
                ", author='"+author+'\''+
                ", price="+price+
                ", stock="+stock+
                "}";
    }
}
