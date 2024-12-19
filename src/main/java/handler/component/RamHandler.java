package handler.component;

import mediator.IMediator;

import model.component.Processor;
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
        printRamOptions();

        System.out.println("You have selected then following ram capacity: " + ramObject.getCapacity() + "," + " Price: " + ramObject.getPrice() + "$");
        System.out.println();
        System.out.println("Please proceed further with the other options to complete your build!");
        System.out.println();
    }

    public void printRamOptions() {
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
    }

    public void printRamQuantity() {
        List<Ram> rams = ramService.getAllRam();

        System.out.println();
        System.out.println("Here are the available RAMs: ");

        int index = 1;
        for (Ram ram : rams) {
            System.out.println(index++ + ". RAM: " + ram.getCapacity() + "GB - Quantity: " + ram.getQuantity());
        }

        System.out.println();
        System.out.print("Please choose a RAM: ");

        int ramChoice = scanner.nextInt();
        ramObject = rams.get(ramChoice - 1);
    }

    public Ram getRamObject() {
        return ramObject;
    }

    public void setRamObject(Ram ramObject) {
        this.ramObject = ramObject;
    }
}
