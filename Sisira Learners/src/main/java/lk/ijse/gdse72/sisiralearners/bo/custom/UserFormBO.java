package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserFormBO extends SuperBO {
    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String userId) throws SQLException, ClassNotFoundException;
    boolean existUser(String id) throws SQLException, ClassNotFoundException;
    String getNextUserId() throws SQLException, ClassNotFoundException;
}
