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

    public Computer selectedComputer;

    public ComputerHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.computerService = mediator.getComputerService();
    }

    public void handleComputerModificationProperties() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
            return;
        }

        System.out.print("Please enter the name of the computer you want to modify: ");
        String selectedName = scanner.nextLine();

        utility.printModificationOptions();

        Computer selectedComputer = computers
                .stream()
                .filter(computer -> computer.getName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        if (selectedComputer == null) {
            System.out.println("Computer not found. Please try again.");
            return;
        }

        int modifyCommand = scanner.nextInt();
        scanner.nextLine();

        switch (modifyCommand) {
            case 1 -> handleNameModification(selectedComputer);
            case 2 -> handleGraphicCardModification(selectedComputer);
            case 3 -> handleRamModification(selectedComputer);
            case 4 -> handleProcessorModification(selectedComputer);
            case 5 -> handlePriceModification(selectedComputer);
            default -> {
                utility.returnToAdminComputerOptions();
                return;
            }
        }
        computerService.updateComputerInDatabase(selectedComputer);
        System.out.println("Computer updated successfully!");
    }

    private void handleNameModification(Computer selectedComputer) {
        try {
            System.out.print("Please type the new name for your PC: ");
            String newName = scanner.nextLine();

            if (newName.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            } else if (computerService.getAllComputerProperties().stream().anyMatch(computer -> computer.getName().equalsIgnoreCase(newName))) {
                System.out.println("A computer with this name already exists. Please choose a different name.");
            } else {
                selectedComputer.setName(newName);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the name. Please try again.");
            e.printStackTrace();
        }
    }

    private void handleGraphicCardModification(Computer selectedComputer) {
        try {
            System.out.print("Please type the new model for your graphic card: ");
            String newGraphicCard = scanner.nextLine();

            if (newGraphicCard.isEmpty()) {
                System.out.println("Graphic card model cannot be empty. Please try again.");
            } else {
                selectedComputer.setGraphicCard(newGraphicCard);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the graphic card. Please try again.");
            e.printStackTrace();
        }
    }

    private void handleRamModification(Computer selectedComputer) {
        try {
            System.out.print("Please type the new size for your RAM: ");
            String input = scanner.nextLine();

            int newRam = Integer.parseInt(input);
            if (newRam <= 0) {
                System.out.println("RAM size must be a positive number. Please try again.");
            } else {
                selectedComputer.setRam(newRam);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for RAM size. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the RAM. Please try again.");
            e.printStackTrace();
        }
    }

    private void handleProcessorModification(Computer selectedComputer) {
        try {
            System.out.print("Please type the new model for your processor: ");
            String newProcessor = scanner.nextLine();

            if (newProcessor.isEmpty()) {
                System.out.println("Processor model cannot be empty. Please try again.");
            } else {
                selectedComputer.setProcessor(newProcessor);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the processor. Please try again.");
            e.printStackTrace();
        }
    }

    private void handlePriceModification(Computer selectedComputer) {
        try {
            System.out.print("Please type the new price for your computer: ");
            String input = scanner.nextLine();

            int newPrice = Integer.parseInt(input);
            if (newPrice <= 0) {
                System.out.println("Price must be a positive number. Please try again.");
            } else {
                selectedComputer.setPrice(newPrice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for price. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the price. Please try again.");
            e.printStackTrace();
        }
    }

    public void handleComputerCreation() {
        List<Computer> computers = computerService.getAllComputerProperties();

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

        Computer newComputer = new Computer(0, name, graphicCard, ram, processor, price);

        computerService.insertComputerIntoDatabase(newComputer);

        System.out.println();
        System.out.println("New computer created successfully!");
    }

    public void handleComputerDeletion() {
        System.out.println();
        System.out.println("Please type the name of the computer you want to delete: ");

        String selectedComputerName = scanner.nextLine();

        computerService.deleteComputerInDatabase(selectedComputerName);

        System.out.println();
        System.out.println("You have successfully deleted the computer!");
    }

    public void handleComputerPrint() {
        List<Computer> computers = computerService.getAllComputerProperties();

        computers.sort(Comparator.comparing(Computer::getPrice).reversed());

        System.out.println("Available Computers in price ascending order:");
        System.out.println();

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
        System.out.println();
        System.out.print("Do you want to see additional options for the computer object?  [Yes / No] ");
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("yes")) {
            System.out.println();
            utility.printAdminComputerOptions();

            String chosenMethod = scanner.nextLine();

            switch (chosenMethod) {
                case "add" -> {
                    handleComputerSelection();
                    mediator.getCartHandler().handleAddCartItem();
                }
                case "new" -> handleComputerCreation();
                case "modify" -> handleComputerModificationProperties();
                case "delete" -> handleComputerDeletion();
                case "back" -> {
                    utility.returnToAdminInterface();
                }
                default -> {
                    System.out.println("No current method available, please try again!");
                    utility.returnToAdminComputerOptions();
                }
            }
        } else if (command.equalsIgnoreCase("no")) {
            utility.returnToAdminInterface();
        }
    }

    public void handleComputerSelection() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
        }

        System.out.print("Please select a computer by its name: ");

        String selectedName = scanner.nextLine();

        Computer computerName = computers
                .stream()
                .filter(computer -> computer.getName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        System.out.println();
        System.out.println("You have selected: " + computerName);
        System.out.println();

        selectedComputer = computerName;

        if (computerName == null) {
            System.out.println("Computer not found. Please try again.");
        }

    }

    public Computer getSelectedComputer() {
        return selectedComputer;
    }
}
