package handler;

import service.UserService;
import util.Utility;

import java.util.List;
import java.util.Scanner;

import model.CartItem;
import model.Computer;

import service.CartService;

import mediator.IMediator;

public class CartHandler {
    private final Utility utility;
    private final Scanner scanner;

    private final UserHandler userHandler;
    private final ComputerHandler computerHandler;

    private final UserService userService;
    private final CartService cartService;

    private final IMediator mediator;

    public CartHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.userHandler = mediator.getUserHandler();
        this.computerHandler = mediator.getComputerHandler();
        this.userService = mediator.getUserService();
        this.cartService = mediator.getCartService();
    }

    public void handleCreateCart() {
        String userId = userHandler.currentUserID;

        if (!cartService.userHasCart(userId)) {
            cartService.createCartForUser(userId);
            System.out.println("A new cart has been created for the user.");
        }
    }

    public void handleAddCartItem() {
        computerHandler.handleComputerPrint();

        Computer selectedComputer = mediator.getComputerHandler().handleComputerSelection();

        System.out.println("Do you want to add this computer to your cart? [Yes / No]");

        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("Yes")) {
            String userId = mediator.getUserHandler().currentUserID;
            int cartId = cartService.getCartIdByUserId(userId);

            String computerId = selectedComputer.getId();
            String computerName = selectedComputer.getName();
            double computerPrice = selectedComputer.getPrice();

            cartService.addToCart(new CartItem(cartId, computerId, computerName, computerPrice));

            System.out.println("You have successfully added the selected computer to your cart!");

        } else if (command.equalsIgnoreCase("No")) {
            utility.returnToInterface();
        }
    }

    public void handlePrintCart() {
        String userId = userHandler.currentUserID;
        int cartId = cartService.getCartIdByUserId(userId);

        int balance = userService.getBalanceById(userId);
        int totalPrice = 0;

        List<Computer> computers = cartService.getComputersByCartId(cartId);

        for (Computer computer : computers) {
            totalPrice += computer.getPrice();
        }

        System.out.println("Currently you have " + computers.size() + " computers in your cart.");
        System.out.println("Your current balance is: " + balance + "$.");
        System.out.println("Total price of your cart is: " + totalPrice + "$.");
        System.out.println();
        System.out.println("Here are the computers in your cart: ");
        System.out.println();

        for (Computer computer : computers) {
            String borderTop = "╭──────────────────────────────────────────────────────────────────────────────────╮";
            String borderBottom = "╰──────────────────────────────────────────────────────────────────────────────────╯";

            System.out.println(borderTop);
            System.out.println("  Computer name: " + computer.getName() + ", " +
                    "RAM: " + computer.getRam() + "GB" + ", " +
                    "Processor: " + computer.getProcessor() + ", " +
                    "Price: " + computer.getPrice() + "$");
            System.out.println(borderBottom);
        }
    }

}