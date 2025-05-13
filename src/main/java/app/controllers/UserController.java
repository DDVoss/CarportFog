package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UserController {

    public static Handler loginPost = ctx -> {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.getUserByEmail(email);

                if (user != null && password.equals(user.getPassword())) {
                    ctx.sessionAttribute("currentUser", user);
                    ctx.redirect("/dashboard");

                } else {
                    ctx.attribute("error", "Forkert e-mail eller adgangskode");
                    ctx.render("login.html");
                }

        } catch (DatabaseException e) {
            ctx.status(500).result("Serverfejl: " + e.getMessage());
        }
    };

    public static  void createCustomerAndOrder(Context ctx, ConnectionPool connectionPool)  {
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

            int userId = UserMapper.createUser(firstName, lastName, phone, email, address, zip, password);
            OrderMapper.createOrder(userId, width, length);

            ctx.render("index.html");
        } catch (DatabaseException e)   {
            ctx.attribute("error", "Database fejl prøv venligst igen");
            ctx.render("summary.html"); /* Should be changed to the receipt site (receipt site not created yet)*/
        }
    }


    public static Handler logout = ctx -> {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    };


}