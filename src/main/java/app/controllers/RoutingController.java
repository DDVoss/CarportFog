package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoutingController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {
        // render
        app.get("index", ctx -> ctx.render("index.html"));
        app.get("size", ctx -> ctx.render("size.html"));
        app.get("customer-information", ctx -> ctx.render("customer-information.html"));

        app.get("plan-drawing", ctx -> ctx.render("plan-drawing.html"));
        app.get("login", ctx -> ctx.render("loginpage.html"));
        app.get("adminpage", ctx -> ctx.render("adminPage.html"));


        //posts

        app.post("/size", ctx -> {
            Map<String, String> sizeInfo = new HashMap<>();
            sizeInfo.put("width", ctx.formParam("width"));
            sizeInfo.put("length", ctx.formParam("length"));
            sizeInfo.put("trapez", ctx.formParam("trapez"));
            ctx.sessionAttribute("sizeInfo", sizeInfo);
            ctx.redirect("/summary");
        });

        app.post("/admin/orderDetails/update", ctx -> {
            //order
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            String status = ctx.formParam("orderStatus");
            int totalPrice = Integer.parseInt(ctx.formParam("totalPrice"));
            int width = Integer.parseInt(ctx.formParam("width"));
            int length = Integer.parseInt(ctx.formParam("length"));
            //user
            int userId = Integer.parseInt(ctx.formParam("userId"));
            String firstName = ctx.formParam("firstName");
            String lastName = ctx.formParam("lastName");
            String email = ctx.formParam("email");
            int phoneNumber = Integer.parseInt(ctx.formParam("phoneNumber"));
            String address = ctx.formParam("address");
            int zip = Integer.parseInt(ctx.formParam("zip"));

            Order order = new Order(orderId, totalPrice, status, width, length);
            OrderMapper.updateOrderDetails(order);

            User user = new User(userId, firstName, lastName, email, phoneNumber, address, zip);
            UserMapper.updateUserDetails(user);

            ctx.redirect("/admin/orders/" + orderId);

        });

        app.get("summary", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sizeInfo", ctx.sessionAttribute("sizeInfo")); // Ensure session data is available
            ctx.render("summary.html", model);
        });


        // get all orders
        app.get("/admin/orders", ctx -> {
            List<Order> orders = OrderMapper.getAllOrders();
            ctx.attribute("orders", orders);
            ctx.render("see-all-orders.html");
        });

        // get all orderDetails
       app.get("/admin/orders/{id}", ctx -> {

            int orderId = Integer.parseInt(ctx.pathParam("id"));
            Order order = OrderMapper.getAllOrderDetailsById(orderId);
            List<String> statuses = OrderMapper.getAllOrderStatuses();

            ctx.attribute("order", order);
            ctx.attribute("statuses", statuses);
            ctx.render("order-details.html");
        });

    }
}
