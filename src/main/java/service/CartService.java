package service;

import connection.DatabaseConnectionManager;
import model.CartItem;
import model.Computer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();
    private int cartId;

    public void createCartForUser(String userId) {
        try (Connection conn = dbManager.getConnection("users")) {
            String sql = "INSERT INTO Cart (userId) VALUES (?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, userId);
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.cartId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToCart(CartItem cartItem) {
        try (Connection conn = dbManager.getConnection("users")) {
            String sql = "INSERT INTO CartItem (cartId, computerId, computerName, computerPrice) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, cartItem.getCartId());
                ps.setString(2, cartItem.getComputerId());
                ps.setString(3, cartItem.getComputerName());
                ps.setDouble(4, cartItem.getComputerPrice());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userHasCart(String userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE userid = ?";
        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCartIdByUserId(String userId) {
        String sql = "SELECT cartId FROM Cart WHERE userId = ?";
        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cartId");
            } else {
                throw new IllegalArgumentException("Cart not found for userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred");
        }
    }

    public List<Computer> getComputersByCartId(int cartId) {
        List<Computer> computers = new ArrayList<>();
        List<String> computerIds = new ArrayList<>();

        String sqlCartItems = "SELECT computerId FROM cartItem WHERE cartId = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sqlCartItems)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                computerIds.add(rs.getString("computerId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlComputers = "SELECT id, name, graphicCard, ram, processor, price FROM computers WHERE id = ?";

        try (Connection conn = dbManager.getConnection("computers")) {
            for (String computerId : computerIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlComputers)) {
                    ps.setString(1, computerId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
    }

    public void deleteComputersByCartId(int cartId) {
        List<String> computerIds = new ArrayList<>();

        String sqlCartItems = "SELECT computerId FROM cartItem WHERE cartId = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sqlCartItems)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                computerIds.add(rs.getString("computerId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlDeleteCartItem = "DELETE FROM cartItem WHERE cartId = ? AND computerId = ?";

        try (Connection conn = dbManager.getConnection("users")) {
            for (String computerId : computerIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCartItem)) {
                    ps.setInt(1, cartId);
                    ps.setString(2, computerId);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}