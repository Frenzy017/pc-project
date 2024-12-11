package service.component;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.component.Ram;
import model.component.VideoCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public String getVideoCardNameById(int videoCardId) {
        String sql = "SELECT name FROM videoCard WHERE id = ?";
        String videoCard = null;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, videoCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                videoCard = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving video card name for ID: " + videoCardId, e);
        }
        return videoCard;
    }

    public int getVideoCardPriceById(int videoCardId) {
        String sql = "SELECT price FROM videoCard WHERE id = ?";
        int price = 0;
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, videoCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving video card price for ID: " + videoCardId, e);
        }
        return price;
    }

    public List<VideoCard> getAllVideoCards() {
        String sql = "SELECT id, name, quantity, price FROM videoCard";
        List<VideoCard> videoCards = new ArrayList<>();
        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                videoCards.add(new VideoCard(id, name, quantity, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all video cards", e);
        }
        return videoCards;
    }

    public void decreaseVideoCardQuantityByOne(int videoCardId) {
        String sql = "UPDATE videoCard SET quantity = quantity - 1 WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, videoCardId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseException("No video card found with ID: " + videoCardId, new SQLException());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error decreasing video card quantity for ID: " + videoCardId, e);
        }
    }
}
