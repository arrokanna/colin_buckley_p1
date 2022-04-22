package dev.colin.app;

import com.google.gson.Gson;
import dev.colin.data.ExpenseDAOImpl;
import dev.colin.data.UserDAOImpl;
import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.Expense;
import dev.colin.entities.User;
import dev.colin.services.ExpenseService;
import dev.colin.services.ExpenseServiceImpl;
import dev.colin.services.UserService;
import dev.colin.services.UserServiceImpl;
import dev.colin.utilities.CheckExpense;
import dev.colin.utilities.GlobalVariables;
import io.javalin.Javalin;

import java.util.List;
import java.util.Map;

public class App {
    static UserService userService = new UserServiceImpl(new UserDAOImpl());
    static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOImpl());
    static Gson gson = new Gson();
    static Map<Integer,String> statusCode = GlobalVariables.statusCode;

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
                    context.result(statusCode.get(2));
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        app.get("/employees/{id}/expenses",context -> {
            try {
                int userId = Integer.parseInt(context.pathParam("id"));
                List<Expense> userExpenses = expenseService.getUserExpenses(userId);
                String json = gson.toJson(userExpenses);
                context.result(json);
            }catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        app.get("/expenses", context -> {
            String status = context.queryParam("status");

            if (status == null) {
                List<Expense> expenseList = expenseService.getExpenses();
                String json = gson.toJson(expenseList);
                context.result(json);
            } else {
                status = status.toLowerCase();
                switch (status) {
                    case "pending": {
                        List<Expense> pendingExpenseList = expenseService.getExpensesByStatus(1);
                        String json = gson.toJson(pendingExpenseList);
                        context.result(json);
                    } break;
                    case "approved": {
                        List<Expense> approvedExpenseList = expenseService.getExpensesByStatus(2);
                        String json = gson.toJson(approvedExpenseList);
                        context.result(json);
                    } break;
                    case "denied": {
                        List<Expense> deniedExpenseList = expenseService.getExpensesByStatus(3);
                        String json = gson.toJson(deniedExpenseList);
                        context.result(json);
                    } break;
                    default: {
                        context.status(404);
                        context.result(statusCode.get(6));
                    }
                }
            }
        });

        app.get("/expenses/{id}", context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                Expense expense = expenseService.getExpenseById(expenseId);

                if (expense != null) {
                    String json = gson.toJson(expense);
                    context.result(json);
                } else {
                    context.status(404);
                    context.result(statusCode.get(3));
                }

            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
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
                context.result(statusCode.get(1));
            }
        });

        app.post("/employees/{id}/expenses", context -> {
            try {
                int userId = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);

                if (userId == expense.getUserId()) {
                    CheckExpense checkExpense = expenseService.createExpense(expense);

                    if (checkExpense.getExpense() != null) {
                        context.status(201);
                        String json = gson.toJson(checkExpense.getExpense());
                        context.result("expense created: " + json);
                    } else {
                        context.status(404);
                        context.result(statusCode.get(checkExpense.getStatus()));
                    }
                } else {
                    context.status(404);
                    context.result(statusCode.get(7));
                }
            }  catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }

        });

        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            CheckExpense checkExpense = expenseService.createExpense(expense);

            if (checkExpense.getExpense() != null) {
                context.status(201);
                String json = gson.toJson(checkExpense.getExpense());
                context.result("expense created: " + json);
            } else {
                context.status(404);
                context.result(statusCode.get(checkExpense.getStatus()));
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
                        context.result(statusCode.get(checkUpdate.getStatus()));
                    }
                } else {
                    context.status(404);
                    context.result(statusCode.get(7));
                }

            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        app.put("/expenses/{id}",context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Expense expense = gson.fromJson(body,Expense.class);

                if (expenseId == expense.getId()) {
                    CheckBoolean checkUpdate = expenseService.updateExpense(expense);
                    if (checkUpdate.isCheck()) {
                        context.status(201);
                        context.result("expense updated");
                    } else {
                        context.status(404);
                        context.result(statusCode.get(checkUpdate.getStatus()));
                    }
                } else {
                    context.status(404);
                    context.result(statusCode.get(7));
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        //patch
        app.patch("/expenses/{id}/{status}", context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                String status = context.pathParam("status");
                int updateStatus = 0;

                if (status.equals("approve")) {
                    updateStatus = 2;
                } else if (status.equals("deny")) {
                    updateStatus = 3;
                }

                CheckBoolean approveExpense = expenseService.approveOrDenyExpense(expenseId,updateStatus);
                if (approveExpense.isCheck()) {
                    context.status(201);
                    context.result("Expense was updated");
                } else {
                    context.status(404);
                    context.result(statusCode.get(approveExpense.getStatus()));
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
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
                    context.result(statusCode.get(checkDelete.getStatus()));
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        app.delete("/expenses/{id}",context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                CheckBoolean checkExpense = expenseService.deleteExpense(expenseId);

                if (checkExpense.isCheck()) {
                    context.status(201);
                    context.result("expense was deleted");
                } else {
                    context.status(404);
                    context.result(statusCode.get(checkExpense.getStatus()));
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result(statusCode.get(6));
            }
        });

        app.start(5000);
    }
}
