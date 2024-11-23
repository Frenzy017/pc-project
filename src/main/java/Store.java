import java.util.*;

public class Store {
    private final ArrayList<Computer> computers;
    private final ArrayList<Computer> initialComputers = new ArrayList<>();
    private final ArrayList<User> usersList = new ArrayList<>();
    private final ComputerHandler computerHandler = new ComputerHandler();

    private boolean isAdmin;
    private boolean isLogout;
    private User user;

    Scanner scanner = new Scanner(System.in);
    Utility utility = new Utility();
    Cart cart = new Cart();

    public Store() {
        this.computers = new ArrayList<>(Arrays.asList(
                new Computer("Vortex", "NVIDIA RTX 4090", 32, "Intel Core i9-13900K", 1, 7000),
                new Computer("Titan", "NVIDIA RTX 4080", 16, "AMD Ryzen 9 7900X", 2, 6200),
                new Computer("Phantom", "NVIDIA RTX 4070 Ti", 32, "Intel Core i7-13700KF", 3, 5600),
                new Computer("Blaze", "NVIDIA RTX 4060", 16, "AMD Ryzen 7 7800X", 4, 5000)
        ));
        this.initialComputers.addAll(computers);
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public List<Computer> getInitialComputers() {
        return initialComputers;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void start()  {
        utility.printAccountOptions();

        while (!isLogout) {
            String command = scanner.nextLine();

            switch (command) {
                case "create" -> {
                    createUser();
                    start();
                }
                case "login" -> {
                    loginUser();
                    utility.printStoreInterface();
                }
                case "show" -> {
                    printComputers();
                    handleComputerSelection();
                }
                case "logout" -> logout();
                case "cart" -> {
                    cart.printCart();
                    utility.printCartInterface();

                    String cartCommand = scanner.nextLine();

                    switch (cartCommand) {
                        case "clear" -> {
                            cart.clearCart();
                            cart.resetStore(this);
                            utility.printCartInterface();
                        }
                        case "back" -> utility.printStoreInterface();
                        case "deposit" -> user.depositMoney();
                        case "logout" -> logout();
                        default -> returnToInterface();
                    }
                }
                default -> returnToStart();
            }
        }
    }

    public void createUser()  {
        String username = utility.setUsername();
        String password = utility.setPassword();

        isAdmin = username.equals("admin123") && password.equals("admin123");

        usersList.add(new User(username, password, 123, 0));

        for (User user : usersList) {
            this.user = user;
            cart.setUser(user);
        }

        System.out.println();
        System.out.println("Account created successfully!");
        System.out.println();

        Serializer serializer = new Serializer();
        serializer.serializeUsers("users.ser");
    }

    public void loginUser() {
        String usernameToLogin = utility.getUsername();
        String passwordToLogin = utility.getPassword();

        boolean validLogin = false;

        for (User user : usersList) {
            if (Objects.equals(user.getUsername(), usernameToLogin) && Objects.equals(user.getPassword(), passwordToLogin)) {
                validLogin = true;
                break;
            }
        }

        System.out.println();

        if (validLogin) {
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println("Sorry wrong input, please try again!");
            loginUser();
        }
    }

    public void logout()  {
        System.out.println("You have successfully logged out!");
        System.out.println();
        isLogout = true;
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
        Serializer serializer = new Serializer();
        serializer.serializeComputers("computers.ser");

        if (isAdmin) {
            handleComputerSelectionAsAdmin();
        } else {
            handleComputerSelectionAsUser();
        }
    }

    public void handleComputerSelectionAsUser() {
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
        Computer selectedComputer = selectComputer();

        utility.printAdminOptions();

        String command = scanner.nextLine();

        switch (command) {
            case "back" -> utility.printStoreInterface();
            case "add" -> {
                cart.addToCart(selectedComputer);
                computers.remove(selectedComputer);
                utility.printStoreInterface();
            }
            case "create" -> computerHandler.createComputer(this);
            case "modify" -> computerHandler.modifyComputer(selectedComputer);
            case "delete" -> {
                printComputers();
                computerHandler.deleteComputer(this);
            }
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

    public void returnToStart() {
        System.out.println();
        utility.invalidCommand();
        System.out.println();
        start();
    }

    public void returnToInterface() {
        System.out.println();
        utility.invalidCommand();
        utility.printCartInterface();
    }
}


