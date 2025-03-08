package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.UserFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.UserDAO;
import lk.ijse.gdse72.sisiralearners.dto.UserDTO;
import lk.ijse.gdse72.sisiralearners.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserFormBOImpl implements UserFormBO {

    private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = userDAO.getAllData();
        ArrayList<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(new UserDTO(
                    user.getUser_id(),
                    user.getUser_name(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()
            ));
        }
        return userDTOs;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(
                userDTO.getUser_id(),
                userDTO.getUser_name(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(
                userDTO.getUser_id(),
                userDTO.getUser_name(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userId);
    }

    @Override
    public boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.exist(id);
    }

    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        return userDAO.getNewId();
    }
}