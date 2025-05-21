package app.persistence;
import app.entities.Bom;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static app.Main.connectionPool;

public class BomMapper {

    public static void createBom(Bom bom) throws SQLException, DatabaseException {
        String sql = "INSERT INTO bom (variant_id, length, order_id) VALUES (?, ?, ?) RETURNING bom_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bom.getVariantId());
            ps.setInt(2, bom.getLength());
            ps.setInt(3, bom.getOrderId());


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("bom_id");
                    bom.setBomId(generatedId);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting bom");
        }
    }



}

