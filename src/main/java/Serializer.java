import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {
    Store store = new Store();
    ArrayList<User> users = store.getUsersList();
    List<Computer> computers = store.getComputers();

    public void serializeUsers(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(users);

            out.close();
            fileOut.close();

            System.out.println("Users have been serialized");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializeComputers(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(computers);

            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deserializedUsers(String fileName) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            ArrayList<User> deSerializedObj = (ArrayList<User>) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");


        } catch (IOException e) {
            System.out.println("IOException is caught");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught");
            e.printStackTrace();
        }
    }
}
