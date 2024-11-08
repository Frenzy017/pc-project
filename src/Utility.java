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
        System.out.println("║ Available items --> [show]             ║");
        System.out.println("║ Items in cart --> [cart]               ║");
        System.out.println("║ Clear cart --> [clear]                 ║");
        System.out.println("║ Log out --> [logout]                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }
}