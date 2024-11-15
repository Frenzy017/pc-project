import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
    private static final Scanner scanner = new Scanner(System.in);

    public String getUsername() {
        System.out.print("Please enter an username: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.print("Please enter a password: ");
        return scanner.nextLine();
    }

    public void invalidCommand() {
        System.out.println("Invalid command, please try again! ");
    }

    public void println() {
        System.out.println();
    }

    public void printWelcomeMessage() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      Welcome to PC Shop!     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();
    }

    public void printStoreInterface() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            PC Store Interface          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Available items --> [show]             ║");
        System.out.println("║ Cart --> [cart]                        ║");
        System.out.println("║ Logout --> [logout]                    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

    public void printCartInterface() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            Cart Interface              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Return to main menu --> [back]         ║");
        System.out.println("║ Items in cart --> [cart]               ║");
        System.out.println("║ Clear cart --> [clear]                 ║");
        System.out.println("║ Deposit money --> [deposit]            ║");
        System.out.println("║ Log out --> [logout]                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

    public void printModificationOptions() {
        System.out.println("Which parameter do you want to modify?");
        System.out.println("1. Name");
        System.out.println("2. GraphicCard");
        System.out.println("3. RAM");
        System.out.println("4. Processor");
        System.out.println("5. Price");

        System.out.print("Please enter the number which corresponds with the value you want to modify: ");
    }



    public void printAdminOptions() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          Admin Cart Interface          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Return to main menu --> [back]         ║");
        System.out.println("║ Add computer to cart --> [add]         ║");
        System.out.println("║ Create a new computer object --> [new] ║");
        System.out.println("║ Modify computer object --> [modify]    ║");
        System.out.println("║ Delete computer object --> [delete]    ║");
        System.out.println("║ View all users --> [view]              ║");
        System.out.println("║ Remove an user --> [remove]            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }


    public Computer selectComputer(ArrayList<Computer> computers, Scanner scanner) {
        println();
        System.out.print("To select a computer, please type its number: ");

        int selectedNumber = scanner.nextInt();
        scanner.nextLine();

        if (selectedNumber < 1 || selectedNumber > computers.size()) {
            System.out.println("Invalid selection. Please try again.");
            return null;
        }

        Computer selectedComputer = computers.get(selectedNumber - 1);

        System.out.println(
                "You have selected the "
                        + selectedComputer.name + " Computer" + " with the Specifications: " + selectedComputer.graphicCard + ", "
                        + selectedComputer.ram + "GB RAM, " + selectedComputer.processor + ", " + "Price: " + selectedComputer.price + "$"
        );
        println();

        return selectedComputer;
    }
}