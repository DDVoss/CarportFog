package app.entities;

public class Bom {
    private int bom_id;
    private int quantity;
    private String build_description;
    private Order order;
    private Variant variant;


    public Bom(int bom_id, int quantity, String build_description, Order order, Variant variant) {
        this.bom_id = bom_id;
        this.quantity = quantity;
        this.build_description = build_description;
        this.order = order;
        this.variant = variant;
    }

    //getter

    public int getBom_id() {
        return bom_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBuild_description() {
        return build_description;
    }

    public Order getOrder() {
        return order;
    }

    public Variant getVariant() {
        return variant;
    }

//setter


    public void setBom_id(int bom_id) {
        this.bom_id = bom_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBuild_description(String build_description) {
        this.build_description = build_description;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }


}
