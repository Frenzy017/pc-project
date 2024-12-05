package handler;

import util.Utility;
import java.util.Scanner;
import java.util.UUID;
import java.util.Comparator;
import java.util.List;

import mediator.IMediator;

import model.User;

import service.UserService;

public class UserHandler {
    private final Utility utility;
    private final Scanner scanner;

    private final UserService userService;

    private final IMediator mediator;

    public String currentUserID;

    public UserHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.userService = mediator.getUserService();
    }

    public void handleCreateUser() {
        String username = utility.setUsername();
        String password = utility.setPassword();
        String uniqueID = UUID.randomUUID().toString();

        User newUser = new User(uniqueID, username, password, 0, "user");

        userService.addUserToDatabase(newUser);

        mediator.notify(this, "start");
    }

    public void handleLoginUser() {
        System.out.println("Login has been initiated");
        System.out.println();

        if (!userService.areUsersPresent()) {
            System.out.println("There are no current users registered, please register first");
            System.out.println();
            mediator.notify(this, "start");
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

            mediator.notify(this, "start");
        }
    }

    public void handleDeleteUser() {
        System.out.print("To delete a user, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            handleDeleteUser();
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
            handleDeleteUser();
        }
        utility.printAdminStoreInterface();
    }

    public void handleUpdatePassword() {
        System.out.print("To update someone's password, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            handleUpdatePassword();
        }

        System.out.println("You have selected the current account name: " + selectedUsername);
        System.out.println();

        System.out.print("Please type a new password for the user: ");
        String newPassword = scanner.nextLine();

        userService.updateUserPassword(newPassword, selectedUsername);

        System.out.println("You have successfully updated the user's password!");
        utility.printAdminStoreInterface();
    }

    public void handleUpdateUsername() {
        System.out.print("To update someone's username, please type his / her name: ");
        String selectedUsername = scanner.nextLine();

        if (!userService.isUsernamePresent(selectedUsername)) {
            System.out.println("The username you entered does not exist. Please try again.");
            handleUpdatePassword();
        }

        System.out.println("You have selected the current account name: " + selectedUsername);
        System.out.println();

        System.out.print("Please type a new username for the user: ");
        String newUsername = scanner.nextLine();

        userService.updateUserUsername(newUsername, selectedUsername);

        System.out.println("You have successfully updated the user's username!");
        utility.printAdminStoreInterface();
    }

    public void handleViewAllUsers() {
        List<User> users = userService.getAllUserProperties();

        users.sort(Comparator.comparing((User user) -> !user.getRole().equals("admin")).thenComparing(User::getUsername));

        System.out.println("Registered Users:");

        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", Balance: " + user.getBalance() + ", Role: " + user.getRole());
        }
    }

    public void handleViewAllUserOptions() {
        System.out.print("Do you want to modify any user's data?  [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("yes")) {
            System.out.println();
            System.out.println("Here are 4 methods you can choose from:");
            System.out.println("Update user's username --> [username]");
            System.out.println("Update user's password --> [password]");
            System.out.println("Delete a user --> [delete]");
            System.out.println("Abort action --> [back]");
            System.out.println();
            System.out.print("Please choose a method: ");

            String chosenMethod = scanner.nextLine();

            switch (chosenMethod) {
                case "username" -> handleUpdateUsername();
                case "password" -> handleUpdatePassword();
                case "delete" -> handleDeleteUser();
                case "back" -> utility.returnToAdminInterface();
                default -> {
                    System.out.println("No current method available, please try again!");
                    scanner.nextLine();
                    utility.printAdminComputerOptions();
                }
            }
        } else if (command.equalsIgnoreCase("no")) {
            utility.returnToAdminInterface();
        }
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

}