import java.util.ArrayList;

public class Cart {
    private final ArrayList<Computer> cart = new ArrayList<>();
    private int totalPrice = 0;

    public void setCart(Computer computer) {
        cart.add(computer);

        System.out.println();
        System.out.println("You have successfully put your computer: " + computer + " in your cart!");
        System.out.println();
    }

    public void printCart() {
        System.out.println(cart);
    }
}
