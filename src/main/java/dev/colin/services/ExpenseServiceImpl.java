package dev.colin.services;

import dev.colin.data.ExpenseDAO;
import dev.colin.data.UserDAOImpl;
import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.Expense;
import dev.colin.entities.User;
import dev.colin.utilities.CheckExpense;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Expense> getUserExpenses(int userId) {
        return this.expenseDAO.expenses().stream().filter(u->u.getUserId() == userId).collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpensesByStatus(int status) {
        return this.expenseDAO.expenses().stream().filter(s->s.getStatus() == status).collect(Collectors.toList());
    }

    @Override
    public CheckExpense createExpense(Expense expense) {
        User checkUser = this.userDAO.getUserById(expense.getUserId());
        if (checkUser != null && expense.getAmount() >= 0) {
            return new CheckExpense(this.expenseDAO.createExpense(expense),1);
        } else if (checkUser == null){
            return new CheckExpense(null,2);
        } else {
            return new CheckExpense(null,4);
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

        if (checkExpense != null && checkUser != null && expense.getAmount() >= 0 && checkExpense.getStatus() == 1 && expense.getStatus() == 1) {
            return new CheckBoolean(this.expenseDAO.updateExpense(expense),1);
        } else if (checkExpense == null) {
            return new CheckBoolean(false,3);
        } else if (checkUser == null) {
            return new CheckBoolean(false,2);
        } else if (expense.getAmount() < 0) {
            return new CheckBoolean(false,4);
        } else {
            return new CheckBoolean(false,5);
        }
    }

    @Override
    public CheckBoolean approveOrDenyExpense(int expenseId, int status) {
        Expense expense = expenseDAO.getExpenseById(expenseId);
        if (expense != null && expense.getStatus() == 1 && status > 1 && status < 4) {
            expense.setStatus(status);
            return new CheckBoolean(expenseDAO.updateExpense(expense),1);
        } else if (expense == null) {
            return new CheckBoolean(false,3);
        } else if (expense.getStatus() != 1) {
            return new CheckBoolean(false, 5);
        } else {
            return new CheckBoolean(false,6);
        }
    }

    @Override
    public CheckBoolean deleteExpense(int expenseId) {
        Expense expense = expenseDAO.getExpenseById(expenseId);

        if (expense != null && expense.getStatus() == 1) {
            return new CheckBoolean(this.expenseDAO.deleteExpense(expenseId),1);
        } else if (expense == null) {
            return new CheckBoolean(false,3);
        } else {
            return new CheckBoolean(false,5);
        }
    }
}
