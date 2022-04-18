package dev.colin.data;

import dev.colin.entities.User;

import java.util.List;

public interface userDAO {

    List<User> getUsers();

    User createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

}
