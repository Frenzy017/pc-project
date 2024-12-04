package store;

import java.io.*;
import java.util.*;
import util.Utility;

import handler.ComputerHandler;
import handler.UserHandler;

import model.Computer;

import service.ComputerService;
import service.UserService;

import mediator.IMediator;

public class Store {
    private final Utility utility;
    private final Scanner scanner;

    private final UserHandler userHandler;
    private final ComputerHandler computerHandler;

    private final ComputerService computerService;
    private final UserService userService;

    private final ArrayList<Computer> computers;

    private final IMediator mediator;
    private final Map<String, Runnable> commandMap = new HashMap<>();

    private boolean isLogout;
    private boolean isComputerTableInitialized;

    public Store(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.userHandler = mediator.getUserHandler();
        this.computerHandler = mediator.getComputerHandler();
        this.computerService = mediator.getComputerService();
        this.userService = mediator.getUserService();

        this.computers = new ArrayList<>(Arrays.asList(
                new Computer("1", "Vortex", "NVIDIA RTX 4090", 32, "Intel Core i9-13900K", 7000),
                new Computer("2", "Titan", "NVIDIA RTX 4080", 16, "AMD Ryzen 9 7900X", 6200),
                new Computer("3", "Phantom", "NVIDIA RTX 4070 Ti", 32, "Intel Core i7-13700KF", 5600),
                new Computer("4", "Blaze", "NVIDIA RTX 4060", 16, "AMD Ryzen 7 7800X", 5000)
        ));
        loadConfig();
        initializeComputerTable();
        initializeCommandMap();
    }

    private void initializeCommandMap() {
        commandMap.put("start", () -> mediator.notify(this, "start"));
        commandMap.put("create", () -> mediator.notify(this, "createUser"));
        commandMap.put("login", () -> mediator.notify(this, "login"));
        commandMap.put("show", () -> mediator.notify(this, "showComputers"));
        commandMap.put("view", () -> mediator.notify(this, "viewUsers"));
        commandMap.put("logout", () -> mediator.notify(this, "logout"));
        commandMap.put("cart", () -> mediator.notify(this, "cart"));
    }

    public void start() {
        System.out.println("Store has started.");

        utility.printAccountOptions();

        while (!isLogout) {
            String command = scanner.nextLine();
            Runnable action = commandMap.getOrDefault(command, this::printInterfaceBasedOnUserRole);
            action.run();
        }
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
        computerHandler.handleComputerSelection();
    }

    public void handleComputerSelectionAsAdmin() {
        computerHandler.handleComputerPrint();
        computerHandler.handleComputerOptions();
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

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
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

    public void logout() {
        System.out.println("You have successfully logged out!");
        System.out.println();
        isLogout = true;
    }
}

