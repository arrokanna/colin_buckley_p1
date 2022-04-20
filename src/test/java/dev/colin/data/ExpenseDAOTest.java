package dev.colin.data;

import dev.colin.entities.Expense;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDAOTest {

    static ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    static Expense testExpense = new Expense();

    @Test
    @Order(1)
    void create_new_expense() {
        Expense newExpense = new Expense();
        newExpense.setId(0);
        newExpense.setUserId(2);
        newExpense.setDescription("Test");
        newExpense.setAmount(3.99);
        newExpense.setStatus(3);

        testExpense = expenseDAO.createExpense(newExpense);

        Assertions.assertNotEquals(0,testExpense.getId());

    }

    @Test
    @Order(2)
    void update_expense() {
        Expense updateExpense = new Expense();
        updateExpense.setId(testExpense.getId());
        updateExpense.setUserId(3);
        updateExpense.setDescription("Ribbit");
        updateExpense.setAmount(1.99);
        updateExpense.setStatus(2);

        boolean updateCheck = expenseDAO.updateExpense(updateExpense);

        Assertions.assertTrue(updateCheck);
    }

    @Test
    @Order(3)
    void get_expense_by_id() {
        testExpense = expenseDAO.getExpenseById(testExpense.getId());

        Assertions.assertEquals(2,testExpense.getStatus());
    }

    @Test
    @Order(4)
    void delete_expense() {
        boolean checkDelete = expenseDAO.deleteExpense(testExpense.getId());

        Assertions.assertTrue(checkDelete);
    }

    @Test
    void get_all_expenses() {
        List<Expense> expenseList = expenseDAO.expenses();
        Expense expense = expenseList.get(0);

        Assertions.assertEquals(1,expense.getUserId());

    }

}
