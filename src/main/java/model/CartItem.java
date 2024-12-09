package model;

public class CartItem {
    private int cartId;
    private int computerId;
    private double computerPrice;

    public CartItem(int cartId, int computerId, double computerPrice) {
        this.cartId = cartId;
        this.computerId = computerId;
        this.computerPrice = computerPrice;
    }

    public int getCartId() {
        return cartId;
    }

    public int getComputerId() {
        return computerId;
    }

    public double getComputerPrice() {
        return computerPrice;
    }

}
