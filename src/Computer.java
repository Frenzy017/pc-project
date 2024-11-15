import java.util.ArrayList;
import java.util.Scanner;

public class Computer {
    public String name;
    public String graphicCard;
    public int ram;
    public String processor;
    public long id;
    public int price;

    public Computer(String name, String graphicCard, int ram, String processor, long id, int price) {
        this.name = name;
        this.graphicCard = graphicCard;
        this.ram = ram;
        this.processor = processor;
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ", with the Specifications: " + graphicCard + ", " + ram + "GB RAM, " + processor + ", Price: " + price + "$";
    }

}
