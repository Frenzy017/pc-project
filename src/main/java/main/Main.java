package main;

import mediator.IMediator;

import mediator.Mediator;
import store.Store;

public class Main {
    public static void main(String[] args) {
        IMediator mediator = new Mediator();

        Store store = new Store(mediator);

        mediator.setStore(store);

        mediator.getUtility().printWelcomeMessage();
        store.start();
    }
}