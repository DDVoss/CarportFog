package app.persistence.mappers;

import app.entities.*;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class OrderMapper {


    public static void createOrder(int userId, int width, int length) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, width, length) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, width);
            ps.setInt(3, length);

            ps.executeUpdate();
        } catch (SQLException e) {
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



    public static void deleteOrder(int orderId) throws DatabaseException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting order");
        }
    }

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders inner join users using(user_id)";

        try (
                Connection connection = connectionPool.getConnection();
                var preparedStatement = connection.prepareStatement(sql);
                var rs = preparedStatement.executeQuery();
        ) {

            while (rs.next()) {

                // User

                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phone = rs.getInt("phone_nr");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int zip = rs.getInt("zip");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                // Order

                int orderId = rs.getInt("order_id");
                int totalPrice = rs.getInt("total_price");
                String orderStatus = rs.getString("order_status");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String orderDate = rs.getString("order_date");

                User user = new User(userId, firstName, lastName, phone, email, address, zip, isAdmin, password);
                Order order = new Order(orderId, totalPrice, orderStatus, width, length, orderDate, user);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return orderList;
    }

    public static List<Bom> getBomByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        List<Bom> bomList = new ArrayList<>();
        String sql = "SELECT * FROM bom_view where order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
        ) {
            prepareStatement.setInt(1, orderId);
            var rs = prepareStatement.executeQuery();
            while (rs.next()) {

                // Order
                int totalPrice = rs.getInt("total_price");
                String orderStatus = rs.getString("order_status");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String orderDate = rs.getString("order_date");

                Order order = new Order(orderId, totalPrice, orderStatus, width, length, orderDate, null);

                // Material
                int materialId = rs.getInt("material_id");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                String type = rs.getString("type");
                int pricePrUnit = rs.getInt("price_pr_unit");

                Material material = new Material(materialId, description, unit, type, pricePrUnit);

                // Variant
                int variantId = rs.getInt("variant_id");
                int varLength = rs.getInt("length");

                Variant variant = new Variant(variantId, material, varLength);

                // Bom
                int bomId = rs.getInt("bom_id");
                int quantity = rs.getInt("quantity");
                String buildDescription = rs.getString("build_description");

                Bom bom = new Bom(bomId, quantity, buildDescription, order, variant);
                bomList.add(bom);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bomList;
    }

    public static void insertBomItems(List<Bom> bomItems, ConnectionPool connectionPool) throws DatabaseException   {
        String sql = "INSERT INTO bom (order_id. variant_id, quantity, build_description " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())    {
            for (Bom bomItem : bomItems)  {
                try (PreparedStatement ps = connection.prepareStatement(sql))   {
                    ps.setInt(1, bomItem.getOrder().getOrderId());
                    ps.setInt(2, bomItem.getVariant().getVariantId());
                    ps.setInt(3, bomItem.getQuantity());
                    ps.setString(4, bomItem.getBuild_description());
                }
            }
        } catch (SQLException e)   {
            throw new DatabaseException("Could not create bomItem in the database");
        }
    }
}


