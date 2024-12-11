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

public interface IMediator {
    void setStore(Store store);
    void notify(Object sender, String event);

    Utility getUtility();
    Scanner getScanner();

    UserHandler getUserHandler();
    ComputerHandler getComputerHandler();
    CartHandler getCartHandler();

    ProcessorHandler getProcessorHandler();
    RamHandler getRamHandler();
    VideoCardHandler getVideoCardHandler();

    CartService getCartService();
    UserService getUserService();
    ComputerService getComputerService();
    RoleService getRoleService();

    ProcessorService getProcessorService();
    RamService getRamService();
    VideoService getVideoService();
}
