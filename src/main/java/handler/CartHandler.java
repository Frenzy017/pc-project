package handler;

import service.ComputerService;
import service.UserService;
import service.component.ProcessorService;
import service.component.RamService;
import service.component.VideoService;
import util.Utility;

import java.util.List;
import java.util.Map;
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
    private final ComputerService computerService;

    private final ProcessorService processorService;
    private final RamService ramService;
    private final VideoService videoService;

    private final IMediator mediator;

    int balance = 0;
    int totalPrice = 0;

    public CartHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.userHandler = mediator.getUserHandler();
        this.computerHandler = mediator.getComputerHandler();
        this.userService = mediator.getUserService();
        this.cartService = mediator.getCartService();
        this.computerService = mediator.getComputerService();
        this.processorService = mediator.getProcessorService();
        this.ramService = mediator.getRamService();
        this.videoService = mediator.getVideoService();
    }

    private int getUserId() {
        return userHandler.currentUserID;
    }

    private int getCartIdForCurrentUser() {
        int userId = getUserId();
        return cartService.getCartIdByUserId(userId);
    }

    public void handleCart() {
        utility.printCartInterface();

        handleCreateCart();

        while (true) {
            String cartCommand = scanner.nextLine();

            switch (cartCommand.toLowerCase()) {
                case "back" -> {
                    utility.returnToInterface();
                    return;
                }
                case "show" -> {
                    handlePrintCart();
                    handlePurchaseIfNotEmpty();
                }
                case "clear" -> handleClearCart();
                case "deposit" -> handleDepositMoney();
                default -> utility.printCartInterface();
            }
        }
    }

    private void handleCreateCart() {
        int userId = getUserId();

        if (!cartService.userHasCart(userId)) {
            cartService.createCartForUser(userId);
        }
    }

    public void handleAddCartItem() {
        handleCreateCart();

        Computer selectedComputer = computerHandler.getSelectedComputer();

        System.out.print("Do you want to add this computer to your cart? [Yes / No] ");

        scanner.nextLine();

        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("Yes")) {
            int cartId = getCartIdForCurrentUser();
            int computerId = selectedComputer.getId();

            double computerPrice = selectedComputer.getTotalPrice();

            cartService.addToCart(new CartItem(cartId, computerId, computerPrice));

            System.out.println();
            System.out.println("You have successfully added the selected computer to your cart!");

            utility.returnToInterface();

        } else if (command.equalsIgnoreCase("No")) {
            utility.returnToInterface();
        }
    }

    private void handleClearCart() {
        int cartId = getCartIdForCurrentUser();
        cartService.deleteComputersByCartId(cartId);
        System.out.println("Cart has been cleared.");
        utility.returnToCartInterface();
    }

    private void handleDepositMoney() {
        System.out.print("Enter the amount you want to deposit: ");
        int depositAmount = scanner.nextInt();

        if (depositAmount > 0) {
            int userId = getUserId();
            userService.updateUserBalance(userId, depositAmount);
            System.out.println("Successfully deposited " + depositAmount + "$ to your account.");
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive number.");
        }
    }

    private void handlePrintCart() {
        int userId = getUserId();
        int cartId = getCartIdForCurrentUser();

        balance = userService.getBalanceById(userId);

        List<Computer> computers = cartService.getComputersByCartId(cartId);

        totalPrice = computers.stream().mapToInt(Computer::getTotalPrice).sum();

        System.out.println("Currently you have " + computers.size() + " computers in your cart.");
        System.out.println("Your current balance is: " + balance + "$.");
        System.out.println("Total price of your cart is: " + totalPrice + "$.");
        System.out.println();

        if (computers.isEmpty()) {
            System.out.println("There are no computers in your cart, please select one first.");
            utility.returnToCartInterface();
        } else {
            System.out.println("Here are the computers in your cart: ");
            System.out.println();

            for (Computer computer : computers) {
                Map<String, Object> componentDetails = computerHandler.getComponentDetails(computer);

                System.out.println(String.format(
                        "Computer name: %s, Processor: %s, RAM: %dGB, Video card: %s, Price: %d$",
                        computer.getName(), componentDetails.get("processorName"), componentDetails.get("ramCapacity"),
                        componentDetails.get("videoCardName"), computer.getTotalPrice()));
            }
        }
    }

    private void handlePurchaseCart() {
        int userId = getUserId();
        int cartId = getCartIdForCurrentUser();

        List<Computer> computers = cartService.getComputersByCartId(cartId);

        System.out.println();

        System.out.print("Do you want to purchase the computers you have selected in your cart?  [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("Yes")) {

            int deficit = totalPrice - balance;

            if (balance < totalPrice) {
                System.out.println("Insufficient balance. You're missing: " + deficit + "$" + " to purchase your products.");
                System.out.println("Please deposit more money to proceed.");
                utility.returnToCartInterface();
            }
            for (Computer computer : computers) {
                int processorId = computer.getProcessor_id();
                int ramId = computer.getRam_id();
                int videoCardId = computer.getVideoCard_id();

                processorService.decreaseProcessorQuantityByOne(processorId);
                ramService.decreaseRamQuantityByOne(ramId);
                videoService.decreaseVideoCardQuantityByOne(videoCardId);

                if (computer.getName().equals("customComputer")) {
                    computerService.deleteComputerInDatabase(computer.getName());
                }
            }
            userService.updateUserBalanceDeduct(userId, totalPrice);
            cartService.deleteComputersByCartId(cartId);
            System.out.println("Purchase successful! You have bought " + computers.size() + " computers.");
        }
        utility.returnToCartInterface();
    }

    private void handlePurchaseIfNotEmpty() {
        if (!cartService.getComputersByCartId(getCartIdForCurrentUser()).isEmpty()) {
            handlePurchaseCart();
        }
    }
}