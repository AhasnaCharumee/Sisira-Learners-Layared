package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.UserDAO;
import lk.ijse.gdse72.sisiralearners.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    public ArrayList<User> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User");
        ArrayList<User> users = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            users.add(user);
        }

        return users;
    }

    public boolean save(User user) throws SQLException {
        return SQLUtil.execute("INSERT INTO User VALUES (?,?,?,?,?)",
                user.getUser_id(),
                user.getUser_name(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public boolean update(User user) throws SQLException {
        return SQLUtil.execute("UPDATE User SET user_name=?, email=?, password=?, role=? WHERE user_id=?",
                user.getUser_name(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getUser_id()
        );
    }

    public boolean delete(String userId) throws SQLException {
        return SQLUtil.execute("DELETE FROM User WHERE user_id=?", userId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM User WHERE user_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM User ORDER BY user_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }


    public boolean authenticateUser(String username, String password) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User WHERE user_name=? AND password=?", username, password);
        return rst.next();
    }


}