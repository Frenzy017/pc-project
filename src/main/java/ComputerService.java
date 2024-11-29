import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComputerService {
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public void insertComputer(Computer computer) {
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

    public void deleteComputer(int computer) {
        String sql = "DELETE FROM computers WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, computer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComputersToDatabase(List<Computer> computers) {
        for (Computer computer : computers) {
            computer.id = UUID.randomUUID().toString();
            insertComputer(computer);
        }
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
/

}