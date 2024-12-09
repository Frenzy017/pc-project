package store;

import java.util.*;

import handler.CartHandler;
import util.Utility;

import handler.ComputerHandler;
import handler.UserHandler;


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

    private final IMediator mediator;

    private final Map<String, Runnable> commandMap = new HashMap<>();

    private boolean isLogout;

    public Store(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.userHandler = mediator.getUserHandler();
        this.computerHandler = mediator.getComputerHandler();
        this.cartHandler = mediator.getCartHandler();
        this.computerService = mediator.getComputerService();
        this.userService = mediator.getUserService();

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
}


