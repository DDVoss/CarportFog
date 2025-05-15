package app.services;

import app.entities.Material;
import app.entities.Variant;
import app.persistence.mappers.MaterialsMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static final int POSTS = 2;
    private static final int BEAMS = 3;
    private static final int RAFTERS = 4;

    private List<Material> materialItems = new ArrayList<>();
    private int width;
    private int length;

    public Calculator(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void calcCarport()   {
        calcBeams();
        calcRafters();
        calcPost();
    }

    // Stolper
    private   void  calcPost()  {
        // Antal Stolper
        int quantity = 6;

        // Finde længde på stolper - dvs variant
        Variant variant = MaterialsMapper.getVariantsByMaterialIdAndMinLength(0, )
    }

    // Remme
    private void calcBeams() {

    }

    // Spær
    private void calcRafters()   {

    }

    public List<Material> getMaterialItems()  {
        return materialItems;
    }

}
