package app.entities;

import java.util.List;

public class Bom {
    private Material material;
    private int bom_id;
    private int quantity;
    private String buildDescription;
    private Order order;
    private Variant variant;
    private List <Bom> bomList;


    public Bom(int quantity, String buildDescription, Order order, Variant variant) {
        this.quantity = quantity;
        this.buildDescription = buildDescription;
        this.order = order;
        this.variant = variant;
    }

    public Bom(int quantity, String buildDescription) {
        this.quantity = quantity;
        this.buildDescription = buildDescription;
    }

    public Bom(int quantity, String buildDescription, Variant variant, Material material) {
        this.quantity = quantity;
        this.buildDescription = buildDescription;
        this.variant = variant;
        this.material = material;
    }

    //getter

    public int getBom_id() {
        return bom_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBuildDescription() {
        return buildDescription;
    }

    public Order getOrder() {
        return order;
    }

    public Variant getVariant() {
        return variant;
    }

    public List<Bom> getBomList() {
        return bomList;
    }

    //setter


    public void setBom_id(int bom_id) {
        this.bom_id = bom_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBuild_description(String build_description) {
        this.buildDescription = build_description;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial(){
        return material;
    }


}
