package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoutingController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        // render
        app.get("index", ctx -> ctx.render("index.html"));
        app.get("size", ctx -> ctx.render("size.html"));
        app.get("customer-information", ctx -> ctx.render("customer-information.html"));

        app.get("plan-drawing", ctx -> ctx.render("plan-drawing.html"));

        //posts

        app.post("/size", ctx -> {
            Map<String, String> sizeInfo = new HashMap<>();
            sizeInfo.put("width", ctx.formParam("width"));
            sizeInfo.put("length", ctx.formParam("length"));
            sizeInfo.put("trapez", ctx.formParam("trapez"));
            ctx.sessionAttribute("sizeInfo", sizeInfo);
            ctx.redirect("/summary");
        });

        app.get("summary", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sizeInfo", ctx.sessionAttribute("sizeInfo")); // Ensure session data is available
            ctx.render("summary.html", model);
        });
    }
}
