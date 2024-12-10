package mediator;

import handler.CartHandler;
import handler.ComputerHandler;
import handler.UserHandler;
import service.CartService;
import service.ComputerService;
import service.RoleService;
import service.UserService;
import store.Store;
import util.Utility;

import java.util.Scanner;

public interface IMediator {
    void setStore(Store store);
    void notify(Object sender, String event);

    Utility getUtility();
    Scanner getScanner();

    UserHandler getUserHandler();
    ComputerHandler getComputerHandler();
    CartHandler getCartHandler();

    CartService getCartService();
    UserService getUserService();
    ComputerService getComputerService();
    RoleService getRoleService();
}
