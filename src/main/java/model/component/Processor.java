package model.component;

public class Processor {
    private int id;
    private String name;
    private int quantity;
    private int price;

    public Processor(int id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
