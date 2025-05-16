package app.services;

import app.entities.Bom;
import app.entities.Material;
import app.entities.Order;
import app.entities.Variant;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.mappers.MaterialsMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static final int POSTS = 2;
    private static final int BEAMS = 3;
    private static final int RAFTERS = 4;

    private List<Bom> bomItems = new ArrayList<>();
    private int width;
    private int length;
    private ConnectionPool connectionPool;

    public Calculator(int length, int width, ConnectionPool connectionPool) {
        this.length = length;
        this.width = width;
        this.connectionPool = connectionPool;

    }

    public void calcCarport(Order order) throws DatabaseException {
        calcBeams(order);
        calcRafters(order);
        calcPost(order);
    }

    // Stolper
    private   void  calcPost(Order order) throws DatabaseException {
        // Antal Stolper
        int quantity = calcPostQuantity();

        // Finde længde på stolper - dvs variant
        List <Variant> variants = MaterialsMapper.getVariantsByMaterialIdAndMinLength(0, POSTS, connectionPool);
        Variant variant = variants.get(0);
        Bom bom = new Bom(0, quantity, "Stolper nedgraves 90 cm. i jord", order, variant);
        bomItems.add(bom);

    }

    public int calcPostQuantity()   {
        return 2 * (2 + (length - 130) / 340);
    }

    // Remme
    private void calcBeams(Order order) {

    }

    // Spær
    private void calcRafters(Order order)   {

    }

    public List<Bom> getBomItems()  {
        return bomItems;
    }

}
