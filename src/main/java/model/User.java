package model;

public class User {
    private int id;
    private String username;
    private String password;
    private int balance;
    private int role_Id;

    public User(int id, String username, String password, int balance, int role_Id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role_Id = role_Id;
    }

    public int getId() {
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

    public int getRole_Id() {
        return this.role_Id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setRole_Id(int role_Id) {
        this.role_Id = role_Id;
    }
}
