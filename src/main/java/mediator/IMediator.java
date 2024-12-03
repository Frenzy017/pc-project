package mediator;

import handler.UserHandler;
import store.Store;

public interface IMediator {
    void setUserHandler(UserHandler userHandler);

    void setStore(Store store);

    void notify(Object sender, String event);

}
