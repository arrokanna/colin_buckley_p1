package dev.colin.data;

import dev.colin.entities.Expense;

import java.util.List;

public interface expenseDAO {

    List<Expense> allExpenses();

    Expense createExpense(Expense expense);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int expenseId);

}
