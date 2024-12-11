package handler.component;

import mediator.IMediator;

import model.component.Processor;
import service.component.ProcessorService;

import java.util.List;
import java.util.Scanner;

public class ProcessorHandler {
    private final Scanner scanner;

    private final IMediator mediator;

    private final ProcessorService processorService;

    private Processor processorObject;

    public ProcessorHandler(IMediator mediator) {
        this.mediator = mediator;
        this.scanner = mediator.getScanner();
        this.processorService = mediator.getProcessorService();
    }

    public void handleProcessorConfig() {
        List<Processor> processors = processorService.getAllProcessors();

        System.out.println("Here are the available processors: ");

        int index = 1;
        for (Processor processor : processors) {
            System.out.println(index++ + "." + "Processor: " + processor.getName() + "," + " Price: " + processor.getPrice() + "$");
        }

        System.out.println();
        System.out.print("Please choose a processor: ");

        int processorChoice = scanner.nextInt();
        processorObject = processors.get(processorChoice - 1);

        System.out.println("You have selected then following processor: " + processorObject.getName() + "," + " Price: " + processorObject.getPrice() + "$");
        System.out.println();
        System.out.println("Please proceed further with the other options to complete your build!");
        System.out.println();
    }

    public Processor getProcessorObject() {
        return processorObject;
    }
}
