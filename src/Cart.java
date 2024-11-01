import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<StoreInventory> cart = new ArrayList<StoreInventory>();
    private StoreInventory computer;

    public Cart(StoreInventory computer) {
        this.computer = computer;
    }
    
    public void setCart(StoreInventory computer) {
        cart.add(computer);

        // add return methods in Storeinventory to get names etc needed for print

        // TO IMPLEMENT:
        // DELETE A CERTAIN ITEM
        //


        System.out.println();
        System.out.println("You have successfully put your computer: " + computer + " in your cart!");
    }
}
