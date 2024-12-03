import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Utility utility = new Utility();
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        UserService userService = new UserService();

        UserHandler userHandler = new UserHandler(userService, utility, scanner, store);
        store.setUserHandler(userHandler);

        utility.printWelcomeMessage();
        store.start();
    }
}