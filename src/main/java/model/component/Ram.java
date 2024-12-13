package model.component;

public class Ram {
    private int id;
    private int capacity;
    private int quantity;
    private int price;

    public Ram(int id, int capacity, int quantity, int price) {
        this.id = id;
        this.capacity = capacity;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(int generatedId) {
        this.id = generatedId;
    }
}
