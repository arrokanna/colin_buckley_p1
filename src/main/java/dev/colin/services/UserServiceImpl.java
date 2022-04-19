package dev.colin.services;

import dev.colin.data.UserDAO;
import dev.colin.entities.CheckBoolean;
import dev.colin.entities.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getUsers() {
        return this.userDAO.getUsers();
    }

    @Override
    public User createUser(User user) {
        return this.userDAO.createUser(user);
    }

    @Override
    public User getUserById(int userId) {
        if (userId > 0) {
            return this.userDAO.getUserById(userId);
        } else {
            return null;
        }

    }

    @Override
    public CheckBoolean updateUser(User user) {
        User checkUser = this.userDAO.getUserById(user.getId());

        if (checkUser != null) {
            return new CheckBoolean(this.userDAO.updateUser(user),2);
        } else {
            return new CheckBoolean(false,1);
        }


    }

    @Override
    public CheckBoolean delete(int userId) {
        User checkUser = this.userDAO.getUserById(userId);
        if (checkUser != null) {
            return new CheckBoolean(this.userDAO.deleteUser(userId),2);
        } else {
            return new CheckBoolean(false, 1);
        }

    }
}