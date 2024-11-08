import java.util.ArrayList;
import java.util.List;

public class Cart {
    private ArrayList<Computer> cart = new ArrayList<>();
    private int totalPrice;
    private int quantity;

    public Cart(int totalPrice, int quantity) {
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public void setCart(Computer computer) {
        cart.add(computer);
        totalPrice += computer.price;
        quantity++;

        System.out.println();
        System.out.println("You have successfully put your computer: " + computer + " in your cart!");
        System.out.println();
    }

    public void printCart() {
        System.out.println("Currently you have " + quantity + " computers in your cart.");
        System.out.println("Total price of your cart is: " + totalPrice + "$.");
        System.out.println("Here are the computers in your cart: ");
        System.out.println();

        if (cart == null || cart.isEmpty()) {
            System.out.println("Currently there are no items in the cart, please select items to proceed further.");
        } else {
            for (Computer computer : cart) {
                System.out.println(computer);
            }
        }
    }

    public void clearCart() {
        quantity = 0;
        totalPrice = 0;
        cart.clear();
    }

    public void resetComputers(List<Computer> initialComputers, List<Computer> computers) {
        computers.clear();
        computers.addAll(initialComputers);
    }

}
