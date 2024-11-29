import java.util.ArrayList;
import java.util.Scanner;

public class Cart {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Computer> cart = new ArrayList<>();
    private final Utility utility = new Utility();
    private int totalPrice;

    public void addToCart(Computer computer) {
        cart.add(computer);

        totalPrice += computer.price;

        System.out.println();
        System.out.println("You have successfully put your computer: " + computer + " in your cart!");
        System.out.println();
    }

    public void purchase(String userId) {
        UserService userService = new UserService();
        int balance = userService.getBalanceById(userId);

        if (balance >= totalPrice) {
            int newBalance = balance - totalPrice;
            userService.updateUserBalance(userId, newBalance);
            clearCart();
            System.out.println("Purchase successful! Your new balance is: " + balance + "$.");
            utility.printCartInterface();
        } else {
            System.out.println("Insufficient funds, please deposit more money to complete the purchase :)");
            System.out.println("To return, please press Enter...");
            scanner.nextLine();
            utility.printCartInterface();
        }
    }

    public void printCart(String userID) {
        UserService userService = new UserService();
        int balance = userService.getBalanceById(userID);

        System.out.println("Currently you have " + cart.size() + " computers in your cart.");
        System.out.println("Your current balance is: " + balance + "$.");
        System.out.println("Total price of your cart is: " + totalPrice + "$.");
        System.out.println("Here are the computers in your cart: ");
        System.out.println();

        if (cart.isEmpty()) {
            System.out.println("Currently there are no items in the cart, please select items to proceed further.");
        } else {
            for (Computer computer : cart) {
                System.out.println(computer);
            }
        }
    }

    public void clearCart() {
        totalPrice = 0;
        cart.clear();
    }

    public void resetStore(Store store) {
        store.getComputers().clear();
        store.getComputers().addAll(store.getInitialComputers());
    }

}
