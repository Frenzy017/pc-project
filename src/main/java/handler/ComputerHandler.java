package handler;

import java.util.*;

import handler.component.ProcessorHandler;
import handler.component.RamHandler;
import handler.component.VideoCardHandler;
import model.component.Processor;
import model.component.Ram;
import model.component.VideoCard;
import service.component.ProcessorService;
import service.component.RamService;
import service.component.VideoService;
import util.Utility;

import model.Computer;
import service.ComputerService;

import mediator.IMediator;

public class ComputerHandler {
    private final Utility utility;
    private final Scanner scanner;

    private final ComputerService computerService;
    private final ProcessorService processorService;
    private final RamService ramService;
    private final VideoService videoService;

    private final ProcessorHandler processorHandler;
    private final RamHandler ramHandler;
    private final VideoCardHandler videoCardHandler;

    private final IMediator mediator;

    private Computer selectedComputer;

    private String configuredComputerPrint;

    private boolean isConfigurationComplete;

    private boolean isProcessorConfigured;
    private boolean isRamConfigured;
    private boolean isVideoCardConfigured;

    public ComputerHandler(IMediator mediator) {
        this.mediator = mediator;
        this.utility = mediator.getUtility();
        this.scanner = mediator.getScanner();
        this.computerService = mediator.getComputerService();
        this.processorService = mediator.getProcessorService();
        this.ramService = mediator.getRamService();
        this.videoService = mediator.getVideoService();
        this.processorHandler = mediator.getProcessorHandler();
        this.ramHandler = mediator.getRamHandler();
        this.videoCardHandler = mediator.getVideoCardHandler();
    }

    public void handleComputerPrint() {
        List<Computer> computers = computerService.getAllComputerProperties();

        computers.sort(Comparator.comparing(Computer::getTotalPrice).reversed());

        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║        Available Computer Configurations        ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
        System.out.println();

        int index = 1;
        for (Computer computer : computers) {
            System.out.println(index++ + ". " + handleComputerDetails(computer));
        }
    }

    public void handleComputerSelection() {
        System.out.print("Do you want to create your own custom computer configuration? [Yes/No] ");
        String command = scanner.nextLine();
        System.out.println();

        if (command.equalsIgnoreCase("Yes")) {
            handleComputerCustomConfiguration();
        } else if (command.equalsIgnoreCase("No")) {
            handleComputerRegularSelection();
        }
    }

