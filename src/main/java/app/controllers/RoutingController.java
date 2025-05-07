package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class RoutingController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        app.get("index", ctx -> ctx.render("index.html"));
        app.get("size", ctx -> ctx.render("size.html"));
        app.get("customer-information", ctx -> ctx.render("customer-information.html"));
        app.get("summary", ctx -> ctx.render("summary.html"));
    }
}
