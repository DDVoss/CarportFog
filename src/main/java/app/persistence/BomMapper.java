package app.persistence;

import app.entities.Bom;
import app.entities.Material;
import app.entities.Variant;
import app.exceptions.DatabaseException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BomMapper {

    public static List<Bom> getBomItemsByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        List<Bom> bomItems = new ArrayList<>();

        String sql = "SELECT o.order_id, o.width, o.length, o.order_date, " +
                "bom.quantity, bom.build_description, v.length AS variant_length, " +
                "m.description, m.unit, m.type " +
                "FROM orders o " +
                "JOIN bom USING (order_id) " +
                "JOIN variants v USING (variant_id) " +
                "JOIN materials m USING (material_id) " +
                "WHERE o.order_id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);  // bind orderId to the ?

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    int quantity = rs.getInt("quantity");
                    String buildDescription = rs.getString("build_description");

                    int variantLength = rs.getInt("variant_length");
                    String variantDescription = rs.getString("description");

                    String materialUnit = rs.getString("unit");

                    Variant variant = new Variant(variantLength,variantDescription);
                    Material material = new Material(materialUnit);
                    Bom bomItem = new Bom(quantity,buildDescription,variant,material);

                    bomItems.add(bomItem);
                }
            }
        } catch (SQLException e) {
            throw(new DatabaseException(e.getMessage()));
        }

        return bomItems;
    }

}
