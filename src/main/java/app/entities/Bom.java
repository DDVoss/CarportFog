package app.entities;

public class Bom {
    private int bom_id;
    private int variant_id;
    private int length;
    private int order_id;


    public Bom(int bom_id, int variant_id, int length, int order_id) {
        this.bom_id = bom_id;
        this.variant_id = variant_id;
        this.length = length;
        this.order_id = order_id;
    }


    public int getBomId() {
        return bom_id;
    }
    public void setBomId(int bom_id) {
        this.bom_id = bom_id;
    }


    public int getVariantId() {
        return variant_id;
    }
    public void setVariantId(int variant_id) {
        this.variant_id = variant_id;
    }


    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }


    public int getOrderId() {
        return order_id;
    }
    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }
}