package dev.colin.app;

import com.google.gson.Gson;
import dev.colin.data.UserDAOImpl;
import dev.colin.entities.CheckBoolean;
import dev.colin.entities.User;
import dev.colin.services.UserService;
import dev.colin.services.UserServiceImpl;
import io.javalin.Javalin;

import java.util.List;
import java.util.Objects;

public class App {
    static UserService userService = new UserServiceImpl(new UserDAOImpl());
    //static List<User> userList = userService.getUsers();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // get methods
        app.get("/employees", context -> {
            List<User> userList = userService.getUsers();
            String json = gson.toJson(userList);
            context.result(json);
        });

        app.get("/employees/{id}",context -> {
            try {
                int userId = Integer.parseInt(context.pathParam("id"));
                User user = userService.getUserById(userId);

                if (user != null) {
                    String json = gson.toJson(user);
                    context.result(json);
                } else {
                    context.status(404);
                    context.result("no employee found");
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result("invalid data type");
            }

        });

        // post methods
        app.post("/employees", context -> {
            String body = context.body();
            User user = gson.fromJson(body, User.class);
            User newUser = userService.createUser(user);

            if (newUser != null) {
                context.status(201);
                String json = gson.toJson(newUser);
                context.result("User created: " + json);
            } else {
                context.status(404);
                context.result("issue creating user");
            }

        });

        // put methods
        app.put("/employees/{id}", context -> {
            try {
                int userId = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                User user = gson.fromJson(body, User.class);

                if (userId == user.getId()) {
                    CheckBoolean checkUpdate = userService.updateUser(user);

                    if (checkUpdate.isCheck()) {
                        context.status(201);
                        context.result("employee updated");
                    } else {
                        context.status(404);
                        if (checkUpdate.getStatus() == 1) {
                            context.result("no employee found");
                        } else {
                            context.result("issue updating employee");
                        }
                    }
                } else {
                    context.status(404);
                    context.result("input mismatch");
                }

            } catch (NumberFormatException exception) {
                context.status(404);
                context.result("invalid data type");
            }


        });

        // delete methods
        app.delete("/employees/{id}", context -> {
            try {
                int userId = Integer.parseInt(context.pathParam("id"));
                CheckBoolean checkDelete = userService.delete(userId);

                if (checkDelete.isCheck()) {
                    context.result("employee was deleted");
                } else {
                    context.status(404);
                    if (checkDelete.getStatus() == 1) {
                        context.result("No employee found");
                    } else {
                        context.result("Issue deleting user");
                    }
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result("invalid data type");
            }
        });

        app.start(7000);
    }
}
