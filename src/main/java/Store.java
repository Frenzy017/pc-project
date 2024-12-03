import java.io.*;
import java.util.*;

public class Store {
    Scanner scanner = new Scanner(System.in);
    Utility utility = new Utility();
    Cart cart = new Cart();

    private ComputerHandler computerHandler = new ComputerHandler();
    private UserHandler userHandler;

    private final Map<String, Runnable> commandMap = new HashMap<>();

    private final ArrayList<Computer> computers;
    private final ArrayList<Computer> initialComputers = new ArrayList<>();

    private final ComputerService computerService = new ComputerService();
    private final UserService userService = new UserService();

    private boolean isLogout;
    private boolean isComputerTableInitialized;

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
        initializeCommandMap();
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    private void initializeCommandMap() {
        commandMap.put("create", this::createUser);
        commandMap.put("login", this::loginUser);
        commandMap.put("show", this::showComputers);
        commandMap.put("view", this::viewUsers);
        commandMap.put("logout", this::logout);
//        commandMap.put("cart", this::handleCart);
    }

    public void start() {
        utility.printAccountOptions();

        while (!isLogout) {
            String command = scanner.nextLine();
            Runnable action = commandMap.getOrDefault(command, this::printInterfaceBasedOnUserRole);
            action.run();
        }
    }

    public void createUser() {
        userHandler.handleCreateUser();
    }

    public void loginUser() {
        userHandler.handleLoginUser();
    }

    public void viewUsers() {
        userHandler.handleViewAllUsers();
        userHandler.handleViewAllUserOptions();

    }

    public void showComputers() {
        String currentUserID = userHandler.getCurrentUserID();

        if (userService.getUserRoleById(currentUserID).equals("admin")) {
            handleComputerSelectionAsAdmin();
        } else {
            handleComputerSelectionAsUser();
        }
    }

    public void handleComputerSelectionAsUser() {
        computerHandler.handleComputerPrint();
        // Cart implement still in progression.
        computerHandler.handleSelectComputerAndAddToCart();
    }

    public void handleComputerSelectionAsAdmin() {
        computerHandler.handleComputerPrint();
        computerHandler.handleComputerObjectOptions();
    }


    public void printInterfaceBasedOnUserRole() {
        String role = userService.getUserRoleById(userHandler.currentUserID);

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

    public void initializeComputerTable() {
        if (!isComputerTableInitialized) {
            computerService.addComputersToDatabase(computers);
            isComputerTableInitialized = true;
            saveConfig();
        }
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public List<Computer> getInitialComputers() {
        return initialComputers;
    }
}


