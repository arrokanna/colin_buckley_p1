package dev.colin.services;

import dev.colin.data.ExpenseDAO;
import dev.colin.data.UserDAO;
import dev.colin.data.UserDAOImpl;
import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.Expense;
import dev.colin.entities.User;
import dev.colin.utilities.CheckExpense;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseDAO expenseDAO;

    private final UserService userDAO = new UserServiceImpl(new UserDAOImpl());

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public List<Expense> getExpenses() {
        return this.expenseDAO.expenses();
    }

    @Override
    public CheckExpense createExpense(Expense expense) {
        User checkUser = this.userDAO.getUserById(expense.getUserId());
        if (checkUser != null) {
            return new CheckExpense(this.expenseDAO.createExpense(expense),2);
        } else {
            return new CheckExpense(null,1);
        }
    }

    @Override
    public Expense getExpenseById(int expenseId) {
        if (expenseId > 0) {
            return this.expenseDAO.getExpenseById(expenseId);
        } else {
            return null;
        }
    }

    @Override
    public CheckBoolean updateExpense(Expense expense) {
        Expense checkExpense = this.expenseDAO.getExpenseById(expense.getId());
        User checkUser = this.userDAO.getUserById(expense.getUserId());

        if (checkExpense != null && checkUser != null) {
            return new CheckBoolean(this.expenseDAO.updateExpense(expense),3);
        } else if (checkExpense == null) {
            return new CheckBoolean(false,2);
        } else {
            return new CheckBoolean(false,1);
        }
    }

    @Override
    public CheckBoolean deleteExpense(int expenseId) {
        Expense expense = expenseDAO.getExpenseById(expenseId);

        if (expense != null && expense.getStatus() == 1) {
            return new CheckBoolean(this.expenseDAO.deleteExpense(expenseId),3);
        } else if (expense == null) {
            return new CheckBoolean(false,2);
        } else {
            return new CheckBoolean(false,1);
        }
    }
}
