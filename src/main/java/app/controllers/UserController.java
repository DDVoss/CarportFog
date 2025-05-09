package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;
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


    public static Handler logout = ctx -> {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    };


}