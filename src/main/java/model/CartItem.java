package model;

public class CartItem {
    private int cartId;
    private String computerId;
    private String computerName;
    private double computerPrice;

    public CartItem(int cartId, String computerId, String computerName, double computerPrice) {
        this.cartId = cartId;
        this.computerId = computerId;
        this.computerName = computerName;
        this.computerPrice = computerPrice;
    }

    public int getCartId() {
        return cartId;
    }

    public String getComputerId() {
        return computerId;
    }

    public String getComputerName() {
        return computerName;
    }

    public double getComputerPrice() {
        return computerPrice;
    }

}
