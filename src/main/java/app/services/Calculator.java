package app.services;

import app.entities.Bom;
import app.entities.Material;
import app.entities.Order;
import app.entities.Variant;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;

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

    public Calculator(int width, int length, ConnectionPool connectionPool) {
        this.width = width;
        this.length = length;
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
        List <Variant> variants = MaterialMapper.getVariantsByMaterialIdAndMinLength(0, POSTS, connectionPool);
        Variant variant = variants.get(0);
        Bom bom = new Bom(quantity, "Stolper nedgraves 90 cm. i jord", order, variant);
        bomItems.add(bom);

    }

    public int calcPostQuantity()   {
        return 2 * (2 + (length - 130) / 340);
    }

    // Remme
    private void calcBeams(Order order) {
        // Antal remme
        int quantity = 2;
        int length = order.getLength();

        try {
            List<Variant> variants = MaterialMapper.getVariantsByMaterialIdAndMinLength(0, BEAMS, connectionPool);
            if (length <= 600)  {
                Variant variant = variants.get(3);
                Bom bom1 = new Bom(quantity,"Remme i sider, sadles ned i stolper", order, variant);
                bomItems.add(bom1);

            }if (length > 600){
                // If Variant exceeds 600 it also adds a new raft
                Variant variant = variants.get(3);
                Bom bom1 = new Bom(quantity,"Remme i sider, sadles ned i stolper", order, variant);

                Variant variant1 = variants.get(0);
                Bom bom2 = new Bom(1, "Remme i sider, sadles ned i stolper (deles op)", order, variant1);

                bomItems.add(bom1);
                bomItems.add(bom2);
            }
        } catch (RuntimeException e)    {
            throw new RuntimeException("CalcBeams metoden virker ikke efter hensigten", e);
        }
    }


    // Spær
    private void calcRafters(Order order)   {
        int quantity = calcRaftQuantity();
        try {
            List<Variant> variants = MaterialMapper.getVariantsByMaterialIdAndMinLength(0, RAFTERS, connectionPool);
            if (length > 480) {
                Variant variant = variants.get(1);
                Bom bom = new Bom(quantity, "Spær, monteres på rem", order, variant);
                bomItems.add(bom);
            } if (length <= 480) {
                Variant variant = variants.get(0);
                Bom bom = new Bom(quantity, "Spær, monteres på rem", order, variant);
                bomItems.add(bom);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int calcRaftQuantity()  {
        double rafterWidth = 4.5;
        double spacing = 55;

        return (int) (Math.ceil((length - rafterWidth) / spacing) + 1);
    }

    public List<Bom> getBomItems()  {
        return bomItems;
    }

}