    public void handleComputerCustomConfiguration() {
        while (!isConfigurationComplete) {
            System.out.println("╭──────────────────────────────────────────╮");
            System.out.println("│  Please choose a configuration option    │");
            System.out.println("│  by typing the corresponding number:     │");
            System.out.println("╰──────────────────────────────────────────╯");
            System.out.println("1. Processor Configuration " + (isProcessorConfigured ? "✔" : ""));
            System.out.println("2. RAM Configuration " + (isRamConfigured ? "✔" : ""));
            System.out.println("3. Video Card Configuration " + (isVideoCardConfigured ? "✔" : ""));
            System.out.println("4. Check your computer build status");
            System.out.println();
            System.out.print("Configuration option: ");

            int configCommand = scanner.nextInt();

            switch (configCommand) {
                case 1 -> {
                    processorHandler.handleProcessorConfig();
                    isProcessorConfigured = true;
                }
                case 2 -> {
                    ramHandler.handleRamConfig();
                    isRamConfigured = true;
                }
                case 3 -> {
                    videoCardHandler.handleVideoCardConfig();
                    isVideoCardConfigured = true;
                }
                case 4 -> {
                    handleComputerBuildConfiguration();
                    isConfigurationComplete = true;
                }
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    public Map<String, Object> getComponentDetails(Computer computer) {
        int computerProcessorId = computer.getProcessor_id();
        int computerRamId = computer.getRam_id();
        int computerVideoCardId = computer.getVideoCard_id();

        String processorName = processorService.getProcessorNameById(computerProcessorId);
        int ramCapacity = ramService.getRamCapacityById(computerRamId);
        String videoCardName = videoService.getVideoCardNameById(computerVideoCardId);

        int processorPrice = processorService.getProcessorPriceById(computerProcessorId);
        int ramPrice = ramService.getRamPriceById(computerRamId);
        int videoCardPrice = videoService.getVideoCardPriceById(computerVideoCardId);

        Map<String, Object> componentDetails = new HashMap<>();
        componentDetails.put("processorName", processorName);
        componentDetails.put("ramCapacity", ramCapacity);
        componentDetails.put("videoCardName", videoCardName);
        componentDetails.put("processorPrice", processorPrice);
        componentDetails.put("ramPrice", ramPrice);
        componentDetails.put("videoCardPrice", videoCardPrice);

        return componentDetails;
    }

    private String handleComputerDetails(Computer computer) {
        String computerName = computer.getName();

        Map<String, Object> componentDetails = getComponentDetails(computer);

        String processorName = (String) componentDetails.get("processorName");
        int ramCapacity = (int) componentDetails.get("ramCapacity");
        String videoCardName = (String) componentDetails.get("videoCardName");

        int processorPrice = (int) componentDetails.get("processorPrice");
        int ramPrice = (int) componentDetails.get("ramPrice");
        int videoCardPrice = (int) componentDetails.get("videoCardPrice");

        int buildPrice = processorPrice + ramPrice + videoCardPrice;

        computerService.updateComputerPriceInDatabase(computer, buildPrice);

        return new StringBuilder()
                .append("Name: ").append(computerName)
                .append(", Processor: ").append(processorName)
                .append(", RAM: ").append(ramCapacity).append("GB")
                .append(", Video card: ").append(videoCardName)
                .append(", Price: ").append(buildPrice).append("$")
                .toString();
    }

    private static Map<String, Object[]> handleComponentDetailsObject(Processor processorObject, Ram ramObject, VideoCard videoCardObject) {
        Object[] processorDetails = new Object[]{
                processorObject,
                processorObject != null ? processorObject.getName() : "null",
                processorObject != null ? processorObject.getPrice() : 0
        };

        Object[] ramDetails = new Object[]{
                ramObject,
                ramObject != null ? ramObject.getCapacity() + "GB" : "null",
                ramObject != null ? ramObject.getPrice() : 0
        };

        Object[] videoCardDetails = new Object[]{
                videoCardObject,
                videoCardObject != null ? videoCardObject.getName() : "null",
                videoCardObject != null ? videoCardObject.getPrice() : 0
        };

        Map<String, Object[]> components = Map.of(
                "Processor", processorDetails,
                "RAM", ramDetails,
                "Video card", videoCardDetails
        );
        return components;
    }

    public void handleComputerBuildConfiguration() {
        Processor processorObject = processorHandler.getProcessorObject();
        Ram ramObject = ramHandler.getRamObject();
        VideoCard videoCardObject = videoCardHandler.getVideoCardObject();

        final var components = handleComponentDetailsObject(processorObject, ramObject, videoCardObject);

        List<String> configuredComponents = new ArrayList<>();
        List<String> notConfiguredComponents = new ArrayList<>();

        int totalPrice = 0;

        for (Map.Entry<String, Object[]> entry : components.entrySet()) {
            Object[] component = entry.getValue();
            String nameOrCapacity = Objects.toString(component[1], "");

            if (component[0] != null) {
                configuredComponents.add(entry.getKey() + ": " + nameOrCapacity + ", Price: " + component[2] + "$");
                totalPrice += (int) component[2];
            } else {
                notConfiguredComponents.add(entry.getKey() + ": not configured yet, please configure it to proceed.");
            }
        }

        if (notConfiguredComponents.isEmpty()) {
            selectedComputer = new Computer(0, "customComputer", processorObject.getId(), ramObject.getId(), videoCardObject.getId(), totalPrice);
            configuredComputerPrint = "CPU: " + processorObject.getName() + ", " + "RAM: " + ramObject.getCapacity() + "GB, " + "GPU: " + videoCardObject.getName() + ", " + "Price: " + totalPrice + "$";

            int computerId = computerService.insertComputerIntoDatabase(selectedComputer);
            selectedComputer.setId(computerId);
        } else {
            configuredComponents.forEach(System.out::println);
            notConfiguredComponents.forEach(System.out::println);
        }

        if (isProcessorConfigured && isRamConfigured && isVideoCardConfigured) {
            System.out.println("Here is your newly configured computer: ");
            System.out.println(configuredComputerPrint);
            System.out.println();
        }
    }

    public void handleComputerRegularSelection() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
        }

        System.out.print("Proceeding with regular selection, please select a computer by typing the number it is associated with: ");

        int selectedComputerIndex = scanner.nextInt();

        if (selectedComputerIndex >= 0 && selectedComputerIndex < computers.size()) {
            Computer computerObject = computers.get(selectedComputerIndex);
            Map<String, Object> componentDetails = getComponentDetails(computerObject);

            System.out.println();
            System.out.println(String.format(
                    "You have selected: Computer name: %s, Processor: %s, RAM: %sGB, Video card: %s, Price: %d$",
                    computerObject.getName(), componentDetails.get("processorName"), componentDetails.get("ramCapacity"),
                    componentDetails.get("videoCardName"), computerObject.getTotalPrice())
            );

            selectedComputer = computerObject;
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }

    public Computer getSelectedComputer() {
        return selectedComputer;
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

//        switch (modifyCommand) {
//            case 1 -> handleNameModification(selectedComputer);
//            case 2 -> handleGraphicCardModification(selectedComputer);
//            case 3 -> handleRamModification(selectedComputer);
//            case 4 -> handleProcessorModification(selectedComputer);
//            case 5 -> handlePriceModification(selectedComputer);
//            default -> {
//                utility.returnToAdminComputerOptions();
//                return;
//            }
//        }
//        computerService.updateComputerInDatabase(selectedComputer);
//        System.out.println("Computer updated successfully!");
    }


//    private void handleNameModification(Computer selectedComputer) {
//        try {
//            System.out.print("Please type the new name for your PC: ");
//            String newName = scanner.nextLine();
//
//            if (newName.isEmpty()) {
//                System.out.println("Name cannot be empty. Please try again.");
//            } else if (computerService.getAllComputerProperties().stream().anyMatch(computer -> computer.getName().equalsIgnoreCase(newName))) {
//                System.out.println("A computer with this name already exists. Please choose a different name.");
//            } else {
//                selectedComputer.setName(newName);
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while modifying the name. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    private void handleGraphicCardModification(Computer selectedComputer) {
//        try {
//            System.out.print("Please type the new model for your graphic card: ");
//            String newGraphicCard = scanner.nextLine();
//
//            if (newGraphicCard.isEmpty()) {
//                System.out.println("Graphic card model cannot be empty. Please try again.");
//            } else {
//                selectedComputer.setVideCard_id(newGraphicCard);
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while modifying the graphic card. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    private void handleRamModification(Computer selectedComputer) {
//        try {
//            System.out.print("Please type the new size for your RAM: ");
//            String input = scanner.nextLine();
//
//            int newRam = Integer.parseInt(input);
//            if (newRam <= 0) {
//                System.out.println("RAM size must be a positive number. Please try again.");
//            } else {
//                selectedComputer.setRam_id(newRam);
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input for RAM size. Please enter a valid number.");
//        } catch (Exception e) {
//            System.out.println("An error occurred while modifying the RAM. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    private void handleProcessorModification(Computer selectedComputer) {
//        try {
//            System.out.print("Please type the new model for your processor: ");
//            String newProcessor = scanner.nextLine();
//
//            if (newProcessor.isEmpty()) {
//                System.out.println("Processor model cannot be empty. Please try again.");
//            } else {
//                selectedComputer.setProcessor_id(newProcessor);
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while modifying the processor. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    private void handlePriceModification(Computer selectedComputer) {
//        try {
//            System.out.print("Please type the new price for your computer: ");
//            String input = scanner.nextLine();
//
//            int newPrice = Integer.parseInt(input);
//            if (newPrice <= 0) {
//                System.out.println("Price must be a positive number. Please try again.");
//            } else {
//                selectedComputer.setTotalPrice(newPrice);
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input for price. Please enter a valid number.");
//        } catch (Exception e) {
//            System.out.println("An error occurred while modifying the price. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    public void handleComputerCreation() {
//        List<Computer> computers = computerService.getAllComputerProperties();
//
//        System.out.print("Enter computer name: ");
//        String name = scanner.nextLine();
//
//        boolean nameExists = computers.stream().anyMatch(computer -> computer.getName().equalsIgnoreCase(name));
//
//        if (nameExists) {
//            System.out.println("A computer with this name already exists. Please choose a different name.");
//            return;
//        }
//
//        System.out.print("Enter graphic card: ");
//        String graphicCard = scanner.nextLine();
//
//        System.out.print("Enter RAM size (GB): ");
//        int ram = Integer.parseInt(scanner.nextLine());
//
//        System.out.print("Enter processor: ");
//        String processor = scanner.nextLine();
//
//        System.out.print("Enter price: ");
//        int price = scanner.nextInt();
//
//        Computer newComputer = new Computer(0, name, graphicCard, ram, processor, price);
//
//        computerService.insertComputerIntoDatabase(newComputer);
//
//        System.out.println();
//        System.out.println("New computer created successfully!");
//    }

    public void handleComputerDeletion() {
        System.out.println();
        System.out.println("Please type the name of the computer you want to delete: ");

        String selectedComputerName = scanner.nextLine();

        computerService.deleteComputerInDatabase(selectedComputerName);

        System.out.println();
        System.out.println("You have successfully deleted the computer!");
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
//                case "new" -> handleComputerCreation();
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
}
