package dev.colin.data;

import dev.colin.entities.Expense;
import dev.colin.utilities.ConnectionUtil;
import dev.colin.utilities.LogLevel;
import dev.colin.utilities.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOImpl implements ExpenseDAO {

    @Override
    public List<Expense> expenses() {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from expenses\n" +
                    "order by expense_id";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            List<Expense> expenseList = new ArrayList<>();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setDescription(rs.getString("expense_description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setStatus(rs.getInt("status"));
                expenseList.add(expense);
            }

            return expenseList;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "expenses()";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return null;
        }

    }

    @Override
    public List<Expense> expenseByUser(int userId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from expenses\n" +
                    "where user_id = ?\n" +
                    "order by expense_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);

            ResultSet rs = ps.executeQuery();
            List<Expense> expenseList = new ArrayList<>();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setDescription(rs.getString("expense_description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setStatus(rs.getInt("status"));
                expenseList.add(expense);
            }

            return expenseList;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "expenseByUser(int: " + userId + " )";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return null;
        }

    }

    @Override
    public Expense createExpense(Expense expense) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into expenses (user_id, expense_description,amount) \n" +
                    "values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,expense.getUserId());
            ps.setString(2,expense.getDescription());
            ps.setDouble(3,expense.getAmount());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Expense newExpense = new Expense();
                newExpense.setId(rs.getInt("expense_id"));
                newExpense.setUserId(rs.getInt("user_id"));
                newExpense.setDescription(rs.getString("expense_description"));
                newExpense.setAmount(rs.getDouble("amount"));
                newExpense.setStatus(rs.getInt("status"));
                return newExpense;
            } else {
                String message = "failed to return created expense";
                String method = "expenseByUser(int: " + expense + " )";
                Logger.log(message, LogLevel.WARNING,method,"ExpenseDAOImpl");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "expenseByUser(int: " + expense.toString() + " )";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return null;
        }

    }

    @Override
    public Expense getExpenseById(int expenseId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from expenses\n" +
                    "where expense_id = ?\n" +
                    "order by expense_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,expenseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setDescription(rs.getString("expense_description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setStatus(rs.getInt("status"));
                return expense;
            } else {
                String message = "failed to retrieve expense";
                String method = "getExpenseById(int: " + expenseId + ")";
                Logger.log(message, LogLevel.WARNING,method,"ExpenseDAOImpl");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "getExpenseById(int: " + expenseId + ")";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return null;
        }

    }

    @Override
    public boolean updateExpense(Expense expense) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update expenses \n" +
                    "set user_id = ?,\n" +
                    "expense_description = ?,\n" +
                    "amount = ?,\n" +
                    "status = ?\n" +
                    "where expense_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,expense.getUserId());
            ps.setString(2,expense.getDescription());
            ps.setDouble(3,expense.getAmount());
            ps.setInt(4,expense.getStatus());
            ps.setInt(5,expense.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "updateExpense(Expense: " + expense.toString() + " )";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return false;
        }

    }

    @Override
    public boolean deleteExpense(int expenseId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from expenses \n" +
                    "where expense_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,expenseId);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "deleteExpense(int: " + expenseId + " )";
            Logger.log(message, LogLevel.ERROR,method,"ExpenseDAOImpl");
            return false;
        }

    }
}
