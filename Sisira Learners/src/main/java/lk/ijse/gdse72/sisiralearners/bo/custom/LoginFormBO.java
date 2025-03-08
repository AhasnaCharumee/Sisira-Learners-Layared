package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;

import java.sql.SQLException;

public interface LoginFormBO extends SuperBO {
    boolean authenticateUser(String username, String password) throws SQLException;

}
