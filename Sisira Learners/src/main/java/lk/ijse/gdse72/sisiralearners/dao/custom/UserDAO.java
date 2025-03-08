package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean authenticateUser(String username, String password) throws SQLException;
}
