package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import connection.DatabaseConnectionManager;
import model.Computer;

public class ComputerService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public void insertComputerIntoDatabase(Computer computer) {
        String sql = "INSERT INTO computers (id, name, graphicCard, ram, processor, price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection computersConnection = dbManager.getConnection("computers");
             PreparedStatement ps = computersConnection.prepareStatement(sql)) {

            ps.setString(1, computer.getId());
            ps.setString(2, computer.getName());
            ps.setString(3, computer.getGraphicCard());
            ps.setInt(4, computer.getRam());
            ps.setString(5, computer.getProcessor());
            ps.setInt(6, computer.getPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateComputerInDatabase(Computer computer) {
        String sql = "UPDATE computers SET name = ?, graphicCard = ?, ram = ?, processor = ?, price = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, computer.getName());
            ps.setString(2, computer.getGraphicCard());
            ps.setInt(3, computer.getRam());
            ps.setString(4, computer.getProcessor());
            ps.setInt(5, computer.getPrice());
            ps.setString(6, computer.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComputerInDatabase(String name) {
        String sql = "DELETE FROM computers WHERE name = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComputersToDatabase(List<Computer> computers) {
        for (Computer computer : computers) {
            computer.id = UUID.randomUUID().toString();
            insertComputerIntoDatabase(computer);
        }
    }

    public List<Computer> getAllComputerProperties() {
        List<Computer> computers = new ArrayList<>();
        String sql = "SELECT id, name, graphicCard, ram, processor, price FROM computers";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Computer computer = new Computer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("graphicCard"),
                        rs.getInt("ram"),
                        rs.getString("processor"),
                        rs.getInt("price")
                );
                computers.add(computer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
    }


//    public void updateComputer(Computer computer) {
//        String sql = "UPDATE computers SET name = ?, graphicCard = ?, ram = ?, processor = ?, price = ? WHERE id = ?";
//
//        try (Connection conn = dbManager.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, computer.getName());
//            ps.setString(2, computer.getGraphicCard());
//            ps.setInt(3, computer.getRam());
//            ps.setString(4, computer.getProcessor());
//            ps.setInt(5, computer.getPrice());
//            ps.setLong(6, computer.getId());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}