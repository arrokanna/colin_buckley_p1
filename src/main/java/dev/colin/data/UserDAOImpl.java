package dev.colin.data;

import dev.colin.entities.User;
import dev.colin.utilities.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements userDAO {

    @Override
    public List<User> getUsers() {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<User> userList = new ArrayList<>();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                userList.add(user);
            }

            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public User createUser(User user) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into users (first_name,last_name) \n" +
                    "values (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            User newUser = new User();

            if (rs.next()) {
                newUser.setId(rs.getInt("user_id"));
                newUser.setFirstName(rs.getString("first_name"));
                newUser.setLastName(rs.getString("last_name"));
            }

            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public User getUserById(int userId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from users\n" +
                    "where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            User user = new User();

            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean updateUser(User user) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update users \n" +
                    "set first_name = ?,\n" +
                    "last_name = ?\n" +
                    "where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3,user.getId());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteUser(int userId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from users \n" +
                    "where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
