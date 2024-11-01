import java.util.Scanner;

public class PCShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AccountRepository inAccountRepository = new AccountRepository();
        Account account = new Account("", "");

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      Welcome to PC Shop!     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();

        System.out.println("Choose a command before you continue!");

        System.out.println();
        System.out.println("╔═══════════ Account ══════════╗");
        System.out.println("║ ~~~~~~ create / login ~~~~~~ ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Please enter a command: ");
        String command = scanner.nextLine();

        switch (command) {
            case "create" -> account.create(inAccountRepository);
            case "login" -> account.login(inAccountRepository);
            case "logout" -> account.logout(); // Fix the method not working properly
            default -> System.out.println("Invalid command, please try again");
        }
    }
}