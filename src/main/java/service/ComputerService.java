package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.Computer;

public class ComputerService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public void insertComputerIntoDatabase(Computer computer) {
        String sql = "INSERT INTO computers (name, graphicCard, ram, processor, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection computersConnection = dbManager.getConnection("computers");
             PreparedStatement ps = computersConnection.prepareStatement(sql)) {

            ps.setString(1, computer.getName());
            ps.setString(2, computer.getGraphicCard());
            ps.setInt(3, computer.getRam());
            ps.setString(4, computer.getProcessor());
            ps.setInt(5, computer.getPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting computer in database: " + computer, e);
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
            ps.setInt(6, computer.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating computer in database: " + computer, e);
        }
    }

    public void deleteComputerInDatabase(String name) {
        String sql = "DELETE FROM computers WHERE name = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting computer from database: " + name, e);
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
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("graphicCard"),
                        rs.getInt("ram"),
                        rs.getString("processor"),
                        rs.getInt("price")
                );
                computers.add(computer);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all computer properties", e);
        }
        return computers;
    }
}