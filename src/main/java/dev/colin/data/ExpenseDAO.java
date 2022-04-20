package dev.colin.data;

import dev.colin.entities.Expense;

import java.util.List;

public interface ExpenseDAO {

    List<Expense> expenses();

    List<Expense> expenseByUser(int userId);

    Expense createExpense(Expense expense);

    Expense getExpenseById(int expenseId);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int expenseId);

}
