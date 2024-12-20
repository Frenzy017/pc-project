package service;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public void addUserToDatabase(User user) {
        String sql = "INSERT INTO users (username, password, balance, role_id) VALUES (?, ?, ?, ?)";

        try (Connection usersConnection = dbManager.getConnection("pcstore");
             PreparedStatement ps = usersConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getBalance());
            ps.setInt(4, user.getRole_Id());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

            System.out.println();
            System.out.println("Account created successfully!");
            System.out.println();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: The username '" + user.getUsername() + "' is already taken. Please choose a different username.");
        } catch (SQLException e) {
            throw new DatabaseException("Error adding user to database: " + user, e);
        }
    }

    public void removeUserFromDatabase(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection usersConnection = dbManager.getConnection("pcstore");
             PreparedStatement ps = usersConnection.prepareStatement(sql)) {

            ps.setInt(1, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error removing user from database: " + user, e);
        }
    }

    public int getBalanceById(int userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        int balance = 0;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getInt("balance");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting balance byy user id: " + userId, e);
        }
        return balance;
    }

    public void updateUserBalance(int userId, int amountToDeposit) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        int updatedBalance = getBalanceById(userId) + amountToDeposit;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, updatedBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating user's balance: " + userId, e);
        }
    }

    public void updateUserBalanceDeduct(int userId, int amountToDeduct) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        int updatedBalance = getBalanceById(userId) - amountToDeduct;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, updatedBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error deducting user's balance: " + userId, e);
        }
    }

    public void updateUserPassword(String newPassword, String username) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating user's password: " + username, e);
        }
    }

    public void updateUserUsername(String newUsername, String currentUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newUsername);
            ps.setString(2, currentUsername);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating user's username: " + currentUsername, e);
        }
    }

    public boolean areUsersPresent() {
        String sql = "SELECT COUNT(*) FROM users";

        boolean usersExist = false;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                usersExist = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error checking if users are present", e);
        }
        return usersExist;
    }

    public boolean isUsernamePresent(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error checking if username is present: " + username, e);
        }
        return false;
    }

    public boolean validateUserCredentials(String usernameToLogin, String passwordToLogin) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        boolean validLogin = false;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usernameToLogin);
            ps.setString(2, passwordToLogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                validLogin = true;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error validating user's credentials: " + usernameToLogin, e);
        }
        return validLogin;
    }

    public List<User> getAllUserProperties() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, balance, role_id FROM users";

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("balance"),
                        rs.getInt("role_id")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting all user properties", e);
        }
        return users;
    }

    public int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        int userId = 0;

        try (Connection conn = dbManager.getConnection("pcstore");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting user's id by username: " + username, e);
        }
        return userId;
    }
}