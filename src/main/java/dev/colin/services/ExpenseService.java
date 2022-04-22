package dev.colin.services;

import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.Expense;
import dev.colin.utilities.CheckExpense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpenses();

    List<Expense> getUserExpenses(int userId);

    List<Expense> getExpensesByStatus(int status);

    CheckExpense createExpense(Expense expense);

    Expense getExpenseById(int expenseId);

    CheckBoolean updateExpense(Expense expense);

    CheckBoolean approveOrDenyExpense(int expenseId, int status);

    CheckBoolean deleteExpense(int expenseId);

}
