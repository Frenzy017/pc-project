import java.util.Map;
import java.util.Scanner;

public class Account {
    private final String username;
    private final String password;
    private final Scanner scanner = new Scanner(System.in);

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void create(AccountRepository accountRepository) {

        System.out.print("Please enter an username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter a password: ");
        String password = scanner.nextLine();

        accountRepository.add(new Account(username, password));
        System.out.println("Account created successfully!");
        System.out.println();
        System.out.println("#### Please proceed to log in ####");

        login(accountRepository);
    }

    public void login(AccountRepository accountRepository) {

        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        Map<String, String> loginInput = accountRepository.getAccounts();

        boolean isUsernamePresent = loginInput.containsKey(username);
        boolean isPasswordCorrect = isUsernamePresent && loginInput.get(username).equals(password);
        boolean validLogin = isUsernamePresent && isPasswordCorrect;

        if (validLogin) {
            System.out.println();
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println("Sorry wrong input, please try again!");
            login(accountRepository);
        }

        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            PC Store Interface          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Available items --> (show)          ║");
        System.out.println("║ 2. Logout --> (logout)                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        System.out.print("Choose a command before you continue! ");
        System.out.print("");

        String command = scanner.nextLine();

        StoreInventory computer = new StoreInventory("", "", 0, "", 0, 0);

        switch (command) {
            case "logout" -> logout();
            case "show" -> {
                computer.printAvailableComputers();
                computer.selectComputer();
            }
        }
    }

    public void logout() {
        System.out.println("You have successfully logged out!");
    }
}
