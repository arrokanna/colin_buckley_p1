package dev.colin.data;

import dev.colin.entities.User;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    static UserDAO userDAO = new UserDAOImpl();
    static User testUser = new User();

    @Test
    @Order(1)
    void create_new_user() {
        User newUser = new User(0,"Test","Test");
        testUser = userDAO.createUser(newUser);

        Assertions.assertNotEquals(0,testUser.getId());
    }

    @Test
    @Order(2)
    void update_user() {
        User updateTest = new User(testUser.getId(), "Fluffy", "Dragon");
        boolean updatePassed = userDAO.updateUser(updateTest);

        Assertions.assertTrue(updatePassed);
    }

    @Test
    @Order(3)
    void get_user_by_id() {
        testUser = userDAO.getUserById(testUser.getId());

        Assertions.assertEquals("Fluffy",testUser.getFirstName());
    }

    @Test
    @Order(4)
    void delete_user() {
        boolean deletePassed = userDAO.deleteUser(testUser.getId());

        Assertions.assertTrue(deletePassed);
    }

    @Test
    void user_list() {
        List<User> userList = userDAO.getUsers();
        User testUser = userList.get(0);

        Assertions.assertEquals(1, testUser.getId());
    }

    @Test
    void get_user_by_id_bad_id() {
        User noUser = userDAO.getUserById(0);

        Assertions.assertNull(noUser);
    }

}
