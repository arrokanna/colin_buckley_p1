package dev.colin.services;

import dev.colin.entities.User;

import java.util.List;

public interface userService {

    List<User> getUsers();

    User createUser(User user);

    boolean updateUser(User user);

    boolean delete(int userId);

}
