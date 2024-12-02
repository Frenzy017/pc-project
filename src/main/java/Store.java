import java.io.*;
import java.util.*;

public class Store {
    Scanner scanner = new Scanner(System.in);
    Utility utility = new Utility();
    Cart cart = new Cart();

    private UserHandler userHandler;

    private final ArrayList<Computer> computers;
    private final ArrayList<Computer> initialComputers = new ArrayList<>();

    private final ComputerHandler computerHandler = new ComputerHandler();
    private final ComputerService computerService = new ComputerService();
    private final UserService userService = new UserService();

    private boolean isLogout;
    private boolean isComputerTableInitialized;

    public String currentUserID;

    public Store() {
        this.computers = new ArrayList<>(Arrays.asList(
                new Computer("1", "Vortex", "NVIDIA RTX 4090", 32, "Intel Core i9-13900K", 7000),
                new Computer("2", "Titan", "NVIDIA RTX 4080", 16, "AMD Ryzen 9 7900X", 6200),
                new Computer("3", "Phantom", "NVIDIA RTX 4070 Ti", 32, "Intel Core i7-13700KF", 5600),
                new Computer("4", "Blaze", "NVIDIA RTX 4060", 16, "AMD Ryzen 7 7800X", 5000)
        ));
        this.initialComputers.addAll(computers);
        loadConfig();
        initializeComputerTable();
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    public void start() {
        utility.printAccountOptions();

        while (!isLogout) {
            String command = scanner.nextLine();

            switch (command) {
                case "create" -> {
                    createUser();
                    start();
                }
                case "login" -> loginUser();
                case "show" -> handleComputerSelection();
                case "view" -> view();
                case "logout" -> logout();
                case "cart" -> {
                    cart.printCart(currentUserID);
                    utility.printCartInterface();

                    String cartCommand = scanner.nextLine();

                    switch (cartCommand) {
                        case "clear" -> {
                            cart.clearCart();
                            cart.resetStore(this);
                            utility.printCartInterface();
                        }
                        case "back" -> utility.printStoreInterface();
                        case "deposit" -> userService.depositMoney(currentUserID);
                        case "purchase" -> cart.purchase(currentUserID);
                        case "logout" -> logout();
                        default -> returnToInterface();
                    }
                }
                default -> printInterfaceBasedOnUserRole();
            }
        }
    }

    public void createUser() {
        userHandler.createUser();
    }

    public void loginUser() {
        userHandler.loginUser();
    }

    public void deleteUser() {
        userHandler.deleteUser();
    }

    public void updatePassword() {
        userHandler.updatePassword();
    }

    public void updateUsername() {
        userHandler.updateUsername();
    }

    public Computer selectComputer() {
        System.out.println();
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
        System.out.println();

        return selectedComputer;
    }

    public void handleComputerSelection() {
        if (userService.getUserRoleById(currentUserID).equals("admin")) {
            handleComputerSelectionAsAdmin();
        } else {
            handleComputerSelectionAsUser();
        }
    }

    public void handleComputerSelectionAsUser() {
        printComputers();

        Computer selectedComputer = selectComputer();

        System.out.print("Do you want to add this computer to your cart? [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("Yes")) {
            cart.addToCart(selectedComputer);
            computers.remove(selectedComputer);
            utility.printStoreInterface();
        } else if (command.equalsIgnoreCase("No")) {
            utility.printStoreInterface();
        } else {
            utility.invalidCommand();
        }
    }

    public void handleComputerSelectionAsAdmin() {
        printComputers();

        Computer selectedComputer = selectComputer();

        System.out.println("Here are the available computer options: ");
        utility.printAdminComputerOptions();

        String command = scanner.nextLine();

        switch (command) {
            case "back" -> utility.printAdminStoreInterface();
            case "add" -> {
                cart.addToCart(selectedComputer);
                computers.remove(selectedComputer);
                utility.printAdminStoreInterface();
            }
            case "create" -> computerHandler.createComputer(this);
            case "modify" -> computerHandler.modifyComputer(selectedComputer);
            case "delete" -> {
                printComputers();
                computerHandler.deleteComputer(this);
            }
            case "remove" -> {
//                userService.deleteUser(user);
            }
        }
    }

    public void view() {
        List<User> users = userService.getAllUserProperties();

        users.sort(Comparator.comparing((User user) -> !user.getRole().equals("admin")).thenComparing(User::getUsername));

        System.out.println("Registered Users:");

        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", Balance: " + user.getBalance() + ", Role: " + user.getRole());
        }

        System.out.print("Do you want to modify any user's data?  [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("yes")) {
            System.out.println();
            System.out.println("Here are 4 methods you can choose from:");
            System.out.println("Update user's username --> [username]");
            System.out.println("Update user's password --> [password]");
            System.out.println("Delete a user --> [delete]");
            System.out.println("Abort action --> [back]");
            System.out.println();
            System.out.println("Please choose a method: ");

            String chosenMethod = scanner.nextLine();

            switch (chosenMethod) {
                case "username" -> updateUsername();
                case "password" -> updatePassword();
                case "delete" -> deleteUser();
                case "back" -> returnToAdminInterface();
                default -> {
                    System.out.println("No current method available, please try again!");
                    scanner.nextLine();
                    utility.printAdminComputerOptions();
                }
            }

        } else if (command.equalsIgnoreCase("no")) {
            returnToAdminInterface();
        }
    }

    public void printComputers() {
        System.out.println();
        System.out.println("Here are the available computers:");

        for (int i = 1; i <= computers.size(); i++) {
            Computer computer = computers.get(i - 1);
            System.out.println(
                    i + ". Computer: "
                            + computer.name + ", Specifications: "
                            + computer.graphicCard + ", "
                            + computer.ram + "GB RAM, "
                            + computer.processor + ", "
                            + "Price: " + computer.price + "$");
        }
    }

    public void printInterfaceBasedOnUserRole() {
        String role = userService.getUserRoleById(currentUserID);

        if (role.equals("admin")) {
            utility.invalidCommand();
            utility.printAdminStoreInterface();
        } else {
            utility.invalidCommand();
            utility.printStoreInterface();
        }
    }

    public void logout() {
        System.out.println("You have successfully logged out!");
        System.out.println();
        isLogout = true;
    }

    public void returnToInterface() {
        System.out.println();
        utility.invalidCommand();
        utility.printCartInterface();
    }

    public void returnToAdminInterface() {
        System.out.println();
        System.out.print("Press Enter to return to the main interface...");
        scanner.nextLine();
        utility.printAdminStoreInterface();
    }

    public void initializeComputerTable() {
        if (!isComputerTableInitialized) {
            computerService.addComputersToDatabase(computers);
            isComputerTableInitialized = true;
            saveConfig();
        }
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            isComputerTableInitialized = Boolean.parseBoolean(properties.getProperty("isComputerTableInitialized"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveConfig() {
        Properties properties = new Properties();
        properties.setProperty("isComputerTableInitialized", Boolean.toString(isComputerTableInitialized));
        try (OutputStream output = new FileOutputStream("config.properties")) {
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public List<Computer> getInitialComputers() {
        return initialComputers;
    }
}


