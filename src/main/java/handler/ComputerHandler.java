package handler;

import java.sql.SQLException;
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

    private boolean isConfigurationComplete = false;

    private boolean isProcessorConfigured = false;
    private boolean isRamConfigured = false;
    private boolean isVideoCardConfigured = false;

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
                case 4 -> handleComputerBuildConfiguration();
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

        int processorQuantity = processorService.getProcessorQuantityById(computerProcessorId);
        int ramQuantity = ramService.getRamQuantityById(computerRamId);
        int videoCardQuantity = videoService.getVideoCardQuantityById(computerVideoCardId);

        int processorPrice = processorService.getProcessorPriceById(computerProcessorId);
        int ramPrice = ramService.getRamPriceById(computerRamId);
        int videoCardPrice = videoService.getVideoCardPriceById(computerVideoCardId);

        Map<String, Object> componentDetails = new HashMap<>();
        componentDetails.put("processorId", computerProcessorId);
        componentDetails.put("ramId", computerRamId);
        componentDetails.put("videoCardId", computerVideoCardId);

        componentDetails.put("processorName", processorName);
        componentDetails.put("ramCapacity", ramCapacity);
        componentDetails.put("videoCardName", videoCardName);

        componentDetails.put("processorQuantity", processorQuantity);
        componentDetails.put("ramQuantity", ramQuantity);
        componentDetails.put("videoCardQuantity", videoCardQuantity);

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
            isConfigurationComplete = true;
            System.out.println("Here is your newly configured computer: ");
            System.out.println(configuredComputerPrint);
            System.out.println();
            isProcessorConfigured = false;
            isRamConfigured = false;
            isVideoCardConfigured = false;
        }
    }

    public void handleComputerRegularSelection() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
        }

        System.out.print("Proceeding with regular selection, please select a computer by typing the number it is associated with: ");

        int selectedComputerIndex = scanner.nextInt() - 1;


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
                case "new" -> handleCreateComputer();
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

    public void handleComputerModificationProperties() {
        List<Computer> computers = computerService.getAllComputerProperties();

        if (computers.isEmpty()) {
            System.out.println("No computers available.");
            return;
        }

        System.out.print("Please enter the number of the computer to modify: ");
        int computerToModifyIndex = scanner.nextInt() - 1;

        Computer computerToModify = computers.get(computerToModifyIndex);

        utility.printModificationOptions();

        int modifyCommand = scanner.nextInt();
        scanner.nextLine();

        switch (modifyCommand) {
            case 1 -> handleModificationName(computerToModify);
            case 2 -> handleModificationProcessor(computerToModify);
            case 3 -> handleModificationRam(computerToModify);
            case 4 -> handleModificationVideoCard(computerToModify);
            case 5 -> handleQuantityModification();
            default -> {
                utility.returnToAdminComputerOptions();
                return;
            }
        }
        computerService.updateComputerInDatabase(computerToModify);
        System.out.print("Press Enter to return to the main interface...");
    }

    private void handleModificationName(Computer computerToModify) {
        List<Computer> computers = computerService.getAllComputerProperties();

        boolean isNameValid = false;

        while (!isNameValid) {
            System.out.print("Enter the name you want to modify it to: ");
            String computerName = scanner.nextLine();

            boolean computerNameExists = computers.stream().anyMatch(computer -> computer.getName().equals(computerName));

            if (computerNameExists) {
                System.out.println("The name is already taken, please choose a different one.");
            } else {
                computerToModify.setName(computerName);
                isNameValid = true;
                System.out.println("You have successfully changed the computer's name!");
            }
        }
    }

    private void handleModificationProcessor(Computer computerToModify) {
        Map<String, Object> componentDetails = getComponentDetails(computerToModify);

        System.out.println("The current processor of the computer you have selected is: " + componentDetails.get("processorName"));
        System.out.println();

        boolean isNameValid = false;

        while (!isNameValid) {
            processorHandler.printProcessorOptions();

            Processor processorObject = processorHandler.getProcessorObject();

            System.out.println("You have selected the following processor: " + processorObject.getName() + ", Price: " + processorObject.getPrice() + "$");
            System.out.println();

            System.out.print("Do you want to update the computer with that processor? [Yes/No] ");

            scanner.nextLine();
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("Yes")) {
                computerToModify.setProcessor_id(processorObject.getId());
                System.out.println("Processor updated!");
                isNameValid = true;
            } else {
                System.out.println();
                System.out.println("Please select a different processor.");
            }
        }
    }

    private void handleModificationRam(Computer computerToModify) {
        Map<String, Object> componentDetails = getComponentDetails(computerToModify);

        System.out.println("The current RAM of the computer you have selected is: " + componentDetails.get("ramCapacity") + "GB");
        System.out.println();

        boolean isNameValid = false;

        while (!isNameValid) {
            ramHandler.printRamOptions();

            Ram ramObject = ramHandler.getRamObject();

            System.out.println("You have selected the following RAM: " + ramObject.getCapacity() + "GB, Price: " + ramObject.getPrice() + "$");
            System.out.println();

            System.out.print("Do you want to update the computer with that RAM? [Yes/No] ");

            scanner.nextLine();
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("Yes")) {
                computerToModify.setRam_id(ramObject.getId());
                System.out.println("RAM updated!");
                isNameValid = true;
            } else {
                System.out.println();
                System.out.println("Please select a different RAM.");
            }
        }
    }

    private void handleModificationVideoCard(Computer computerToModify) {
        Map<String, Object> componentDetails = getComponentDetails(computerToModify);

        System.out.println("The current video card of the computer you have selected is: " + componentDetails.get("videoCardName"));
        System.out.println();

        boolean isNameValid = false;

        while (!isNameValid) {
            videoCardHandler.printVideoCardOptions();

            VideoCard videoCardObject = videoCardHandler.getVideoCardObject();

            System.out.println("You have selected the following video card: " + videoCardObject.getName() + ", Price: " + videoCardObject.getPrice() + "$");
            System.out.println();

            System.out.print("Do you want to update the computer with that video card? [Yes/No] ");

            scanner.nextLine();
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("Yes")) {
                computerToModify.setVideoCard_id(videoCardObject.getId());
                System.out.println("Video card updated!");
                isNameValid = true;
            } else {
                System.out.println();
                System.out.println("Please select a different video card.");
            }
        }
    }

    private void handleQuantityModification() {
        System.out.println("Select a component to modify the quantity:");
        System.out.println("1. Processor");
        System.out.println("2. RAM");
        System.out.println("3. Video Card");
        System.out.print("Enter your choice: ");

        int componentChoice = scanner.nextInt();
        scanner.nextLine();

        switch (componentChoice) {
            case 1 -> handleQuantityProcessor();
            case 2 -> handleQuantityRam();
            case 3 -> handleQuantityVideoCard();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void handleQuantityProcessor() {
        processorHandler.printProcessorQuantity();

        Processor processorObject = processorHandler.getProcessorObject();

        System.out.println("You have selected: " + "Processor: " + processorObject.getName() + " -" + " Quantity: " + processorObject.getQuantity());
        System.out.println();

        System.out.print("Please enter the new quantity value for the processor: ");

        int quantityValue = scanner.nextInt();

        processorObject.setQuantity(quantityValue);
        processorService.updateProcessorQuantityInDatabase(processorObject);

        System.out.println("Processor quantity successfully updated!");
        System.out.println();
    }

    private void handleQuantityRam() {
        ramHandler.printRamQuantity();

        Ram ramObject = ramHandler.getRamObject();

        System.out.println("You have selected: " + "RAM: " + ramObject.getCapacity() + "GB -" + " Quantity: " + ramObject.getQuantity());
        System.out.println();

        System.out.print("Please enter the new quantity value for the RAM: ");
        int quantityValue = scanner.nextInt();

        ramObject.setQuantity(quantityValue);
        ramService.updateRamQuantityInDatabase(ramObject);

        System.out.println("Ram quantity successfully updated!");
        System.out.println();
    }

    private void handleQuantityVideoCard() {
        videoCardHandler.printVideoCardQuantity();

        VideoCard videoCardObject = videoCardHandler.getVideoCardObject();

        System.out.println("You have selected: " + "Video Card: " + videoCardObject.getName() + " -" + " Quantity: " + videoCardObject.getQuantity());
        System.out.println();

        System.out.print("Please enter the new quantity value for the video card: ");
        int quantityValue = scanner.nextInt();

        videoCardObject.setQuantity(quantityValue);
        videoService.updateVideoCardQuantityInDatabase(videoCardObject);

        System.out.println("Video card quantity successfully updated!");
        System.out.println();
    }



    public void handleCreateComputer() {
        String name = handleCreateName();

        int processor_id = handleCreateProcessor();
        if (processor_id == -1) {
            System.out.println("Processor creation failed. Aborting computer creation.");
            return;
        }

        int ram_id = handleSelectRam();

        int videoCard_id = handleCreateVideoCard();
        if (videoCard_id == -1) {
            System.out.println("Video card creation failed. Aborting computer creation.");
            return;
        }

        Computer computer = new Computer(0, name, processor_id, ram_id, videoCard_id, 0);

        computerService.insertComputerIntoDatabase(computer);
        System.out.println("Computer created successfully.");
    }

    private String handleCreateName() {
        List<Computer> computers = computerService.getAllComputerProperties();

        System.out.print("Enter computer name: ");
        String computerName = scanner.nextLine();

        boolean computerNameExists = computers.stream().anyMatch(computer -> computer.getName().equalsIgnoreCase(computerName));

        if (computerNameExists) {
            System.out.println("A computer with this name already exists. Please choose a different name.");
            return computerName;
        }
        return computerName;
    }

    private int handleCreateProcessor() {
        List<Processor> processors = processorService.getAllProcessors();

        System.out.print("Enter processor name: ");
        String processorName = scanner.nextLine();

        boolean processorNameExists = processors.stream().anyMatch(processor -> processor.getName().equalsIgnoreCase(processorName));

        if (processorNameExists) {
            System.out.println("A processor with this name already exists. Please choose a different name.");
        }

        System.out.print("Enter quantity for the new processor: ");
        int processorQuantity = scanner.nextInt();

        System.out.print("Enter price for the new processor: ");
        int processorPrice = scanner.nextInt();

        Processor processor = new Processor(0, processorName, processorQuantity, processorPrice);
        processorService.createProcessor(processor);

        int generatedId = processor.getId();

        return generatedId;
    }

    private int handleSelectRam() {
        System.out.println();
        ramHandler.printRamOptions();
        Ram ramObject = ramHandler.getRamObject();
        return ramObject.getId();
    }

    private int handleCreateVideoCard() {
        List<VideoCard> videoCards = videoService.getAllVideoCards();

        scanner.nextLine();
        System.out.print("Enter video card name: ");
        String videoCardName = scanner.nextLine();

        boolean videoCardNameExists = videoCards.stream().anyMatch(videoCard -> videoCard.getName().equalsIgnoreCase(videoCardName));

        if (videoCardNameExists) {
            System.out.println("A video card with this name already exists. Please choose a different name.");
            return -1;
        }

        System.out.print("Enter quantity for the new video card: ");
        int videoCardQuantity = scanner.nextInt();

        System.out.print("Enter price for the new video card: ");
        int videoCardPrice = scanner.nextInt();


        VideoCard videoCard = new VideoCard(0, videoCardName, videoCardQuantity, videoCardPrice);
        videoService.createVideoCard(videoCard);

        int generatedId = videoCard.getId();

        return generatedId;
    }

    public void handleComputerDeletion() {
        System.out.println();
        System.out.print("Please select a computer to delete by typing the number it is associated with: ");

        int selectedComputerIndex = scanner.nextInt() - 1;

        List<Computer> computers = computerService.getAllComputerProperties();

        if (selectedComputerIndex >= 0 && selectedComputerIndex < computers.size()) {
            Computer selectedComputer = computers.get(selectedComputerIndex);
            computerService.deleteComputerInDatabaseById(selectedComputer.getId());

            System.out.println("You have successfully deleted the computer!");
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }
}
