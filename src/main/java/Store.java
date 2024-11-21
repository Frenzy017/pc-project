import java.io.FileNotFoundException;
import java.util.*;

public class Store {
    private final ArrayList<Computer> computers;
    private final ArrayList<Computer> initialComputers = new ArrayList<>();
    private final ArrayList<User> usersList = new ArrayList<>();
    private final Admin admin = new Admin();

    private boolean isAdmin;
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


    public void start() throws FileNotFoundException {
        utility.printAccountOptions();

        while (true) {
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

    public void createUser() throws FileNotFoundException {
        String username = utility.getUsername();
        String password = utility.getPassword();

        isAdmin = username.equals("admin123") && password.equals("admin123");

        usersList.add(new User(username, password, 123, 0, isAdmin));

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
        String username = utility.getUsername();
        String password = utility.getPassword();

        boolean validLogin = false;

        for (User user : usersList) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)) {
                validLogin = true;
                break;
            }
        }
        if (validLogin) {
            utility.println();
            System.out.println("You have successfully logged in!");
        } else {
            utility.println();
            System.out.println("Sorry wrong input, please try again!");
            loginUser();
        }
    }

    public void logout() throws FileNotFoundException {
        System.out.println("You have successfully logged out!");
        utility.println();
        start();
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
        Computer selectedComputer = utility.selectComputer(computers, scanner);

        if (selectedComputer == null) {
            handleComputerSelectionAsUser();
            return;
        }

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
        Computer selectedComputer = utility.selectComputer(computers, scanner);

        if (selectedComputer == null) {
            handleComputerSelectionAsAdmin();
            return;
        }

        utility.printAdminOptions();

        String command = scanner.nextLine();

        switch (command) {
            case "back" -> utility.printStoreInterface();
            case "add" -> {
                cart.addToCart(selectedComputer);
                computers.remove(selectedComputer);
                utility.printStoreInterface();
            }
            case "create" -> admin.createComputer(this);
            case "modify" -> admin.modifyComputer(selectedComputer);
            case "delete" -> {
                printComputers();
                admin.deleteComputer(this);
            }
        }
    }

    public void printComputers() {
        utility.println();
        System.out.println("Here are the available computers:");

        int index = 1;

        for (Computer computer : computers) {
            System.out.println(
                    index + ". Computer: "
                            + computer.name + ", Specifications: "
                            + computer.graphicCard + ", "
                            + computer.ram + "GB RAM, "
                            + computer.processor + ", "
                            + "Price: " + computer.price + "$");
            index++;
        }
    }

    public void returnToStart() throws FileNotFoundException {
        utility.println();
        utility.invalidCommand();
        utility.println();
        start();
    }

    public void returnToInterface() {
        utility.println();
        utility.invalidCommand();
        utility.printCartInterface();
    }


}


