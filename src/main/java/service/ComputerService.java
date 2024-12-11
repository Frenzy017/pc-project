package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.Computer;

public class ComputerService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public int insertComputerIntoDatabase(Computer computer) {
        String sql = "INSERT INTO computers (name, processor_id, ram_id, videoCard_id, totalPrice) VALUES (?, ?, ?, ?, ?)";
        int generatedId = 0;

        try (Connection computersConnection = dbManager.getConnection("computers");
             PreparedStatement ps = computersConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, computer.getName());
            ps.setInt(2, computer.getProcessor_id());
            ps.setInt(3, computer.getRam_id());
            ps.setInt(4, computer.getVideoCard_id());
            ps.setInt(5, computer.getTotalPrice());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting computer in database: " + computer, e);
        }

        return generatedId;
    }

    public void updateComputerInDatabase(Computer computer) {
        String sql = "UPDATE computers SET name = ?, processor_id = ?, ram_id = ?, videoCard_id = ?, totalPrice = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, computer.getName());
            ps.setInt(2, computer.getVideoCard_id());
            ps.setInt(3, computer.getRam_id());
            ps.setInt(4, computer.getProcessor_id());
            ps.setInt(5, computer.getTotalPrice());
            ps.setInt(6, computer.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating computer in database: " + computer, e);
        }
    }

    public void updateComputerPriceInDatabase(Computer computer, int buildPrice) {
        String sql = "UPDATE computers SET totalPrice = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, buildPrice);
            ps.setInt(2, computer.getId());
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
        String sql = "SELECT id, name, processor_id, ram_id, videoCard_id, totalPrice FROM computers";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Computer computer = new Computer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("processor_id"),
                        rs.getInt("ram_id"),
                        rs.getInt("videoCard_id"),
                        rs.getInt("totalPrice")
                );
                computers.add(computer);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all computer properties", e);
        }
        return computers;
    }
}