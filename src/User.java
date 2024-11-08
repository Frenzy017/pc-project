import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private long id;
    private int balance;

    Scanner scanner = new Scanner(System.in);

    public User(String username, String password, long id, int balance) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.balance = balance;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void depositMoney() {
        if (balance == 0) {
            System.out.println("Please deposit some money:");
            int money = scanner.nextInt();


        }
    }

}
