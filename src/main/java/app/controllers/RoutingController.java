package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import org.eclipse.jetty.server.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoutingController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
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
            ctx.redirect("/plan-drawing");
        });

        app.post("/customer-information", ctx -> {
            Map<String, String> perInfo = new HashMap<>();
            perInfo.put("fname", ctx.formParam("fname"));
            perInfo.put("lname", ctx.formParam("lname"));
            perInfo.put("phone", ctx.formParam("phone"));
            perInfo.put("email", ctx.formParam("email"));
            perInfo.put("address", ctx.formParam("address"));
            perInfo.put("zip", ctx.formParam("zip"));
            ctx.sessionAttribute("perInfo", perInfo);
            ctx.redirect("/summary");
        });


        app.get("/size", ctx -> {
            Map<String, Object> model = new HashMap<>();
            Map<String, String> sizeInfo = ctx.sessionAttribute("sizeInfo");
            model.put("sizeInfo", sizeInfo);
            ctx.render("size.html", model);
        });


        app.get("summary", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sizeInfo", ctx.sessionAttribute("sizeInfo"));
            model.put("perInfo", ctx.sessionAttribute("perInfo"));
            ctx.render("summary.html", model);
        });


    }
}
