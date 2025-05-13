package app.entities;

public class Bom {
    private int bom_id;
    private int variant_id;
    private int order_id;
    private int quantity;
    private String build_description;
    private Order order;

    public Bom(int bom_id, int variant_id, int order_id, int quantity, String build_description, Order order) {
        this.bom_id = bom_id;
        this.variant_id = variant_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.build_description = build_description;
        this.order = order;
    }

    //getter

    public int getBom_id() {
        return bom_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public int getOrder_id() {
        return order_id;
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


    //setter


    public void setBom_id(int bom_id) {
        this.bom_id = bom_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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
}
