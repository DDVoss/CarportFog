package app.persistence;

import app.entities.Order;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class OrderMapper {


    public static int createOrder(int userId, int width, int length) throws DatabaseException, SQLException {
        String sql = "INSERT INTO orders (user_id, width, length) " +
                "VALUES (?, ?, ?) RETURNING order_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, width);
            ps.setInt(3, length);

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

    //list of orderStatus
    public static List<String> getAllOrderStatuses() throws DatabaseException {
        List<String> statuses = new ArrayList<>();

        String sql = "SELECT status FROM order_status";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

                while (rs.next()){
                    statuses.add(rs.getString("status"));
                }

        } catch (SQLException e) {
            throw new DatabaseException(e, "kunne ikke hente hente statusser fra databasen");
        }
        return statuses;
    }


    // Update order details
    public static void updateOrderDetails(Order order) throws DatabaseException {
        String sql = "UPDATE orders SET order_status = ?, total_price = ?, width = ?, length = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, order.getOrderStatus());
            ps.setInt(2, order.getTotalPrice());
            ps.setInt(3, order.getWidth());
            ps.setInt(4, order.getLength());
            ps.setInt(5, order.getOrderId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Fejl ved opdatering af ordre detajlerne");
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


                orders.add(new Order(orderId, totalPrice, orderDate, width, length, orderStatus, userId, null));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return orders;
    }

    public static void insertBomItems(List<Bom> bomItems, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO bom (variant_id, order_id ,quantity, build_description) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            for (Bom bomItem : bomItems) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, bomItem.getVariant().getVariantId());
                    ps.setInt(2, bomItem.getOrder().getOrderId());
                    ps.setInt(3, bomItem.getQuantity());
                    ps.setString(4, bomItem.getBuild_description());

                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create bomItem in the database");
        }
    }

    public static Order getOrderDetailsById(int orderId) throws DatabaseException {

        String sql = "SELECT * FROM orders INNER JOIN users ON orders.user_id = users.user_id WHERE orders.order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    //user
                    int userId = rs.getInt("user_id");
                    String firstname = rs.getString("first_name");
                    String lastname = rs.getString("last_name");
                    String email = rs.getString("email");
                    int phoneNumber = rs.getInt("phone_nr");
                    String address = rs.getString("address");
                    int zip = rs.getInt("zip");
                    Boolean role = rs.getBoolean("is_admin");
                    String password = rs.getString("password");
                    //order
                    int order_Id = rs.getInt("order_id");
                    int totalPrice = rs.getInt("total_price");
                    String orderDate = rs.getString("order_date");
                    String orderStatus = rs.getString("order_status");
                    int width = rs.getInt("width");
                    int length = rs.getInt("length");

                    User user = new User(userId, firstname, lastname, phoneNumber, email, address, zip, role, password);
                    Order order = new Order(order_Id, totalPrice, orderDate, width, length, orderStatus, user);

                    return order;
                } else {
                    throw new DatabaseException("Ordren med ID " + orderId + " blev ikke fundet");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Fejl ved hentning af ordrer med brugerinformationer");
        }
    }
}


