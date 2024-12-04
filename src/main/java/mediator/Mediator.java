package mediator;

import handler.CartHandler;
import handler.ComputerHandler;
import handler.UserHandler;
import service.CartService;
import service.ComputerService;
import service.UserService;
import store.Store;
import util.Utility;

import java.util.Scanner;

public class Mediator implements IMediator {
    private final Utility utility = new Utility();
    private final Scanner scanner = new Scanner(System.in);

    private Store store;

    private UserHandler userHandler;
    private ComputerHandler computerHandler;
    private CartHandler cartHandler;

    private final UserService userService = new UserService();
    private final ComputerService computerService = new ComputerService();
    private final CartService cartService = new CartService();

    @Override
    public void notify(Object sender, String event) {
       cartHandler = getCartHandler();
       userHandler = getUserHandler();
        switch (event) {
            case "start" -> store.start();
            case "createUser" -> userHandler.handleCreateUser();
            case "login" -> userHandler.handleLoginUser();
            case "showComputers" -> store.showComputers();
            case "viewUsers" -> {
                userHandler.handleViewAllUsers();
                userHandler.handleViewAllUserOptions();
            }
            case "logout" -> store.logout();
            case "cart" -> {
                cartHandler.handlePrintCart();
//                cartHandler.handleCreateCart();
//                cartHandler.handleAddCartItem();
            }
            default -> throw new IllegalArgumentException("Unknown event: " + event);
        }
    }

    @Override
    public void setStore(Store store) {
        this.store = store;
    }

    public Utility getUtility() {
        return utility;
    }

    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public UserHandler getUserHandler() {
        if (userHandler == null) {
            userHandler = new UserHandler(this);
        }
        return userHandler;
    }

    @Override
    public ComputerHandler getComputerHandler() {
        if (computerHandler == null) {
            computerHandler = new ComputerHandler(this);
        }
        return computerHandler;
    }

    public CartHandler getCartHandler() {
        if (cartHandler == null) {
            cartHandler = new CartHandler(this);
        }
        return cartHandler;
    }

    public UserService getUserService() {
        return userService;
    }

    public ComputerService getComputerService() {
        return computerService;
    }

    public CartService getCartService() {
        return cartService;
    }

}