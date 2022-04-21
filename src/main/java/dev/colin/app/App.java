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
import io.javalin.Javalin;

import java.util.List;

public class App {
    static UserService userService = new UserServiceImpl(new UserDAOImpl());
    static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOImpl());
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

        // need to finish specific searches
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
                        // to implement method
                        context.result("pending");
                    } break;
                    case "approved": {
                        // to implement method
                        context.result("approved");
                    } break;
                    case "denied": {
                        // to implement method
                        context.result("denied");
                    } break;
                    default: {
                        context.result("you done fucked up A-A-RON");
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
                    context.result("expense not found");
                }

            } catch (NumberFormatException exception) {
                context.result("entered word expected number");
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

        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);

            if (expense.getAmount() >= 0) {
                CheckExpense checkExpense = expenseService.createExpense(expense);

                if (checkExpense.getExpense() != null) {
                    context.status(201);
                    String json = gson.toJson(checkExpense);
                    context.result("expense created: " + json);
                } else {
                    context.status(404);
                    if (checkExpense.getStatus() == 1) {
                        context.result("user id does not exist");
                    } else {
                        context.result("issue creating expense");
                    }
                }
            } else {
                context.status(404);
                context.result("amount must be greater then 0");
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

        app.put("/expenses/{id}",context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Expense expense = gson.fromJson(body,Expense.class);

                if (expense.getAmount() >= 0) {
                    if (expenseId == expense.getId()) {
                        CheckBoolean checkUpdate = expenseService.updateExpense(expense);

                        if (checkUpdate.isCheck()) {
                            context.status(201);
                            context.result("expense updated");
                        } else if (checkUpdate.getStatus() == 1) {
                            context.status(404);
                            context.result("invalid user id");
                        } else if (checkUpdate.getStatus() == 2) {
                            context.status(404);
                            context.result("invalid expense id");
                        } else {
                            context.status(404);
                            context.result("issue updating expense");
                        }

                    } else {
                        context.status(404);
                        context.result("input mismatch");
                    }
                } else {
                    context.status(404);
                    context.result("amount must be more then 0");
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

        app.delete("/expenses/{id}",context -> {
            try {
                int expenseId = Integer.parseInt(context.pathParam("id"));
                CheckBoolean checkExpense = expenseService.deleteExpense(expenseId);

                if (checkExpense.isCheck()) {
                    context.status(201);
                    context.result("expense was deleted");
                } else {
                    context.status(404);
                    if (checkExpense.getStatus() == 1) {
                        context.result("can't delete status of approved or denied");
                    } else if (checkExpense.getStatus() == 2) {
                        context.result("no expense found");
                    } else {
                        context.result("issue deleting expense");
                    }
                }
            } catch (NumberFormatException exception) {
                context.status(404);
                context.result("invalid data type");
            }
        });

        app.start(5000);
    }
}
