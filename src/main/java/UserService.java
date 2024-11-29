import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();
    Scanner scanner = new Scanner(System.in);

    Utility utility = new Utility();

    public void insertUser(User user) {
        String sql = "INSERT INTO users (id, username, password, balance, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection usersConnection = dbManager.getConnection("users");
             PreparedStatement ps = usersConnection.prepareStatement(sql)) {

            ps.setString(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getBalance());
            ps.setString(5, user.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection usersConnection = dbManager.getConnection("users");
             PreparedStatement ps = usersConnection.prepareStatement(sql)) {

            ps.setString(1, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserBalance(String userId, int newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newBalance);
            ps.setString(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserPassword(String newPassword, String username) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserUsername(String newUsername, String currentUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newUsername);
            ps.setString(2, currentUsername);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean areUsersPresent() {
        String sql = "SELECT COUNT(*) FROM users";

        boolean usersExist = false;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                usersExist = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersExist;
    }

    public boolean isUsernamePresent(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateUserCredentials(String usernameToLogin, String passwordToLogin) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        boolean validLogin = false;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usernameToLogin);
            ps.setString(2, passwordToLogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                validLogin = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return validLogin;
    }

    public List<User> getAllUserProperties() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, balance, role FROM users";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("balance"),
                        rs.getString("role")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public String getUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        String userId = null;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getString("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String getUserRoleById(String userId) {
        String sql = "SELECT role FROM users WHERE id = ?";
        String role = null;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public int getBalanceById(String userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        int balance = 0;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getInt("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void depositMoney(String userId) {
        int currentBalance = getBalanceById(userId);

        System.out.println("Please enter any amount to deposit: ");
        int amount = scanner.nextInt();

        if (amount > 0) {
            int newBalance = currentBalance + amount;
            updateUserBalance(userId, newBalance);

            System.out.println("You have successfully put: " + amount + " $ in your account!");
            utility.printCartInterface();
        } else {
            System.out.println("Please enter a positive amount!");
            depositMoney(userId);
        }
    }


}