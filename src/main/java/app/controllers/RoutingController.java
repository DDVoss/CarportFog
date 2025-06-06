package app.controllers;

import app.entities.Bom;
import app.entities.Material;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import app.persistence.BomMapper;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.controllers.UserController.sendRequest;


public class RoutingController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)    {

        // render
        app.get("index", ctx -> ctx.render("index.html"));
        //app.get("size", ctx -> ctx.render("size.html"));
        app.get("customer-information", ctx -> ctx.render("customer-information.html"));
        //app.get("plan-drawing", ctx -> ctx.render("plan-drawing.html"));
        app.get("login", ctx -> ctx.render("loginpage.html"));
        app.get("adminpage", ctx -> ctx.render("adminPage.html"));
        app.get("receipt", ctx -> ctx.render("receipt.html"));
        app.get("plan-drawing", ctx -> OrderController.showOrder(app, ctx));


        //posts
        app.post("/login", UserController.loginPost);
        app.post("createCustomerAndOrder", ctx -> sendRequest(ctx, connectionPool));

        //size input form parameters gets put inside a hashmap and saved in a session attribute
        app.post("/size", ctx -> {
            Map<String, String> sizeInfo = new HashMap<>();
            sizeInfo.put("width", ctx.formParam("width"));
            sizeInfo.put("length", ctx.formParam("length"));
            ctx.sessionAttribute("sizeInfo", sizeInfo);
            ctx.redirect("/plan-drawing");

        });

        // updates users and orders by overwriting information from order-details.html
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

            User user = new User(userId, firstName, lastName, phoneNumber, email, address, zip);
            UserMapper.updateUserDetails(user);

            ctx.redirect("/admin/orders/" + orderId);

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

       // Remembers the inputted size when navigating back to size.html
        app.get("/size", ctx -> {
            Map<String, Object> model = new HashMap<>();
            Map<String, String> sizeInfo = ctx.sessionAttribute("sizeInfo");
            model.put("sizeInfo", sizeInfo);
            ctx.render("size.html", model);
        });

        // Gets all inputted information from size and customer-information and renders summary.html
        app.get("summary", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sizeInfo", ctx.sessionAttribute("sizeInfo"));
            model.put("perInfo", ctx.sessionAttribute("perInfo"));
            ctx.render("summary.html", model);
        });


        // Gets a list of all orders and renders see-all-orders.html
        app.get("/admin/orders", ctx -> {
            List<Order> orders = OrderMapper.getAllOrders();
            ctx.attribute("orders", orders);
            ctx.render("see-all-orders.html");
        });


        // Gets specific order information from an orderId. Also gets all available status and renders order-details
        app.get("/admin/orders/{id}", ctx -> {
            int orderId = Integer.parseInt(ctx.pathParam("id"));
            Order order = OrderMapper.getAllOrderDetailsById(orderId);
            List<String> statuses = OrderMapper.getAllOrderStatuses();

            ctx.attribute("order", order);
            ctx.attribute("statuses", statuses);
            ctx.render("order-details.html");
        });


        // Gets specific order details from a user if  userid is filled out via a login screen
        app.get("/user/orders", ctx -> {
            Integer userId = ctx.sessionAttribute("userId");
            if (userId == null) {
                ctx.status(403).result("Not logged in");
                return;
            }

            List<Order> orders = OrderMapper.getAllOrdersWithUserByUserId(userId);
            ctx.attribute("orders", orders);
            ctx.render("user-orders.html");
        });


        // Gets a list of all materials and renders admin-materials.html
        app.get("/admin/materialer", ctx -> {
            List<Material> allMaterials = MaterialMapper.getMaterials();
            ctx.attribute("materials", allMaterials);
            ctx.render("admin-materials.html");
        });


        // Gets specific bill of material items from an orderId
        app.get("/BOM/{orderId}", ctx -> {
            int orderId = Integer.parseInt(ctx.pathParam("orderId"));
            try {

                List<Bom> bomItems = BomMapper.getBomItemsByOrderId(orderId, connectionPool);
                ctx.attribute("bomItems", bomItems);
                ctx.render("bomsite.html");
            } catch (DatabaseException e) {
                ctx.attribute("error", "Kunne ikke hente materialelisten.");

            }
        });
    }
}


