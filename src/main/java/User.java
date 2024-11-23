import java.io.Serializable;
import java.util.Scanner;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private long id;
    private int balance;

    Scanner scanner = new Scanner(System.in);

    public User(String username, String password, long id, int balance)   {
        this.username = username;
        this.password = password;
        this.id = id;
        this.balance = balance;
    }



    public String getUsername() {return this.username;}

    public String getPassword() {
        return this.password;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void depositMoney() {
        System.out.println("Please enter any amount to deposit: ");

        int amount = scanner.nextInt();

        if (amount > 0) {
            setBalance(getBalance() + amount);
            System.out.println("You have successfully put: " + amount + " $ in your account!");
        } else {
            System.out.println("Please enter a positive amount!");
            depositMoney();
        }
    }
}
