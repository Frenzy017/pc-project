package handler.component;

import mediator.IMediator;

import model.component.VideoCard;
import service.component.VideoService;

import java.util.List;
import java.util.Scanner;

public class VideoCardHandler {
    private final Scanner scanner;

    private final IMediator mediator;

    private final VideoService videoService;

    private VideoCard videoCardObject;

    public VideoCardHandler(IMediator mediator) {
        this.mediator = mediator;
        this.scanner = mediator.getScanner();
        this.videoService = mediator.getVideoService();
    }

    public void handleVideoCardConfig() {
        List<VideoCard> videoCards = videoService.getAllVideoCards();

        System.out.println("Here are the available video cards: ");

        int index = 1;
        for (VideoCard videoCard : videoCards) {
            System.out.println(index++ + "." + "VideoCard: " + videoCard.getName() + "," + " Price: " + videoCard.getPrice() + "$");
        }

        System.out.println();
        System.out.print("Please choose a video card: ");

        int videoCardChoice = scanner.nextInt();
        videoCardObject = videoCards.get(videoCardChoice - 1);

        System.out.println("You have selected then following video card: " + videoCardObject.getName() + "," + " Price: " + videoCardObject.getPrice() + "$");
        System.out.println();
        System.out.println("Please proceed further with the other options to complete your build!");
        System.out.println();
    }

    public VideoCard getVideoCardObject() {
        return videoCardObject;
    }
}
