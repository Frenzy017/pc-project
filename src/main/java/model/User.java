package model;

public class User {
    private String id;
    private String username;
    private String password;
    private String role;
    private int balance;


    public User(String id, String username, String password, int balance, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public String getId() {
        return id;
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
