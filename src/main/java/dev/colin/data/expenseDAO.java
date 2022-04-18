package dev.colin.data;

import dev.colin.entities.Expense;

import java.util.List;

public interface expenseDAO {

    List<Expense> allExpenses();

    Expense createExpense(Expense expense);

    Expense getExpenseById(int expenseId);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int expenseId);

}
