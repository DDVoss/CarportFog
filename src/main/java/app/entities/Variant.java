package app.entities;

public class Variant {
    private int variantId;
    private Material material;
    private int length;
    private String description;

    public Variant(int variantId, Material material, int length) {
        this.variantId = variantId;
        this.material = material;
        this.length = length;
    }

    public Variant(int variantLength, String variantDescription) {
        this.length= variantLength;
        this.description = variantDescription;
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
    public void setDescription(String description) {
        this.description = description;

    }
    public String getDescription() {
        return description;
    }

}
