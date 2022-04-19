package dev.colin.service;

import dev.colin.data.UserDAOImpl;
import dev.colin.entities.CheckBoolean;
import dev.colin.entities.User;
import dev.colin.services.UserService;
import dev.colin.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    static UserService userService = new UserServiceImpl(new UserDAOImpl());
    static User testUser = new User();

    @Test
    @Order(1)
    void create_new_user() {
        User newUser = new User(0,"Test","Test");
        testUser = userService.createUser(newUser);

        Assertions.assertNotEquals(0,testUser.getId());
    }

    @Test
    @Order(2)
    void update_user() {
        User updateTest = new User(testUser.getId(), "Fluffy", "Dragon");
        CheckBoolean updatePassed = userService.updateUser(updateTest);

        Assertions.assertTrue(updatePassed.isCheck());
    }

    @Test
    @Order(3)
    void get_user_by_id() {
        testUser = userService.getUserById(testUser.getId());

        Assertions.assertEquals("Fluffy",testUser.getFirstName());
    }

    @Test
    @Order(4)
    void delete_user() {
        CheckBoolean deletePassed = userService.delete(testUser.getId());

        Assertions.assertTrue(deletePassed.isCheck());
    }

    @Test
    void user_list() {
        List<User> userList = userService.getUsers();
        User testUser = userList.get(0);

        Assertions.assertEquals(1, testUser.getId());
    }


}
