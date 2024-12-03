package mediator;

import handler.UserHandler;
import store.Store;

public class Mediator implements IMediator {
    private UserHandler userHandler;
    private Store store;

    @Override
    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Override
    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public void notify(Object sender, String event) {
        if (sender instanceof UserHandler) {
            if (event.equals("login")) {
                handleLoginUser();
            }
        } else if (sender instanceof Store) {
            if (event.equals("start")) {
                handleStoreStart();
            }
        }
    }

    private void handleLoginUser() {
        userHandler.handleLoginUser();
    }

    private void handleStoreStart() {
        store.handleStoreStart();
    }
}