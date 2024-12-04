package handler;

import java.util.*;
import util.Utility;

import model.Computer;
import service.ComputerService;

import mediator.IMediator;

public class ComputerHandler {
    private final Utility utility;
    private final Scanner scanner;

    private final ComputerService computerService;

    private final IMediator mediator;

    public ComputerHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.computerService = mediator.getComputerService();
    }

    public void handleComputerModificationProperties() {
        List<Computer> computers = computerService.getAllComputerProperties();
        Map<Integer, Runnable> commandMap = new HashMap<>();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
            return;
        }

        System.out.print("Please enter the name of the computer you want to modify: ");
        String selectedName = scanner.nextLine();

        Computer selectedComputer = computers
                .stream()
                .filter(computer -> computer.getName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        if (selectedComputer == null) {
            System.out.println("Computer not found. Please try again.");
            return;
        }
        utility.printModificationOptions();

        int modifyCommand = scanner.nextInt();
        scanner.nextLine();

        switch (modifyCommand) {
            case 1 -> {
                System.out.print("Please type the new name for your PC: ");
                selectedComputer.setName(scanner.nextLine());
            }
            case 2 -> {
                System.out.print("Please type the new model for your graphic card: ");
                selectedComputer.setGraphicCard(scanner.nextLine());
            }
            case 3 -> {
                System.out.print("Please type the new size for your RAM: ");
                selectedComputer.setRam(scanner.nextInt());
                scanner.nextLine();
            }
            case 4 -> {
                System.out.print("Please type the new model for your processor: ");
                selectedComputer.setProcessor(scanner.nextLine());
            }
            case 5 -> {
                System.out.print("Please type the new price for your PC: ");
                selectedComputer.setPrice(scanner.nextInt());
                scanner.nextLine();
            }
            default -> {
                System.out.println("Invalid command. Please try again.");
                return;
            }
        }

        computerService.updateComputerInDatabase(selectedComputer);
        System.out.println("Computer updated successfully!");
        utility.printAdminComputerOptions();
    }

    public void handleComputerCreation() {
        List<Computer> computers = computerService.getAllComputerProperties();

        String uniqueID = UUID.randomUUID().toString();

        System.out.print("Enter computer name: ");
        String name = scanner.nextLine();

        boolean nameExists = computers.stream().anyMatch(computer -> computer.getName().equalsIgnoreCase(name));

        if (nameExists) {
            System.out.println("A computer with this name already exists. Please choose a different name.");
            return;
        }

        System.out.print("Enter graphic card: ");
        String graphicCard = scanner.nextLine();

        System.out.print("Enter RAM size (GB): ");
        int ram = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter processor: ");
        String processor = scanner.nextLine();

        System.out.print("Enter price: ");
        int price = scanner.nextInt();

        Computer newComputer = new Computer(uniqueID, name, graphicCard, ram, processor, price);

        computerService.insertComputerIntoDatabase(newComputer);

        System.out.println();
        System.out.println("New computer created successfully!");
        utility.printAdminComputerOptions();
    }

    public void handleComputerDeletion() {
        System.out.println();
        System.out.println("Please type the name of the computer you want to delete: ");

        String selectedComputerName = scanner.nextLine();

        computerService.deleteComputerInDatabase(selectedComputerName);

        System.out.println();
        System.out.println("You have successfully deleted the computer!");

        utility.printAdminComputerOptions();
    }

    public void handleComputerPrint() {
        List<Computer> computers = computerService.getAllComputerProperties();

        computers.sort(Comparator.comparing(Computer::getPrice).reversed());

        System.out.println("Available Computers in price ascending order:");

        for (Computer computer : computers) {
            System.out.println("Name: "
                    + computer.getName()
                    + ", Graphic Card: " + computer.getGraphicCard()
                    + ", RAM: " + computer.getRam()
                    + "GB, Processor: " + computer.getProcessor()
                    + ", Price: " + computer.getPrice() + "$");
        }
    }

    public void handleComputerOptions() {
        System.out.print("Do you want to see additional options for the computer object?  [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("yes")) {
            System.out.println();
            utility.printAdminComputerOptions();
            String chosenMethod = scanner.nextLine();

            switch (chosenMethod) {
                case "add" -> handleComputerSelection();
                case "new" -> handleComputerCreation();
                case "modify" -> handleComputerModificationProperties();
                case "delete" -> handleComputerDeletion();
                case "back" -> utility.returnToAdminInterface();
                default -> {
                    System.out.println("No current method available, please try again!");
                    utility.returnToAdminComputerOptions();
                }
            }
        } else if (command.equalsIgnoreCase("no")) {
            utility.returnToAdminInterface();
        }
    }

    public Computer handleComputerSelection() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
        }


        System.out.print("Please select a computer by its name: ");

        String selectedName = scanner.nextLine();

        Computer selectedComputer = computers.stream()
                .filter(computer -> computer.getName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        System.out.println("You have selected: " + selectedComputer);

        if (selectedComputer == null) {
            System.out.println("Computer not found. Please try again.");
        }
        return selectedComputer;
    }
}
