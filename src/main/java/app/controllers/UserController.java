package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import app.services.Calculator;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.SQLException;

public class UserController {

    public static Handler loginPost = ctx -> {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        try {
            User user = UserMapper.getUserByEmail(email);

            if (user == null) {
                ctx.status(400).result("User not found");
                return;
            }

            String userPassword = user.getPassword();
            if (userPassword == null) {
                ctx.status(400).result("Password does not exist");
                return;
            }

            if (!password.equals(userPassword)) {
                ctx.status(400).result("Incorrect password");
                return;
            }
            ctx.sessionAttribute("currentUser", user);
            ctx.sessionAttribute("userId", user.getUserId());

            if (user.isAdmin()) {
                ctx.redirect("/adminpage");
            } else {

                ctx.redirect("/user/orders");
            }

        } catch (DatabaseException e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };



    public static  void sendRequest(Context ctx, ConnectionPool connectionPool)  {
        /* customer data */
        String firstName = ctx.formParam("fname");
        String lastName = ctx.formParam("lname");
        Integer phone = Integer.parseInt(ctx.formParam("phone"));
        String email = ctx.formParam("email");
        String address = ctx.formParam("address");
        Integer zip = Integer.parseInt(ctx.formParam("zip"));
        String password = ctx.formParam("password");

        /* order data */
        Integer width = Integer.parseInt(ctx.formParam("width"));
        Integer length = Integer.parseInt(ctx.formParam("length"));


        try {
            // Creating Customer and order
            int userId = UserMapper.createUser(firstName, lastName, phone, email, address, zip);
            int orderId = OrderMapper.createOrder(userId, width, length);

            //Calculator
            Calculator calculator = new Calculator(width, length, connectionPool);
            Order newOrder = OrderMapper.getOrderDetailsById(orderId);
            calculator.calcCarport(newOrder);

            // Inserting the calculated items in database
            OrderMapper.insertBomItems(calculator.getBomItems(), connectionPool);

            ctx.render("receipt.html");
        } catch (DatabaseException e)   {
            ctx.attribute("error", "Database fejl prøv venligst igen");
            ctx.render("summary.html"); /* Should be changed to the receipt site (receipt site not created yet)*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public static Handler logout = ctx -> {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    };


}