package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class UserMapper {

    // Create
    public static void createUser(User user) throws DatabaseException {
        String sql = "INSERT INTO users (first_name, last_name, phone_nr, email, user_role, address, zip)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING user_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getZip());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting user");
        }
    }

    // Delete user by email
    public static void deleteUser(String email) throws DatabaseException {
        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting user");
        }
    }

    // Update full user
    public static void updateUser(User user) throws DatabaseException {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, phone_nr = ?, email = ?, user_role = ?, address = ?, zip = ? WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getZip());
            ps.setString(8, user.getEmail());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating user");
        }
    }

    // Read: get all users
    public static List<User> getAllUsers() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone_nr");
                String role = rs.getString("user_role");
                String address = rs.getString("address");
                int zip = rs.getInt("zip");

                users.add(new User(id, firstName, lastName, email, phoneNumber, role, address, zip));
            }
            } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return users;
    }

}
