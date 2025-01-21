package mediator;

import handler.CartHandler;
import handler.ComputerHandler;
import handler.UserHandler;
import handler.component.ProcessorHandler;
import handler.component.RamHandler;
import handler.component.VideoCardHandler;
import service.CartService;
import service.ComputerService;
import service.RoleService;
import service.UserService;
import service.component.ProcessorService;
import service.component.RamService;
import service.component.VideoService;
import store.Store;
import util.Utility;

import java.util.Scanner;

public class Mediator implements IMediator {
    private final Utility utility = new Utility(this);
    private static final Scanner scanner = new Scanner(System.in);

    private Store store;

    private UserHandler userHandler;
    private ComputerHandler computerHandler;
    private CartHandler cartHandler;

    private ProcessorHandler processorHandler;
    private RamHandler ramHandler;
    private VideoCardHandler videoCardHandler;

    private static final UserService userService = new UserService();
    private static final ComputerService computerService = new ComputerService();
    private static final CartService cartService = new CartService();
    private static final RoleService roleService = new RoleService();

    private static final ProcessorService processorService = new ProcessorService();
    private static final RamService ramService = new RamService();
    private static final VideoService videoService = new VideoService();

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
            case "cart" -> cartHandler.handleCart();
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

    @Override
    public ProcessorHandler getProcessorHandler() {
        if (processorHandler == null) {
            processorHandler = new ProcessorHandler(this);
        }
        return processorHandler;
    }

    @Override
    public RamHandler getRamHandler() {
        if (ramHandler == null) {
            ramHandler = new RamHandler(this);
        }
        return ramHandler;
    }

    @Override
    public VideoCardHandler getVideoCardHandler() {
        if (videoCardHandler == null) {
            videoCardHandler = new VideoCardHandler(this);
        }
        return videoCardHandler;
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

    public RoleService getRoleService() {
        return roleService;
    }

    public ProcessorService getProcessorService() {
        return processorService;
    }

    public RamService getRamService() {
        return ramService;
    }

    public VideoService getVideoService() {
        return videoService;
    }
}