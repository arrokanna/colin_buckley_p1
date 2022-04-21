package dev.colin.utilities;

import dev.colin.entities.Expense;

public class CheckExpense {

    private final Expense expense;
    private final int status;

    public CheckExpense(Expense expense, int status) {
        this.expense = expense;
        this.status = status;
    }

    public Expense getExpense() {
        return expense;
    }

    public int getStatus() {
        return status;
    }
}
