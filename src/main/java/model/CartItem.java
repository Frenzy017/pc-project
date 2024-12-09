package model;

public class CartItem {
    private int cartId;
    private String computerId;
    private double computerPrice;

    public CartItem(int cartId, String computerId, double computerPrice) {
        this.cartId = cartId;
        this.computerId = computerId;
        this.computerPrice = computerPrice;
    }

    public int getCartId() {
        return cartId;
    }

    public String getComputerId() {
        return computerId;
    }

    public double getComputerPrice() {
        return computerPrice;
    }

}
