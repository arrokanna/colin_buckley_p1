package dev.colin.services;

import dev.colin.entities.Expense;

import java.util.List;

public interface expenseService {

    List<Expense> getExpenses();

    Expense createExpense(Expense expense);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int ExpenseId);

}
