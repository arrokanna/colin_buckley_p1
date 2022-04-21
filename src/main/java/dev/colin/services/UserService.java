package dev.colin.services;

import dev.colin.utilities.CheckBoolean;
import dev.colin.entities.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User createUser(User user);

    User getUserById(int userId);

    CheckBoolean updateUser(User user);

    CheckBoolean delete(int userId);

}
