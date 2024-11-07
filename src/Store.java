import java.util.*;

public class Store {
    private ArrayList<User> usersList = new ArrayList<>();
    private List<Computer> computers = new ArrayList<>();
    private Cart cart = new Cart();

    Scanner scanner = new Scanner(System.in);

    public Store() {
        this.computers = Arrays.asList(
                new Computer("Vortex", "NVIDIA RTX 4090", 32, "Intel Core i9-13900K", 1, 7000),
                new Computer("Titan", "NVIDIA RTX 4080", 16, "AMD Ryzen 9 7900X", 2, 6200),
                new Computer("Phantom", "NVIDIA RTX 4070 Ti", 32, "Intel Core i7-13700KF", 3, 5600),
                new Computer("Blaze", "NVIDIA RTX 4060", 16, "AMD Ryzen 7 7800X", 4, 5000)
        );
    }

    public void start() {
        System.out.println("Choose a command before you continue: ");

        System.out.println();
        System.out.println("╔════ Account ═══╗");
        System.out.println("║ create / login ║");
        System.out.println("╚════════════════╝");

        System.out.print("Please enter a command: ");

        while (true) {
            String command = scanner.nextLine();

            switch (command) {
                case "create" -> {
                    createUser();
                    returnToMenu();
                }
                case "login" -> {
                    loginUser();
                    Utility.printStoreInterface();
                }
                case "show" -> {
                    printComputers();
                    selectComputer();
                }
                case "cart" -> cart.printCart();
                case "logout" -> logout();
                default -> retry();
            }
        }
    }

    public void createUser() {
        String username = Utility.getUsername();
        String password = Utility.getPassword();

        usersList.add(new User(username, password, 123));

        System.out.println();
        System.out.println("Account created successfully!");
        System.out.println();
    }

    public void loginUser() {
        String username = Utility.getUsername();
        String password = Utility.getPassword();

        boolean validLogin = false;

        for (User user : usersList) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)) {
                validLogin = true;
                break;
            }
        }
        if (validLogin) {
            System.out.println();
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println();
            System.out.println("Sorry wrong input, please try again!");
            loginUser();
        }
    }

    public void printComputers() {
        System.out.println();
        System.out.println("Here are the available computers:");

        int index = 1;

        for (Computer computer : computers) {
            System.out.println(index + ". Computer: " + computer.name + ", Specifications: " + computer.graphicCard + ", " + computer.ram + "GB RAM, " + computer.processor + ", " + "Price: " + computer.price + "$");
            index++;
        }
    }

    public void selectComputer() {
        System.out.println();
        System.out.print("To select a computer, please type its number: ");

        int selectedNumber = scanner.nextInt();
        scanner.nextLine();

        if (selectedNumber < 1 || selectedNumber > computers.size()) {
            System.out.println("Invalid selection. Please try again.");
            selectComputer();
            return;
        }

        Computer selectedComputer = computers.get(selectedNumber - 1);

        System.out.println("You have selected the "
                + selectedComputer.name + " Computer" + " with the Specifications: " + selectedComputer.graphicCard + ", "
                + selectedComputer.ram + "GB RAM, " + selectedComputer.processor + ", " + "Price: " + selectedComputer.price + "$"
        );

        System.out.println();
        System.out.print("Do you want to add this computer to your cart? [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("Yes")) {
            Cart cart = new Cart();
            cart.setCart(selectedComputer);
            Utility.printStoreInterface();

        } else if (command.equalsIgnoreCase("No")) {
            Utility.printStoreInterface();
        }
    }

    public void retry() {
        System.out.println();
        System.out.println("Invalid command, please try again! ");
        System.out.println();
        start();
    }

    public void returnToMenu() {
        start();
    }

    public void logout() {
        System.out.println("You have successfully logged out!");
        System.out.println();
        start();
    }

}


