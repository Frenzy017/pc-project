import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreInventory {
    private String name;
    private String graphicCard;
    private int ram;
    private String processor;
    private long id;
    private int price;

    private final Scanner scanner = new Scanner(System.in);

    private final List<StoreInventory> computers = new ArrayList<>();

    public StoreInventory(String name, String graphicCard, int ram, String processor, long id, int price) {
        this.name = name;
        this.graphicCard = graphicCard;
        this.ram = ram;
        this.processor = processor;
        this.id = id;
        this.price = price;
    }

    public void availableComputers() {
        addComputers(
                new StoreInventory("Vortex", "NVIDIA RTX 4090", 32, "Intel Core i9-13900K", 1, 7000),
                new StoreInventory("Titan", "NVIDIA RTX 4080", 16, "AMD Ryzen 9 7900X", 2, 6200),
                new StoreInventory("Phantom", "NVIDIA RTX 4070 Ti", 32, "Intel Core i7-13700KF", 3, 5600),
                new StoreInventory("Blaze", "NVIDIA RTX 4060", 16, "AMD Ryzen 7 7800X", 4, 5000)
        );
    }

    public void addComputers(StoreInventory... computers) {
        for (StoreInventory computer : computers) {
            this.computers.add(computer);
        }
    }

    public void printAvailableComputers() {
        availableComputers();

        System.out.println();
        System.out.println("Here are the available computers:");

        for (StoreInventory computer : computers) {
            System.out.println("  Computer: " + computer.name + ", Specifications: " + computer.graphicCard + ", " + computer.ram + "GB RAM, " + computer.processor + ", " + "Price: " + computer.price + "$");
        }
    }


    public void selectComputer() {
        System.out.println();
        System.out.print("To select a computer, please type it's name: ");

        String selectedComputer = scanner.nextLine();
        System.out.println();

        for (StoreInventory computer : computers) {
            if (computer.name.equalsIgnoreCase(selectedComputer)) {
                System.out.println("You have selected the " + computer.name + " Computer" + " with the Specifications: " + computer.graphicCard + ", " + computer.ram + "GB RAM, " + computer.processor + ", " + "Price: " + computer.price + "$");
            }

            System.out.println();
            System.out.print("Do you want to add this computer to your cart? ");

            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("Yes")) {
                Cart cart = new Cart(computer);
                cart.setCart(computer);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Computer: " + name + ", Specifications: " + graphicCard + ", " + ram + "GB RAM, " + processor + ", Price: " + price + "$";
    }
}



