package service.component;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.component.Processor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessorService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public String getProcessorNameById(int processorId) {
        String sql = "SELECT name FROM processor WHERE id = ?";
        String processor = null;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, processorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                processor = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving processor name for ID: " + processorId, e);
        }
        return processor;
    }

    public int getProcessorPriceById(int processorId) {
        String sql = "SELECT price FROM processor WHERE id = ?";
        int price = 0;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, processorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving processor price for ID: " + processorId, e);
        }
        return price;
    }

    public List<Processor> getAllProcessors() {
        String sql = "SELECT id, name, quantity, price FROM processor";
        List<Processor> processors = new ArrayList<>();
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                processors.add(new Processor(id, name, quantity, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all processors", e);
        }
        return processors;
    }

    public void decreaseProcessorQuantityByOne(int processorId) {
        String sql = "UPDATE processor SET quantity = quantity - 1 WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, processorId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseException("No processor found with ID: " + processorId, new SQLException());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error decreasing processor quantity for ID: " + processorId, e);
        }
    }
}
