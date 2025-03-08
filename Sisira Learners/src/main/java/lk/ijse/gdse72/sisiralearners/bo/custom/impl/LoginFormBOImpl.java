package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.LoginFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.UserDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.impl.UserDAOImpl;

import java.sql.SQLException;

public class LoginFormBOImpl implements LoginFormBO {
    private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean authenticateUser(String username, String password) throws SQLException {
        return userDAO.authenticateUser(username, password);
    }
}
