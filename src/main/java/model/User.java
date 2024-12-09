package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int balance;

    public User(int id, String username, String password, int balance, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getBalance() {
        return this.balance;
    }

    public String getRole() {
        return this.role;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
