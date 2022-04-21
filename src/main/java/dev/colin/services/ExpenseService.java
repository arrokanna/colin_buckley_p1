package dev.colin.services;

import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.Expense;
import dev.colin.utilities.CheckExpense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpenses();

    CheckExpense createExpense(Expense expense);

    Expense getExpenseById(int expenseId);

    CheckBoolean updateExpense(Expense expense);

    CheckBoolean deleteExpense(int expenseId);

}
