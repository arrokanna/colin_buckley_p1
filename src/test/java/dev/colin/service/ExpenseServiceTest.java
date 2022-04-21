package dev.colin.service;

import dev.colin.data.ExpenseDAOImpl;
import dev.colin.entities.Expense;
import dev.colin.services.ExpenseService;
import dev.colin.services.ExpenseServiceImpl;
import dev.colin.utilities.CheckBoolean;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseServiceTest {
    static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOImpl());
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

        testExpense = expenseService.createExpense(newExpense);

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

        CheckBoolean updateCheck = expenseService.updateExpense(updateExpense);

        Assertions.assertTrue(updateCheck.isCheck());
    }

    @Test
    @Order(3)
    void get_expense_by_id() {
        testExpense = expenseService.getExpenseById(testExpense.getId());

        Assertions.assertEquals(2,testExpense.getStatus());
    }

    @Test
    @Order(4)
    void delete_expense() {
        CheckBoolean checkDelete = expenseService.deleteExpense(testExpense.getId());

        Assertions.assertTrue(checkDelete.isCheck());
    }

}
