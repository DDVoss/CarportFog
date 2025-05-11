package app.persistence;

import app.entities.Order;

import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class OrderMapper {

    /*
    // Create a new order and return the generated order_id
    public static int createOrder(Order order) throws DatabaseException, SQLException {
        String sql = "INSERT INTO orders (user_id, total_price, order_date, order_status, width, length) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING order_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalPrice());
            ps.setString(3, order.getOrderDate());
            ps.setString(4, order.getOrderStatus());
            ps.setInt(5, order.getWidth());
            ps.setInt(6, order.getLength());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("order_id");
                } else {
                    throw new DatabaseException("no order ID returned from database");
                }
            } catch (SQLException e) {
                throw new DatabaseException(e, "Error creating order");
            }

        }
    }
     */

    public static void createOrder(int userId, int width, int length) throws DatabaseException  {
        String sql = "INSERT INTO orders (user_id, width, length) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))  {

            ps.setInt(1, userId);
            ps.setInt(2, width);
            ps.setInt(3, length);

            ps.executeUpdate();
        } catch (SQLException e)  {
            throw new DatabaseException(e, "Error inserting order");
        }
    }


    public static void updateOrder(Order order) throws DatabaseException {
        String sql = "UPDATE orders SET user_id = ?, total_price = ?, order_date = ?, order_status = ?, width = ?, length = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalPrice());
            ps.setString(3, order.getOrderDate());
            ps.setString(4, order.getOrderStatus());
            ps.setInt(5, order.getWidth());
            ps.setInt(6, order.getLength());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating user");
        }
    }


    public static List<Order> getAllOrders() throws DatabaseException {
        List<Order> orders= new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                int totalPrice = rs.getInt("total_price");
                String orderDate = rs.getString("order_date");
                String orderStatus = rs.getString("order_status");
                int width = rs.getInt("width");
                int length = rs.getInt("length");


                orders.add(new Order(orderId, userId, totalPrice, orderDate, orderStatus, width, length));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return orders;
    }

    //Delete order

    public static void deleteOrder (int orderId) throws DatabaseException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting order");
        }
    }
    }


