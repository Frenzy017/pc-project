package store;

import java.io.*;
import java.util.*;

import handler.CartHandler;
import util.Utility;

import handler.ComputerHandler;
import handler.UserHandler;

import model.Computer;

import service.ComputerService;
import service.UserService;

import mediator.IMediator;

public class Store {
    private Properties config;

    private final Utility utility;
    private final Scanner scanner;

    private final UserHandler userHandler;
    private final ComputerHandler computerHandler;
    private final CartHandler cartHandler;

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
        this.cartHandler = mediator.getCartHandler();
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
        cartHandler.handleAddCartItem();
    }

    public void handleComputerSelectionAsAdmin() {
        computerHandler.handleComputerPrint();
        computerHandler.handleComputerOptions();
    }

    public void printInterfaceBasedOnUserRole() {
        String role = userService.getUserRoleById(userHandler.currentUserID);

        if (role.equals("admin")) {
            utility.printAdminStoreInterface();
        } else {
            utility.printStoreInterface();
        }
    }

    public void logout() {
        System.out.println("You have successfully logged out!");
        System.out.println();
        isLogout = true;
    }

    private void loadConfig() {
        config = new Properties();
        File configFile = new File("src/main/resources/config.properties");

        if (!configFile.exists()) {
            try (OutputStream output = new FileOutputStream(configFile)) {
                config.setProperty("isComputerTableInitialized", "false");
                config.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(configFile)) {
            config.load(input);
            isComputerTableInitialized = Boolean.parseBoolean(config.getProperty("isComputerTableInitialized", "false"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveConfig() {
        try (OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {
            config.setProperty("isComputerTableInitialized", String.valueOf(isComputerTableInitialized));
            config.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void initializeComputerTable() {
        if (!isComputerTableInitialized) {
            computerService.addComputersToDatabase(computers);
            isComputerTableInitialized = true;
            saveConfig();
        }
    }

}


