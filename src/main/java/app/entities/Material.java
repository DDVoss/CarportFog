package app.entities;

public class Material {
    private int materialId;
    private String description;
    private String unit;
    private String type;
    private int pricePrUnit;


    public Material(int materialId, String description, String unit, String type, int pricePrUnit) {
        this.materialId = materialId;
        this.description = description;
        this.unit = unit;
        this.type = type;
        this.pricePrUnit = pricePrUnit;

    }

    public Material(String materialUnit) {
        this.unit = materialUnit;
    }


    public int getMaterialId() {
        return materialId;
    }
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public int getPricePrUnit() {
        return pricePrUnit;
    }
    public void setPricePrUnit(int pricePrUnit) {
        this.pricePrUnit = pricePrUnit;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", type='" + type + '\'' +
                ", pricePrUnit=" + pricePrUnit +
                '}';
    }
}
