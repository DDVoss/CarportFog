package app.entities;

public class Variant {
    private int variantId;
    private Material material;
    private int length;

    public Variant(int variantId, Material material, int length) {
        this.variantId = variantId;
        this.material = material;
        this.length = length;
    }

    public int getVariantId() {
        return variantId;
    }

    public Material getMaterial() {
        return material;
    }

    public int getLength() {
        return length;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
