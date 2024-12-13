package model;

public class Computer {
    public int id;
    public String name;
    public int processor_id;
    public int ram_id;
    public int videoCard_id;
    public int totalPrice;

    public Computer(int id, String name, int processor_id, int ram_id, int videoCard_id, int totalPrice) {
        this.id = id;
        this.name = name;
        this.processor_id = processor_id;
        this.ram_id = ram_id;
        this.videoCard_id = videoCard_id;
        this.totalPrice = totalPrice;
    }

    public void setId(int computerId) {
        this.id = computerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVideoCard_id(int videoCard_id) {
        this.videoCard_id = videoCard_id;
    }

    public void setRam_id(int ram_id) {
        this.ram_id = ram_id;
    }

    public void setProcessor_id(int processor_id) {
        this.processor_id = processor_id;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getVideoCard_id() {
        return this.videoCard_id;
    }

    public int getRam_id() {
        return this.ram_id;
    }

    public int getProcessor_id() {
        return this.processor_id;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

}
