import java.util.Scanner;

public class Utility {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUsername() {
        System.out.print("Please enter an username: ");
        return scanner.nextLine();
    }

    public static String getPassword() {
        System.out.print("Please enter a password: ");
        return scanner.nextLine();
    }

    public void printWelcomeMessage() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      Welcome to PC Shop!     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();
    }

    public static void printStoreInterface() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            PC Store Interface          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Available items --> [show]          ║");
        System.out.println("║ 2. Logout --> [logout]                 ║");
        System.out.println("║ 3. Cart --> [cart]                     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue: ");
        System.out.print("");
    }

}