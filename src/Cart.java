import java.util.ArrayList;
import java.util.Scanner;

public class Cart {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Computer> cart = new ArrayList<>();
    private int totalPrice;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void addToCart(Computer computer) {
        cart.add(computer);

        totalPrice += computer.price;

        System.out.println();
        System.out.println("You have successfully put your computer: " + computer + " in your cart!");
        System.out.println();
    }

    public void printCart() {
        System.out.println("Currently you have " + cart.size() + " computers in your cart.");
        System.out.println("Your current balance is: " + user.getBalance() + "$.");
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
