import java.util.List;
import java.util.Scanner;

public class ComputerHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final Utility utility = new Utility();

    public void modifyComputer(Computer selectedComputer) {
        utility.printModificationOptions();

        int modifyCommand = scanner.nextInt();

//      System.out.println("1. Name");
//      System.out.println("2. GraphicCard");
//      System.out.println("3. RAM");
//      System.out.println("4. Processor");
//      System.out.println("5. Price");

        switch (modifyCommand) {
            case 1 -> {
                System.out.println();
                System.out.print("Please type what name do you want to rename your PC to: ");
                scanner.nextLine();
                selectedComputer.name = scanner.nextLine();
                utility.printSuccessfulCommand();
                utility.printAdminOptions();
            }
            case 2 -> {
                System.out.println();
                System.out.print("Please type what model do you want to change your graphic card to: ");
                scanner.nextLine();
                selectedComputer.graphicCard = scanner.nextLine();
                utility.printSuccessfulCommand();
                utility.printAdminOptions();
            }
            case 3 -> {
                System.out.println();
                System.out.print("Please type what size do you want to change your RAM to: ");
                scanner.nextLine();
                selectedComputer.ram = scanner.nextInt();
                utility.printSuccessfulCommand();
                utility.printAdminOptions();
            }
            case 4 -> {
                System.out.println();
                System.out.print("Please type what model do you want to change your processor to: ");
                scanner.nextLine();
                selectedComputer.processor = scanner.nextLine();
                utility.printSuccessfulCommand();
                utility.printAdminOptions();
            }
            case 5 -> {
                System.out.println();
                System.out.print("Please type what price do you want to set your PC to: ");
                scanner.nextLine();
                selectedComputer.price = scanner.nextInt();
                utility.printSuccessfulCommand();
                utility.printAdminOptions();
            }
            default -> utility.invalidCommand();
        }
    }

    public void createComputer(Store store) {
        System.out.print("Enter computer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter graphic card: ");
        String graphicCard = scanner.nextLine();

        System.out.print("Enter RAM size (GB): ");
        int ram = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter processor: ");
        String processor = scanner.nextLine();

        System.out.print("Enter price: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        Computer newComputer = new Computer(name, graphicCard, ram, processor, store.getComputers().size() + 1, price);
        store.getComputers().add(newComputer);

        System.out.println("New computer created successfully!");
        utility.printAdminOptions();
    }

    public void deleteComputer(Store store) {
        List<Computer> computersList = store.getComputers();

        System.out.println();
        System.out.println("Please type the number of the computer you want to delete: ");

        int selectedComputer = scanner.nextInt();

        computersList.remove(selectedComputer);

        System.out.println();
        System.out.println("You have successfully deleted the computer!");

        utility.printAdminOptions();
    }
}
