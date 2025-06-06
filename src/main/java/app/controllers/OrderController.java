package app.controllers;
import app.services.CarportSvg;
import io.javalin.Javalin;
import app.services.Svg;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.Locale;

public class OrderController {

    //Showing the Svg plan-drawing on plan-drawing.html via the routing controller
    public static void showOrder(Javalin app, Context ctx){

        Locale.setDefault(new Locale("US"));
        CarportSvg svg = new CarportSvg(600, 780);

        Svg caportSvg = new Svg(0,0,"0 0 855 690", "100%");
        caportSvg.addRectangle(0,0,600,780, "stroke-width:1px; stroke:#000000; fill:#ffffff");

        ctx.attribute("svg", svg.toString());
        ctx.render("plan-drawing.html");
    }





}
