package service;

import connection.DatabaseConnectionManager;
import exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public void createRole(int roleId, String roleName) {
        String sql = "INSERT INTO roles (id, role_name) VALUES (?, ?)";

        try (Connection conn = dbManager.getConnection("roles");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roleId);
            ps.setString(2, roleName);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error creating role with id: " + roleId + " and name: " + roleName, e);
        }
    }

    public int getDefaultUserRole() {
        String sql = "SELECT id FROM roles WHERE role_name = 'user' ";
        int roleId = 0;

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                roleId = rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error getting role id for role_name 'user'", e);
        }
        return roleId;
    }

    public String getUserRoleById(int userId) {
        String sql = "SELECT r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";

        try (Connection conn = dbManager.getConnection("users");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role_name");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting user's role by id: " + userId, e);
        }
        return null;
    }


}
