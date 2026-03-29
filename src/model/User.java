package Book_Store_Management_System.src.model;

public class User {

    // Private Access Modifier And Data Members
    private int id;
    private String name;
    private String email;
    private String password;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(int id,String name,String email,String password) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    // Setters
    public void setId(int id) {
        this.id=id;
    }
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
        this.name=name;
    }
    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email=email;
    }
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password=password; // store plain text here; hash in DAO
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='"+ email + '\'' +
                '}';
    }
}
