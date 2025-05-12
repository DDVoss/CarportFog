package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.User;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class UserMapper {

    public static int createUser(String firstName, String lastName, Integer phone, String email, String address, Integer zip, String password) throws DatabaseException {
        String sql = "insert into users (first_name, last_name, phone_nr, email, address, zip, password) values (?,?,?,?,?,?,?)";

        String genPassword = generateRandomPassword(8);

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, phone);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setInt(6, zip);
            ps.setString(7, genPassword);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0)  {
                throw new DatabaseException("Creating user failed, no rows affected");

            } try (ResultSet rs = ps.getGeneratedKeys())    {
                if(rs.next())   {
                    return rs.getInt(1);
                } else {
                    throw new DatabaseException("Creating user failed, no ID obtained");
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
        String sql = "UPDATE users SET first_name = ?, last_name = ?, phone_nr = ?, email = ?, address = ?, zip = ?, isAdmin = ?, password = ? WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getZip());
            ps.setBoolean(7,user.isAdmin());
            ps.setString(8, user.getPassword());

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
                String address = rs.getString("address");
                int zip = rs.getInt("zip");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                users.add(new User(id, firstName, lastName, email, phoneNumber, address, zip,isAdmin,password));
            }
            } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return users;
    }
    public static User getUserByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    int phoneNumber = rs.getInt("phone_nr");
                    String address = rs.getString("address");
                    int zip = rs.getInt("zip");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    String password = rs.getString("password");


                    return new User(id, firstName, lastName, email, phoneNumber, address, zip,isAdmin,password);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching user by email");
        }
        return null;
    }

    private static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom SR = new SecureRandom();
        StringBuilder SB = new StringBuilder();

        for (int i = 0; i < length; i++) {
            SB.append(chars.charAt(SR.nextInt(chars.length())));
        }

        return SB.toString();
    }



}
