package dev.colin.services;

import dev.colin.entities.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpenses();

    Expense createExpense(Expense expense);

    Expense getExpenseById(Expense expense);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int ExpenseId);

}
