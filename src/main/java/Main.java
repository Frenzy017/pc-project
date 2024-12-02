public class Main {
    public static void main(String[] args) {
        Utility utility = new Utility();
        Store store = new Store();

        UserHandler userHandler = new UserHandler(store);

        utility.printWelcomeMessage();
        store.start();
    }
}