package service.component;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.component.Ram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RamService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public int getRamCapacityById(int ramId) {
        String sql = "SELECT capacity FROM ram WHERE id = ?";
        int capacity = 0;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ramId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                capacity = rs.getInt("capacity");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving ram name for ID: " + ramId, e);
        }
        return capacity;
    }

    public int getRamPriceById(int ramId) {
        String sql = "SELECT price FROM ram WHERE id = ?";
        int price = 0;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ramId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving ram price for ID: " + ramId, e);
        }
        return price;
    }

    public List<Ram> getAllRam() {
        String sql = "SELECT id, capacity, quantity, price FROM ram";
        List<Ram> ram = new ArrayList<>();
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt("id");
                int capacity = rs.getInt("capacity");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                ram.add(new Ram(id, capacity, quantity, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all ram capacity", e);
        }
        return ram;
    }

    public void decreaseRamQuantityByOne(int ramId) {
        String sql = "UPDATE ram SET quantity = quantity - 1 WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ramId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseException("No RAM found with ID: " + ramId, new SQLException());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error decreasing RAM quantity for ID: " + ramId, e);
        }
    }
}
