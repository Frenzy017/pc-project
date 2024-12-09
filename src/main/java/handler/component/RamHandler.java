package handler.component;

import mediator.IMediator;

import model.component.Ram;
import service.component.RamService;

import java.util.List;
import java.util.Scanner;

public class RamHandler {
    private final Scanner scanner;

    private final IMediator mediator;

    private final RamService ramService;

    private Ram ramObject;

    public RamHandler(IMediator mediator) {
        this.mediator = mediator;
        this.scanner = mediator.getScanner();
        this.ramService = mediator.getRamService();
    }

    public void handleRamConfig() {
        List<Ram> ram = ramService.getAllRam();

        System.out.println("Here is the available ram capacity: ");

        int index = 1;
        for (Ram ramObj : ram) {
            System.out.println(index++ + "." + "Ram: " + ramObj.getCapacity() + "GB," + " Price: " + ramObj.getPrice() + "$");
        }

        System.out.println();
        System.out.print("Please choose a ram capacity: ");

        int ramChoice = scanner.nextInt();
        ramObject = ram.get(ramChoice - 1);

        System.out.println("You have selected then following ram capacity: " + ramObject.getCapacity() + "," + " Price: " + ramObject.getPrice() + "$");
        System.out.println();
        System.out.println("Please proceed further with the other options to complete your build!");
        System.out.println();
    }

    public Ram getRamObject() {
        return ramObject;
    }
}
