package app.persistence;

import app.entities.Material;
import app.entities.Variant;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class MaterialMapper {

    public static void createMaterial(Material material) throws SQLException, DatabaseException {
        String sql = "INSERT INTO materials " +
                "(description, unit, type, price_pr_unit) " +
                "VALUES (?, ?, ?, ?) RETURNING material_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, material.getDescription());
            ps.setString(2, material.getUnit());
            ps.setString(3, material.getType());
            ps.setInt(4, material.getPricePrUnit());


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("material_id");
                    material.setMaterialId(generatedId);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting material");
        }
    }




    public static List<Material> getMaterials() throws SQLException, DatabaseException {
        String sql = "SELECT material_id, description, unit, type, price_pr_unit FROM materials";
        List<Material> materials = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {


            while (rs.next()) {
                int materialId = rs.getInt("material_id");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                String type = rs.getString("type");
                int pricePrUnit = rs.getInt("price_pr_unit");

                Material material = new Material(materialId, description, unit, type, pricePrUnit);
                materials.add(material);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return materials;
    }


    public static Material getMaterialById(int materialId) throws SQLException, DatabaseException {
        String sql = "SELECT material_id, description, unit, type, price_pr_unit FROM materials WHERE material_id = ?";
        Material material = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, materialId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String description = rs.getString("description");
                    String unit = rs.getString("unit");
                    String type = rs.getString("type");
                    int pricePrUnit = rs.getInt("price_pr_unit");

                    material = new Material(materialId, description, unit, type, pricePrUnit);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving material by ID");
        }

        return material;
    }



    public static List<Variant> getVariantsByMaterialIdAndMinLength (int minLength, int materialId, ConnectionPool connectionPool)  {
        List<Variant> productVariants = new ArrayList<>();
        String sql ="SELECT * FROM variants " +
                    "INNER JOIN materials m USING(material_id) " +
                    "WHERE material_id = ? AND length >= ?";
        try (Connection connection = connectionPool.getConnection())    {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, materialId);
            ps.setInt(2, minLength);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())    {

                int variantId = resultSet.getInt("variant_id");
                int material_id = resultSet.getInt("material_id");
                int length = resultSet.getInt("length");

                String description = resultSet.getString("description");
                String unit = resultSet.getString("unit");
                String type = resultSet.getString("type");
                int pricePrUnit = resultSet.getInt("price_pr_unit");

                Material material = new Material(materialId,description, unit, type, pricePrUnit);
                Variant variant = new Variant(variantId,material,length);

                productVariants.add(variant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productVariants;
    }
}

