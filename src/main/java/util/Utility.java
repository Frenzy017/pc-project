package util;

import mediator.IMediator;

import java.util.Scanner;


public class Utility {

    private final IMediator mediator;

    public Utility(IMediator mediator) {
        this.mediator = mediator;
    }

    static Scanner scanner = new Scanner(System.in);

    public void printAccountOptions() {
        System.out.println("Choose a command before you continue: ");

        System.out.println();
        System.out.println("╔════ Account ═══╗");
        System.out.println("║ create / login ║");
        System.out.println("╚════════════════╝");

        System.out.print("Please enter a command: ");
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
        System.out.println("║ Items in cart --> [show]               ║");
        System.out.println("║ Clear cart --> [clear]                 ║");
        System.out.println("║ Deposit money --> [deposit]            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

    public void printAdminStoreInterface() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        Admin PC Store Interface        ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Available computers --> [show]         ║");
        System.out.println("║ Cart --> [cart]                        ║");
        System.out.println("║ View all users --> [view]              ║");
        System.out.println("║ Logout --> [logout]                    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

    public void printAdminComputerOptions() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          Admin Computer Options        ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Add computer to cart --> [add]         ║");
        System.out.println("║ Create a new computer object --> [new] ║");
        System.out.println("║ Modify computer object --> [modify]    ║");
        System.out.println("║ Delete computer object --> [delete]    ║");
        System.out.println("║ Return to main menu --> [back]         ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

    public void printCustomConfigurationOptions() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Please choose a configuration option  ║");
        System.out.println("║  by typing the corresponding number:   ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Processor Configuration             ║");
        System.out.println("║ 2. RAM Configuration                   ║");
        System.out.println("║ 3. Video Card Configuration            ║");
        System.out.println("║ 4. Check your computer build status    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.print("Configuration option: ");
    }

    public void printModificationOptions() {
        System.out.println("Which parameter do you want to modify?");
        System.out.println("1. Name");
        System.out.println("2. Processor");
        System.out.println("3. RAM");
        System.out.println("4. Video card");
        System.out.println("5. Quantity");

        System.out.print("Please enter the number which corresponds with the value you want to modify: ");
    }

    public void returnToConfigurationOptions() {
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        printCustomConfigurationOptions();
    }


    public void returnToAdminInterface() {
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        printAdminStoreInterface();
    }

    public void returnToAdminComputerOptions() {
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        printAdminComputerOptions();
    }

    public void returnToInterface() {
        int currentUserID = mediator.getUserHandler().getCurrentUserID();
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        if (mediator.getRoleService().getUserRoleById(currentUserID).equals("admin")) {
            printAdminStoreInterface();
        } else {
            printStoreInterface();
        }
    }

    public void returnToCartInterface() {
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        printCartInterface();
    }

    public String setUsername() {
        System.out.print("Please enter an username: ");
        return scanner.nextLine();
    }

    public String setPassword() {
        System.out.print("Please enter a password: ");
        return scanner.nextLine();
    }

    public String getUsername() {
        System.out.print("Please enter an username to log in: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.print("Please enter a password to log in: ");
        return scanner.nextLine();
    }
}