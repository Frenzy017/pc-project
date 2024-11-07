import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        Utility utility = new Utility();

        utility.printWelcomeMessage();
        store.start();
    }
}