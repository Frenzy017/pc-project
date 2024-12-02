import java.util.Scanner;
import java.util.UUID;

public class UserHandler {
    private final UserService userService = new UserService();
    private final Utility utility = new Utility();
    private final Scanner scanner = new Scanner(System.in);
    private Store store;

    public String currentUserID;

    public UserHandler(Store store) {
        this.store = store;
        this.store.setUserHandler(this);
    }

    public void createUser() {
        String username = utility.setUsername();
        String password = utility.setPassword();
        String uniqueID = UUID.randomUUID().toString();

        User newUser = new User(uniqueID, username, password, 0, "user");

        userService.addUserToDatabase(newUser);

        System.out.println();
        System.out.println("Account created successfully!");
        System.out.println();

    }

    public void loginUser() {
        if (!userService.areUsersPresent()) {
            System.out.println("There are no current users registered, please register first");
            System.out.println();
            store.start();
            return;
        }

        String usernameToLogin = utility.getUsername();
        String passwordToLogin = utility.getPassword();

        boolean validLogin = userService.validateUserCredentials(usernameToLogin, passwordToLogin);

        System.out.println();

        if (validLogin) {
            currentUserID = userService.getUserIdByUsername(usernameToLogin);

            System.out.println("You have successfully logged in!");

            if (userService.getUserRoleById(currentUserID).equals("admin")) {
                utility.printAdminStoreInterface();
            } else {
                utility.printStoreInterface();
            }

        } else {
            System.out.println("Wrong input!");
            System.out.println();
            utility.invalidCommand();
            System.out.println();
            store.start();
        }
    }

    public void deleteUser() {
        System.out.print("To delete a user, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            deleteUser();
            return;
        }

        User userToDelete = userService.getAllUserProperties()
                .stream()
                .filter(user -> user.getUsername().equals(selectedUsername))
                .findFirst()
                .orElse(null);

        if (userToDelete != null) {
            userService.removeUserFromDatabase(userToDelete);
            System.out.println("You have successfully deleted the user: " + selectedUsername);
        } else {
            System.out.println("User not found. Please try again.");
            deleteUser();
        }
        utility.printAdminStoreInterface();
    }

    public void updatePassword() {
        System.out.print("To update someone's password, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            updatePassword();
        }

        System.out.println("You have selected the current account name: " + selectedUsername);
        System.out.println();

        System.out.print("Please type a new password for the user: ");
        String newPassword = scanner.nextLine();

        userService.updateUserPassword(newPassword, selectedUsername);

        System.out.println("You have successfully updated the user's password!");
        utility.printAdminStoreInterface();
    }

    public void updateUsername() {
        System.out.print("To update someone's username, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            updatePassword();
        }

        System.out.println("You have selected the current account name: " + selectedUsername);
        System.out.println();

        System.out.print("Please type a new username for the user: ");
        String newUsername = scanner.nextLine();

        userService.updateUserUsername(newUsername, selectedUsername);

        System.out.println("You have successfully updated the user's username!");
        utility.printAdminStoreInterface();
    }
}