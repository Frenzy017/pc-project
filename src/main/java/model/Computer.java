package model;

public class Computer {
    public int id;
    public String name;
    public String graphicCard;
    public String processor;
    public int ram;
    public int price;

    public Computer(int id, String name, String graphicCard, int ram, String processor, int price) {
        this.id = id;
        this.name = name;
        this.graphicCard = graphicCard;
        this.ram = ram;
        this.processor = processor;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGraphicCard(String graphicCard) {
        this.graphicCard = graphicCard;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGraphicCard() {
        return this.graphicCard;
    }

    public int getRam() {
        return this.ram;
    }

    public String getProcessor() {
        return this.processor;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return name + ", with the Specifications: " + graphicCard + ", " + ram + "GB RAM, " + processor + ", Price: " + price + "$";
    }
}
