package app.services;

public class CarportSvg {

    private int width;
    private int length;
    private Svg carportSvg;




    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(0,0,"-50 0 905 690", "75%");
        carportSvg.addRectangle(0,0,600,780, "stroke-width:1px; stroke:#000000; fill:#ffffff");
        addBeams();
        addRafters();
        addPost();
        addMeasurementLines();

    }

    private void addBeams(){ //rem

        carportSvg.addRectangle(0,35,4.5,780, "stroke-width:1px; stroke:#000000; fill:#ffffff");
        carportSvg.addRectangle(0,565,4.5,780, "stroke-width:1px; stroke:#000000; fill:#ffffff");
    }

    private void addRafters(){ //spær
        for (double i = 0; i < 780; i += 55){
            carportSvg.addRectangle(i, 0, 600, 4.5, "stroke:#000000; fill: #ffffff");
        }

    }

    private void addPost(){
        for (double i = 100; i< 750; i += 200){
            carportSvg.addRectangle(i, 35, 9.5, 9.5, "stroke:#000000; fill: #ffffff");
            carportSvg.addRectangle(i, 565, 9.5, 9.5, "stroke:#000000; fill: #ffffff" );
        }
    }

    private void addMeasurementLines(){
        // Målestreg for længden (vandret pil nederst)
        carportSvg.addArrow(10, 630, 780, 630, "stroke:#000000; stroke-width:1");
        // Målestreg for bredden (lodret pil til venstre)
        carportSvg.addArrow(-40, 35, -40, 565, "stroke:#000000; stroke-width:1");
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
